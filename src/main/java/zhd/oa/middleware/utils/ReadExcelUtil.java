package zhd.oa.middleware.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    @SuppressWarnings("resource")
	public List<String[]> getData(File file, BufferedInputStream in, int ignoreRows ,String filename) throws Exception {

        List<String[]> result = new ArrayList<>();

        int rowSize = 0;

        if (null != file) {
        	
            in = new BufferedInputStream(new FileInputStream(file));

        }
        
        Workbook wb = null;
        
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        
        if("xlsx".equals(suffix)){
        	
        	wb = new XSSFWorkbook(in);
        	
        }if("xls".equals(suffix)){
        	
        	POIFSFileSystem fs = new POIFSFileSystem(in);
        	
        	wb = new HSSFWorkbook(fs); 
        }   
        
        if(wb!=null){
        	
        	Cell cell = null;
        	
        	for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
        		
        		Sheet st = wb.getSheetAt(sheetIndex);
        		
        		// 第一行为标题，不取
        		
        		for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
        			
        			Row row = st.getRow(rowIndex);
        			
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
        					
        					if(columnIndex==0){
        						
        						switch (cell.getCellType()) {
        						
        						case Cell.CELL_TYPE_STRING:
        							
        							value = cell.getStringCellValue()+"";
        							
        							break;
        							
        						case Cell.CELL_TYPE_NUMERIC:
        							
        							
        							value = Math.round(cell.getNumericCellValue()) + "";
        							
        							break;
        							
        						}
        						
        					}else{
        						// 注意：一定要设成这个，否则可能会出现乱码
        						
//	                     cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        						
        						switch (cell.getCellType()) {
        						
        						case Cell.CELL_TYPE_STRING:
        							
        							value = cell.getStringCellValue();
        							
        							break;
        							
        						case Cell.CELL_TYPE_NUMERIC:
        							
        							if (HSSFDateUtil.isCellDateFormatted(cell)) {
        								
        								Date date = cell.getDateCellValue();
        								
        								if (date != null) {
        									
        									value = new SimpleDateFormat("yyyy-MM-dd")
        											
        											.format(date);
        									
        								} else {
        									
        									value = "";
        									
        								}
        								
        							} else {
        								
        								if(cell.getNumericCellValue()==0){
        									
        									value = "";
        									
        								}else{
//                                		value = new DecimalFormat("0.0000").format(cell
//                                				
//                                				.getNumericCellValue());
        									value =  Double.parseDouble(cell.getNumericCellValue()+"")+"";
        								}
        								
        							}
        							
        							break;
        							
        						case Cell.CELL_TYPE_FORMULA:
        							
        							// 导入时如果为公式生成的数据则无值
        							
//                                if (!cell.getStringCellValue().equals("")) {
//
//                                    value = cell.getStringCellValue();
//
//                                } else {
//
//                                    value = cell.getNumericCellValue() + "";
//
//                                }
        							//需要公式算出来的值
        							try {
        								value = new DecimalFormat("0.0000").
        										format(cell.getNumericCellValue());
        							} catch (IllegalStateException e) {
        								
        								value = new DecimalFormat("0.0000").
        										format(cell.getRichStringCellValue());
        							}
        							
        							break;
        							
        						case Cell.CELL_TYPE_BLANK:
        							
        							break;
        							
        						case Cell.CELL_TYPE_ERROR:
        							
        							value = "";
        							
        							break;
        							
        						case Cell.CELL_TYPE_BOOLEAN:
        							
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
        			}
        			if (hasValue) {
        				
        				result.add(values);
        			}
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
