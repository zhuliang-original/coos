package top.coos.web.servlet.file;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.coos.tool.string.StringHelper;
import top.coos.web.core.WebCorePackageInfo;


@WebServlet(urlPatterns = WebCorePackageInfo.SERVLET_FOLDER + "/file/file.progress")
public class FileProgressServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // 将请求，响应的编码设置为UTF-8(防止乱码)
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String uploadid = request.getParameter("uploadid");
        UploadProgress progressListener = null;
        if (!StringHelper.isEmpty(uploadid) && request.getSession().getAttribute("file-upload-progress-listener-" + uploadid) != null)
        {
            progressListener = (UploadProgress) request.getSession().getAttribute("file-upload-progress-listener-" + uploadid);
            
        }
        try
        {
            if (progressListener != null)
            {
                double percent = progressListener.getPercent();
                BigDecimal b = new BigDecimal(percent);
                double percent1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                printWriter.print(percent1);
            }else{
                printWriter.print(-1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            printWriter.close();
        }
    }


}
