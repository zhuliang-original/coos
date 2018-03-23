package top.coos.tool.seal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import top.coos.tool.string.StringHelper;

public class SealTool {

	public SealTool(String title) {

		this.head = title;
	}

	private String head;
	private String body;
	private String icon;
	private File icon_image;
	private String footer;
	private int width = 500;
	private int height = 500;

	public static void main(String[] args) throws Exception {

		SealTool sealTool = new SealTool("这里是头部显示公司名称");
		sealTool.setBody("这里是标准体部内容");

		sealTool.create(new File("D:/1.PNG"));

	}

	public void create(File pngFile) throws IOException {

		BufferedImage image = startGraphics2D();
		ImageIO.write(image, "PNG", pngFile);
	}

	private BufferedImage startGraphics2D() {

		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = buffImg.createGraphics();
		// g.setColor(Color.RED);
		g.setColor(new Color(255, 0, 0));
		// g.setStroke(new BasicStroke(1));
		// 设置锯齿圆滑
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// g.setBackground(new Color(Color.TRANSLUCENT));

		// 绘制圆
		int radius = (width - 40) / 2;// 周半径
		int CENTERX = width / 2;// 画图所出位置
		int CENTERY = height / 2;// 画图所处位置
		Ellipse2D circle = new Ellipse2D.Double();
		circle.setFrameFromCenter(CENTERX, CENTERY, CENTERX + radius, CENTERY + radius);
		g.draw(circle);

		if (icon_image != null && icon_image.isFile()) {
			try {
				BufferedImage icon_img = ImageIO.read(icon_image);
				if (icon_img != null) {
					int w = icon_img.getWidth();
					int h = icon_img.getHeight();
					for (int x = 0; x < w; x++) {
						int x_ = CENTERX - w / 2 + x;
						if (x_ < 0 || x_ >= width) {
							continue;
						}
						for (int y = 0; y < h; y++) {
							int y_ = CENTERY - h / 2 + y;
							if (y_ < 0 || y_ >= height) {
								continue;
							}
							buffImg.setRGB(x_, y_, icon_img.getRGB(x, y));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		else if (!StringHelper.isEmpty(icon)) {
			int size = 120;
			// 绘制中间的五角星
			g.setFont(new Font("宋体", Font.BOLD, size));
			g.drawString(icon, CENTERX - (size / 2), CENTERY + (size / 3));
		}

		if (!StringHelper.isEmpty(body)) {
			// 添加姓名
			int size = 34;
			Font f = new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, size);
			g.setFont(f);// 写入签名
			// 计算坐标
			FontRenderContext context = g.getFontRenderContext();
			Rectangle2D bounds = f.getStringBounds(body, context);
			int w = (int) bounds.getWidth() - body.length() * 2;

			int x = CENTERX - (w / 2);
			g.drawString(body, x, CENTERY + 100);

		}

		if (!StringHelper.isEmpty(footer)) {
			int size = 20;
			// 添加年份
			Font f = new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, size);
			g.setFont(f);
			FontRenderContext context = g.getFontRenderContext();
			Rectangle2D bounds = f.getStringBounds(footer, context);
			int w = (int) bounds.getWidth() - footer.length() * 2;
			int x = CENTERX - (w / 2);
			g.drawString(footer, x, CENTERY + 160);
		}

		// 根据输入字符串得到字符数组
		String[] messages2 = head.split("", 0);
		String[] messages = new String[messages2.length - 1];
		System.arraycopy(messages2, 1, messages, 0, messages2.length - 1);

		// 输入的字数
		int ilength = messages.length;

		// 设置字体属性
		int fontsize = 56;
		Font f = new Font("宋体", Font.BOLD, fontsize);

		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = f.getStringBounds(head, context);

		// 字符宽度＝字符串长度/字符数
		double char_interval = (bounds.getWidth() / ilength);
		// 上坡度
		double ascent = -bounds.getY();

		int first = 0, second = 0;
		boolean odd = false;
		if (ilength % 2 == 1)
		{
			first = (ilength - 1) / 2;
			odd = true;
		}
		else
		{
			first = (ilength) / 2 - 1;
			second = (ilength) / 2;
			odd = false;
		}

		double radius2 = radius - ascent;
		double x0 = CENTERX;
		double y0 = CENTERY - radius + ascent;
		// 旋转角度
		double a = 2 * Math.asin(char_interval / (2 * radius2));

		if (odd)
		{
			g.setFont(f);
			g.drawString(messages[first], (float) (x0 - char_interval / 2), (float) y0);

			// 中心点的右边
			for (int i = first + 1; i < ilength; i++)
			{
				double aa = (i - first) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(aa);
				Font f2 = f.deriveFont(transform);
				g.setFont(f2);
				g.drawString(messages[i], (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
						(float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
			}
			// 中心点的左边
			for (int i = first - 1; i > -1; i--)
			{
				double aa = (first - i) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(-aa);
				Font f2 = f.deriveFont(transform);
				g.setFont(f2);
				g.drawString(messages[i], (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
						(float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
			}

		}
		else
		{
			// 中心点的右边
			for (int i = second; i < ilength; i++)
			{
				double aa = (i - second + 0.5) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(aa);
				Font f2 = f.deriveFont(transform);
				g.setFont(f2);
				g.drawString(messages[i], (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
						(float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
			}

			// 中心点的左边
			for (int i = first; i > -1; i--)
			{
				double aa = (first - i + 0.5) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(-aa);
				Font f2 = f.deriveFont(transform);
				g.setFont(f2);
				g.drawString(messages[i], (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
						(float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
			}
		}
		return buffImg;
	}

	public String getHead() {

		return head;
	}

	public void setHead(String head) {

		this.head = head;
	}

	public String getBody() {

		return body;
	}

	public void setBody(String body) {

		this.body = body;
	}

	public String getIcon() {

		return icon;
	}

	public void setIcon(String icon) {

		this.icon = icon;
	}

	public String getFooter() {

		return footer;
	}

	public void setFooter(String footer) {

		this.footer = footer;
	}

	public int getWidth() {

		return width;
	}

	public void setWidth(int width) {

		this.width = width;
	}

	public int getHeight() {

		return height;
	}

	public void setHeight(int height) {

		this.height = height;
	}

	public File getIcon_image() {

		return icon_image;
	}

	public void setIcon_image(File icon_image) {

		this.icon_image = icon_image;
	}

}
