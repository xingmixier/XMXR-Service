package xmxrProject.genServer.common.utils;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C_ExcelWriterTo<O> {

    Workbook workbook;
    Sheet sheet;
    InputStream is;
    int headRow = 0;
    int bodyStartRow = 1;
    String ExcelFilePath;
    File ExcelFile;


    public C_ExcelWriterTo() {

    }

    public C_ExcelWriterTo(int sheetIndex, String filePath, int headRowIndex, int bodyStartRowIndex) {
        setSheetIndex(sheetIndex);
        setHeadRow(headRowIndex);
        setBodyStartRow(bodyStartRowIndex);
    }


    public void setSheetIndex(int sheetIndex) {
        this.sheet = workbook.getSheetAt(sheetIndex);
    }

    /**
     * @param rowName 行名称
     * @param colName 列名称
     * @param value   值
     * @throws IOException
     */
    public void writeTo(String rowName, String colName, Long value) throws IOException {
        Row row;
        int rowCount = sheet.getPhysicalNumberOfRows();
        int rowIndex = -1, colIndex = -1;
        for (int i = 0; i < rowCount; i++) {
            row = sheet.getRow(i);
            int colCount = row.getPhysicalNumberOfCells();
            for (int ii = 0; ii < colCount; ii++) {
                Cell cell = row.getCell(ii);
                if (cell.getCellType() != CellType.STRING) {
                    continue;
                }
                String cellValue = cell.getStringCellValue();
                if (ii == 0 && colName.equals(cellValue)) {
                    rowIndex = i;
                } else if (rowName.equals(cellValue)) {
                    colIndex = ii;
                }
                if (rowIndex > -1 && colIndex > -1) {
                    break;
                }
            }
        }
        row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        cell.setCellValue(value);
        restFormula();


    }

    /**
     * 刷新公式
     */
    private void restFormula() {
        workbook.setForceFormulaRecalculation(true);
    }

    /**
     * 本地写入
     *
     * @throws IOException
     */
    private void closeAll() throws IOException {
        workbook.setForceFormulaRecalculation(true);
        FileOutputStream os = ExcelFilePath == null ?
                new FileOutputStream(ExcelFile) : new FileOutputStream(ExcelFilePath);

        workbook.write(os);
        is.close();
        os.close();

    }

    /**
     * 设置标题行 与 正文行
     *
     * @param headRow
     */
    public void setHeadRow(int headRow) {
        this.headRow = headRow;
        this.bodyStartRow = this.headRow + 1;
    }

    /**
     * 设置正文开始行
     *
     * @param bodyStartRow
     */
    public void setBodyStartRow(int bodyStartRow) {
        this.bodyStartRow = bodyStartRow;
    }

    /**
     * 获取主键字段列下标
     *
     * @param PKTitle
     * @return
     */
    public int getPKTitleIndex(String PKTitle) {
        Row row = sheet.getRow(headRow);
        int i = 0;
        for (Cell cell : row) {
            if (PKTitle.equals(cell.toString())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * 获取 其他字段 下标
     *
     * @param titles
     * @return
     */
    public Map<Integer, String> getTitleIndex(List<String> titles) {
        Map<Integer, String> map = new HashMap();
        Row row = sheet.getRow(headRow);
        int i = 0;
        for (Cell cell : row) {

            for (int ii = 0; ii < titles.size(); ii++) {
                if (titles.get(ii).equals(cell.toString())) {
                    map.put(i, titles.get(ii));
                    titles.remove(ii);
                }

            }
            i++;
        }
        return map;

    }

    /**
     * 写 一行 根据主键 修改 一行中的某些内容
     *
     * @param PKIndex
     * @param valuesIndex
     * @param os
     * @param isWrite
     * @param callBack
     * @throws IOException
     */
    public void writeToLine(int PKIndex, Map<Integer, String> valuesIndex, List<O> os, boolean isWrite, C_ExcelWriteToCallBack callBack) throws IOException {
        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int i = bodyStartRow; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            String pk = row.getCell(PKIndex).toString();
            for (O o : os) {
                String pkValue = callBack.getPKValue(pk, o);
                if (pk.equals(pkValue)) {
                    for (Map.Entry<Integer, String> entry : valuesIndex.entrySet()) {
                        String value = callBack.getValue(entry.getValue(), o);
                        row.getCell(entry.getKey()).setCellValue(value);
                    }
                }
            }

        }
        if (isWrite) {
            closeAll();
        }

    }


    /**
     * 设置 workBoow
     *
     * @param fileName
     * @param is
     */
    public void setWorkbook(String fileName, InputStream is) {
        this.is = is;
        setWorkbook(fileName);
    }

    /**
     * 设置 WorkBook
     *
     * @param file
     */
    public void setWorkbook(File file) {
        this.ExcelFile = file;
    }

    /**
     * 设置 WorkBook
     *
     * @param filePath
     */
    public void setWorkbook(String filePath) {
        this.ExcelFilePath = filePath;
        String extString = filePath.substring(filePath.lastIndexOf("."));
        try {

            is = filePath == null ? new FileInputStream(this.ExcelFile) : is;
            if (extString.equals(".xls")) {

                workbook = new HSSFWorkbook(is);
            }
            if (extString.equals(".xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
            if (workbook == null) {
                throw new RuntimeException("文件格式错误");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    /**
     * 剪切 sheet 目前不完善 可能显示异常
     *
     * @param sheet
     * @param targetSheetIndex
     * @throws IOException
     */
    public void coverSheet(Sheet sheet, int targetSheetIndex) throws IOException {
        this.sheet = workbook.getSheetAt(targetSheetIndex);
        int rowConut = sheet.getLastRowNum() > this.sheet.getLastRowNum() ? sheet.getLastRowNum() : this.sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum() > this.sheet.getRow(0).getLastCellNum() ? sheet.getRow(0).getLastCellNum() : this.sheet.getRow(0).getLastCellNum();
        for (int row = 0; row < rowConut; row++) {
            for (int col = 0; col < colCount; col++) {
                setValue(sheet.getRow(row).getCell(col), this.sheet.getRow(row).getCell(col));
            }
        }
    }

    /**
     * 设置 单元格 值 剪切时 使用
     *
     * @param cell       新值单元格
     * @param targetCell 修改目标单元格
     */
    private void setValue(Cell cell, Cell targetCell) {
        if (cell == null) {
            targetCell.setCellValue("");
            return;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                String cellValue;
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
//                    需要对日期这一列进行设置样式，否则无法识别是日期类型还是数值类型
//                    默认不支持中文日期类型，需要设置成纯英语日期类型，不要包含年月日等汉字
//                    最好是使用这种格式 2019/10/10 0:00
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    cellValue = sdf.format(DateUtil.getJavaDate(cell
                            .getNumericCellValue()));
                    break;
                }
//               cellValue = df.format(cell.getNumericCellValue());
                String v = String.format("%.4f", cell.getNumericCellValue());      //默认的整数后边是一个小数点，和一个零
                cellValue = (v).replaceAll("\\.0*$", "");//整数作为浮点数格式化以后，删除结尾的点零

                targetCell.setCellValue(cellValue);
                break;
            case BLANK:
                targetCell.setCellValue("");
                break;
            case ERROR:
                targetCell.setCellValue(cell.getErrorCellValue());
                break;
            case STRING:
                targetCell.setCellValue(cell.getStringCellValue());
                break;
            case BOOLEAN:
                targetCell.setCellValue(cell.getBooleanCellValue());
                break;
            case FORMULA:
                targetCell.setCellValue(cell.getCellFormula());
                break;
            default:
                targetCell.setCellValue(cell.toString());
        }
    }

    /**
     * 时间值格式化
     *
     * @param date
     * @return
     */
    private static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    /**
     * http 导出文件
     *
     * @param response
     * @param fileName
     * @throws IOException
     */
    public void write(HttpServletResponse response, String fileName) throws IOException {
        workbook.setForceFormulaRecalculation(true);
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        write(response.getOutputStream());

    }

    /**
     * http 导出文件
     *
     * @param outputStream
     * @throws IOException
     */
    private void write(ServletOutputStream outputStream) throws IOException {
        workbook.setForceFormulaRecalculation(true);
        workbook.write(outputStream);

    }

    public Sheet getSheet(int index) {
        return workbook.getSheetAt(index);
    }

    public void writeTo(int row, int col, String title) {
        sheet.getRow(row).getCell(col).setCellValue(title);
    }

    public void writeToLocal(String filePath, String fileName) {
        try {
            OutputStream os = new FileOutputStream(filePath + fileName);
            restFormula();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝Excel行
     *
     * @param fromsheet
     * @param newsheet
     */
    @SuppressWarnings("deprecation")
    public void copyRows(Sheet fromsheet, Sheet newsheet) {
        int firstrow = fromsheet.getFirstRowNum();
        int lastrow = fromsheet.getLastRowNum();

        if ((firstrow == -1) || (lastrow == -1) || lastrow < firstrow) {
            return;
        }
        // 拷贝合并的单元格
        for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
            CellRangeAddress mergedRegion = fromsheet.getMergedRegion(i);
            newsheet.addMergedRegion(mergedRegion);
        }
        Row fromRow = null;
        Row newRow = null;
        Cell newCell = null;
        Cell fromCell = null;
        // 设置列宽
        for (int i = firstrow; i <= lastrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow != null) {
                for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    int colnum = fromsheet.getColumnWidth((short) j);
                    if (colnum > 100) {
                        newsheet.setColumnWidth((short) j, (short) colnum);
                    }
                    if (colnum == 0) {
                        newsheet.setColumnHidden((short) j, true);
                    } else {
                        newsheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }
        // 拷贝行并填充数据
        for (int i = 0; i <= lastrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            newRow = newsheet.createRow(i - firstrow);
            newRow.setHeight(fromRow.getHeight());
            for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {
                fromCell = fromRow.getCell((short) j);
                if (fromCell == null) {
                    continue;
                }
                newCell = newRow.createCell((short) j);
                CellStyle cellStyle = fromCell.getCellStyle();


                CellType cType = fromCell.getCellType();
                newCell.setCellType(cType);
                switch (cType) {
                    case STRING:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(fromCell.getNumericCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellFormula(fromCell.getCellFormula());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(fromCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        newCell.setCellValue(fromCell.getErrorCellValue());
                        break;
                    default:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    public SXSSFWorkbook getWorkBook() {
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(500);
        return sxssfWorkbook;
    }
}
