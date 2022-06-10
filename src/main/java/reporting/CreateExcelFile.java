package reporting;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;

public class CreateExcelFile {

    private WritableSheet sheet;
    private WritableWorkbook workbook;

    public void CreateSheetFile(String file) throws Exception {
        if (this.workbook == null) {
            this.workbook = Workbook.createWorkbook(new File(file));
        }
        this.sheet = this.workbook.createSheet("Report_Sheet", 0);
    }

    public void getExcelSheetFile(String file, String sheetName) {
        try {
            if (this.workbook == null) {
                this.workbook = Workbook.createWorkbook(new File(file));
            }
            this.sheet = this.workbook.getSheet(sheetName) != null ? this.workbook.getSheet(sheetName) : this.workbook.createSheet(sheetName, this.workbook.getNumberOfSheets());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addColumsToReport(String[] columnNames) {
        try {
            for (int i = 0; i < columnNames.length; i++) {
                this.sheet.addCell(new Label(i, 0, columnNames[i]));
            }
            this.workbook.write();
            this.workbook.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToReport(String file, String sheetName, int column, int row, String value) {
        try {
            this.workbook = Workbook.createWorkbook(new File(file), Workbook.getWorkbook(new File(file)));
            this.sheet = this.workbook.getSheet(sheetName) != null ? this.workbook.getSheet(sheetName) : this.workbook.createSheet(sheetName, this.workbook.getNumberOfSheets());
            this.sheet.addCell(new Label(column, row, value));
            this.workbook.write();
            this.workbook.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
