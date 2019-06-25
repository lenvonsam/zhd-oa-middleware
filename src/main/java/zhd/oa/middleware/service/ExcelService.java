package zhd.oa.middleware.service;

import zhd.oa.middleware.mapper.ExcelInsertMapper;

public class ExcelService extends BaseService{
	
	private ExcelInsertMapper excelInsertMapper = null;
	
	public int excelInsert(String datee, String outdate, String acceptNo){
		
		int res = 0;
		
		try {
			session = openSession();
			excelInsertMapper = session.getMapper(ExcelInsertMapper.class);
			res = excelInsertMapper.insertExcelContent(datee, outdate, acceptNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}	
		return res;
	
	}
	
	
//	  public static void main(String[] args) throws Exception {
//		  
//		  ExcelService excelService = new ExcelService();
//		  
//		  System.out.println("is coming!");
//
//	       File file = new File("C:\\Users\\conqueror\\Desktop\\Office\\Excel.xls");
//
//	       
//	       String[][] result = ReadExcelUtil.shareInstance().getData(file, 1);
//	       
//	      // System.out.println("--"+result[0][5]);
//
//	       int rowLength = result.length;
////	       System.out.println("rowLength"+rowLength);
//
//	       for(int i=0;i<rowLength;i++) {
//	    	   
//	    	   String outdate = result[i][3];
//	    	   String datee = result[i][4];
//	    	   String acceptNo = result[i][1];
//	    	   
//	    	   
//	    	  // int res = excelService.excelInsert(datee, outdate, acceptNo);
//	    	   
//	    	   System.out.println(acceptNo);
//	    	   System.out.println(datee);
//	    	   System.out.println(outdate);
//	    	  // System.out.println(res);
//	    	   
//	           for(int j=0;j<result[i].length;j++) {
//
//	              //System.out.print(result[i][j]+"\t\t");
//	           }
//	           System.out.println();
//	       }
//	  }
//	

	
	
}
