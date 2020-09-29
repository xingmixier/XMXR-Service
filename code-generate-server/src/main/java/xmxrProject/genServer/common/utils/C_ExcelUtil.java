package xmxrProject.genServer.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;

public class C_ExcelUtil implements Closeable{


    private Workbook workbook;
    private Sheet sheet;
    private Integer headRowIndex = 0;
    private Integer bodyRowStartIndex = 1;
    private Integer bodyRowEndIndex = null;
    private Integer[] noBodyRowIndex = null;
    private InputStream is;
    private String fileName;






    public C_ExcelUtil(File file) throws IOException {
        is = new FileInputStream(file);
        fileName = file.getName();
        setWorkbook();

    }

    public C_ExcelUtil(String fileName, InputStream is) {
        this.is = is;
        this.fileName = fileName;
        setWorkbook();
    }

    public C_ExcelUtil(String filePath) throws IOException {
        is = new FileInputStream(filePath);
        this.fileName = filePath;
        setWorkbook();
    }




    public void setSheet(int sheetIndex) {
        sheet = workbook.getSheetAt(sheetIndex);
    }

    public void setSheet(String sheetName) {
        sheet = workbook.getSheet(sheetName);
    }


    public void setWorkbook() {
        String fileEnd = fileName.substring(fileName.lastIndexOf("."));
        try {
            switch (fileEnd) {
                case ".xls":
                    workbook = new HSSFWorkbook(is);
                    break;
                case ".xlsx":
                    workbook = new XSSFWorkbook(is);
                    break;
            }
        } catch (IOException e) {
            System.err.println("初始化 workbook 失败 检查文件名 "+fileName);
            e.printStackTrace();
        }
        setSheet(0);
    }

    public Row getRow(int rowIndex) {
        return sheet.getRow(rowIndex);
    }

    public Cell[] getRowCells(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        short s = row.getLastCellNum();
        Cell[] cells = new Cell[s];
        for (int i = 0; i < s; i++) {
            cells[i] = row.getCell(i);
        }
        return cells;
    }

    public Object getCellValue(int rowIndex, int colIndex) {
        Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
        return getCellValue(cell);
    }

    public Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                //              需要对日期这一列进行设置样式，否则无法识别是日期类型还是数值类型
//              默认不支持中文日期类型，需要设置成纯英语日期类型，不要包含年月日等汉字
//              最好是使用这种格式 2019/10/10 0:00
//              cellValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format();
//              String v=String.format("%.4f",cell.getNumericCellValue());      //默认的整数后边是一个小数点，和一个零
//              整数作为浮点数格式化以后，删除结尾的点零
                if (DateUtil.isCellDateFormatted(cell))
                    return DateUtil.getJavaDate(cell.getNumericCellValue());
                return Double.parseDouble(String.format("%.4f", cell.getNumericCellValue()).replaceAll("\\.0*$", ""));
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            case _NONE:
                return cell;
            case ERROR:
                return cell.getErrorCellValue();
            default:
                return cell.toString();
        }
    }

    /**
     * http 导出文件
     *
     * @param response
     * @param fileName
     * @throws IOException
     */
    public void writeToHttp(HttpServletResponse response, String fileName)   {
        workbook.setForceFormulaRecalculation(true);
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        workbook.setForceFormulaRecalculation(true);
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            System.err.println("response.getOutputStream() 出现异常 请检查 response 对象 状态");
            e.printStackTrace();
        }

    }

    public void coverSheet(Sheet sourceSheet, int targetSheetIndex)   {
        sheet = workbook.getSheetAt(targetSheetIndex);
        int rowCount = sourceSheet.getLastRowNum() > sheet.getLastRowNum() ? sourceSheet.getLastRowNum() : sheet.getLastRowNum();
        int colCount = sourceSheet.getRow(0).getLastCellNum() > sheet.getRow(0).getLastCellNum() ? sourceSheet.getRow(0).getLastCellNum() : sheet.getRow(0).getLastCellNum();
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                setValue(sourceSheet.getRow(row).getCell(col), sheet.getRow(row).getCell(col));
            }
        }
    }

    /**
     * 设置 单元格 值 剪切时 使用
     *
     * @param sourceCell 新值单元格
     * @param targetCell 修改目标单元格
     */
    private void setValue(Cell sourceCell, Cell targetCell) {
        if (sourceCell == null) {
            targetCell.setCellValue("");
            return;
        }
        switch (sourceCell.getCellType()) {
            case NUMERIC:
                String cellValue;
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(sourceCell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellValue = sdf.format(DateUtil.getJavaDate(sourceCell.getNumericCellValue()));
                }
                String v = String.format("%.4f", sourceCell.getNumericCellValue());      //默认的整数后边是一个小数点，和一个零
                cellValue = (v).replaceAll("\\.0*$", "");//整数作为浮点数格式化以后，删除结尾的点零
                targetCell.setCellValue(cellValue);
                break;
            case BLANK:
                targetCell.setCellValue("");
                break;
            case ERROR:
                targetCell.setCellValue(sourceCell.getErrorCellValue());
                break;
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                targetCell.setCellValue(sourceCell.getCellFormula());
                break;
            default:
                targetCell.setCellValue(sourceCell.toString());
        }
    }

    /**
     * 刷新公式
     */
    private void restFormula() {
        workbook.setForceFormulaRecalculation(true);
    }

    public void setRowField(Integer headRowIndex, Integer bodyRowStartIndex, Integer bodyRowEndIndex, Integer[] noBodyRowIndex) {
        this.headRowIndex = headRowIndex;
        this.bodyRowStartIndex = bodyRowStartIndex;
        this.bodyRowEndIndex = bodyRowEndIndex;
        this.noBodyRowIndex = noBodyRowIndex;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Integer getHeadRowIndex() {
        return headRowIndex;
    }

    public void setHeadRowIndex(Integer headRowIndex) {
        this.headRowIndex = headRowIndex;
    }

    public Integer getBodyRowStartIndex() {
        return bodyRowStartIndex;
    }

    public void setBodyRowStartIndex(Integer bodyRowStartIndex) {
        this.bodyRowStartIndex = bodyRowStartIndex;
    }

    public Integer getBodyRowEndIndex() {
        return bodyRowEndIndex;
    }

    public void setBodyRowEndIndex(Integer bodyRowEndIndex) {
        this.bodyRowEndIndex = bodyRowEndIndex;
    }

    public Integer[] getNoBodyRowIndex() {
        return noBodyRowIndex;
    }

    public void setNoBodyRowIndex(Integer[] noBodyRowIndex) {
        this.noBodyRowIndex = noBodyRowIndex;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setCellValue(int row , int col , String  value) {
        sheet.getRow(row).getCell(col).setCellValue(value);
    }
    public void createCell(int row , int col , String value){
        sheet.createRow(row).createCell(col).setCellValue(value);
    }

    public void write(OutputStream os) {
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            C_IOUtil.closeStream(os,workbook);
        }
    }

    @Override
    public void close() throws IOException {
        workbook.close();
    }
}
