package dataFiles;

import jxl.Sheet;
import jxl.Workbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class Data {

    public String[][] datasheetArr(String datasheet, String sheetName) throws Exception {
        String datasheetArr[][] = null;
        int numRows;
        try {
            InputStream file = new FileInputStream(datasheet);
            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet;
            sheet = workbook.getSheet(sheetName);
            numRows = sheet.getRows();
            int numCols = sheet.getColumns();
            datasheetArr = new String[numRows][numCols];
            for (int i = 0; i < numRows; i++) {
                for (int t = 0; t < numCols; t++) {
                    datasheetArr[i][t] = sheet.getCell(t, i).getContents();
                }
            }
            workbook.close();
        } catch (Exception errMsg) {
            throw new Exception("Data sheet load error: \n" + errMsg.getMessage());
        }
        return datasheetArr;
    }

    public static String getCellValue(String[] headers, String[] data, String columnName) throws Exception {
        try {
            return data[Arrays.asList(headers).indexOf(columnName)].trim();
        } catch (Exception e) {
            String msg = columnName + " is not found in the datasheet";
            throw new Exception(msg + " >> " + e.getMessage());
        }
    }
}
