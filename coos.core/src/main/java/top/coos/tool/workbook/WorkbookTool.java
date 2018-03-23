package top.coos.tool.workbook;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class WorkbookTool {

    /**
     * 创建Excel表格
     * 
     * @param outputStream
     * @param columnNames
     * @param mapLst
     * @param bottom_row_str
     */
    public static void createExcel(OutputStream outputStream, String[] columnNames, List<Map<String, Object>> dataList, String[] bottom_row_str) {
        WritableWorkbook writableWorkbook = null;

        try {

            // 创建可写入的Excel工作簿
            writableWorkbook = Workbook.createWorkbook(outputStream);

            // 创建工作表
            WritableSheet sheet = writableWorkbook.createSheet("create", 0);

            WritableCellFormat wcf = new WritableCellFormat();
            wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            wcf.setBackground(Colour.GRAY_25);
            CellView cellview = new CellView();
            cellview.setSize(600);
            sheet.setRowView(0, cellview);
            WritableCell cell = null;

            // 创建标题单元格
            for (int i = 0; i < columnNames.length; i++) {
                sheet.setColumnView(i, 20);// 根据内容自动设置列宽
                cell = new Label(i, 0, columnNames[i].trim());
                sheet.addCell(cell);
                cell.setCellFormat(wcf);
            }
            wcf = new WritableCellFormat();
            wcf.setAlignment(jxl.format.Alignment.RIGHT); // 设置对齐方式
            // 创建数据单元格
            for (int j = 1; j <= dataList.size(); j++) {
                for (int i = 0; i < columnNames.length; i++) {
                    Map<String, Object> data = dataList.get(j - 1);
                    cell = new Label(i, j, "" + (data.get(columnNames[i]) == null ? "" : data.get(columnNames[i])));
                    sheet.addCell(cell);
                    cell.setCellFormat(wcf);
                }
            }

            // 创建底部单元格
            if (bottom_row_str != null) {
                for (int i = 0; i < bottom_row_str.length; i++) {
                    sheet.addCell(new Label(i, dataList.size() + 1, bottom_row_str[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writableWorkbook != null) {
                    writableWorkbook.write();
                    writableWorkbook.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
