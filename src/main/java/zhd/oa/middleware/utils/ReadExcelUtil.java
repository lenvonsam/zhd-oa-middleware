package zhd.oa.middleware.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReadExcelUtil {

    private static ReadExcelUtil instance = null;

    private ReadExcelUtil() {
    }

    public static ReadExcelUtil shareInstance() {
        if (instance == null) {
            instance = new ReadExcelUtil();
        }
        return instance;
    }

    /**
     * @param file       file
     * @param ignoreRows 忽略的行头
     * @return array
     * @throws FileNotFoundException e
     * @throws IOException           e
     */
    public List<String[]> getData(File file, BufferedInputStream in, int ignoreRows) throws FileNotFoundException, IOException {

        List<String[]> result = new ArrayList<>();

        int rowSize = 0;

        if (null != file) {
            in = new BufferedInputStream(new FileInputStream(file));

        }

        // 打开HSSFWorkbook

        POIFSFileSystem fs = new POIFSFileSystem(in);

        HSSFWorkbook wb = new HSSFWorkbook(fs);

        HSSFCell cell = null;

        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

            HSSFSheet st = wb.getSheetAt(sheetIndex);

            // 第一行为标题，不取

            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

                HSSFRow row = st.getRow(rowIndex);

                if (row == null) {

                    continue;
                }

                int tempRowSize = row.getLastCellNum();

                if (tempRowSize > rowSize) {

                    rowSize = tempRowSize;
                }

                String[] values = new String[rowSize];
                Arrays.fill(values, "");

                boolean hasValue = false;

                for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {

                    String value = "";

                    cell = row.getCell(columnIndex);

                    if (cell != null) {

                        // 注意：一定要设成这个，否则可能会出现乱码

//	                     cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                        switch (cell.getCellType()) {

                            case HSSFCell.CELL_TYPE_STRING:

                                value = cell.getStringCellValue();

                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:

                                if (HSSFDateUtil.isCellDateFormatted(cell)) {

                                    Date date = cell.getDateCellValue();

                                    if (date != null) {

                                        value = new SimpleDateFormat("yyyy-MM-dd")

                                                .format(date);

                                    } else {

                                        value = "";

                                    }

                                } else {

                                    value = new DecimalFormat("0.0000").format(cell

                                            .getNumericCellValue());

                                }

                                break;

                            case HSSFCell.CELL_TYPE_FORMULA:

                                // 导入时如果为公式生成的数据则无值

                                if (!cell.getStringCellValue().equals("")) {

                                    value = cell.getStringCellValue();

                                } else {

                                    value = cell.getNumericCellValue() + "";

                                }

                                break;

                            case HSSFCell.CELL_TYPE_BLANK:

                                break;

                            case HSSFCell.CELL_TYPE_ERROR:

                                value = "";

                                break;

                            case HSSFCell.CELL_TYPE_BOOLEAN:

                                value = (cell.getBooleanCellValue() == true ? "Y"

                                        : "N");

                                break;

                            default:

                                value = "";

                        }

                    }

                    if (columnIndex == 0 && value.trim().equals("")) {

                        break;

                    }
                    values[columnIndex] = rightTrim(value);

                    hasValue = true;
                }
                if (hasValue) {

                    result.add(values);
                }
            }
        }

        in.close();
        return result;

    }

    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */

    private static String rightTrim(String str) {

        if (str == null) {

            return "";

        }

        int length = str.length();

        for (int i = length - 1; i >= 0; i--) {

            if (str.charAt(i) != 0x20) {

                break;

            }

            length--;

        }

        return str.substring(0, length);

    }


}
