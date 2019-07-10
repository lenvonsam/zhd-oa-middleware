package zhd.oa.middleware.utils;

public class FormaChecktUtil {
	
	private static FormaChecktUtil instance = null;

	public FormaChecktUtil() {
	}
	
	public static FormaChecktUtil shareInstance() {
		if(instance==null){
			instance = new FormaChecktUtil();
		}
		
		return instance;
	}
	
	
	public int checkDatas(String data , String type){
		int checkRes = 1;
		String[] datas = data.split("\\$");
		for (int i = 0; i < datas.length; i++) {
			String[] kpi = datas[i].split("\\|");
			//0营销中心 1型云公公司 2仓储部门3营销采购部门
			if("0".equals(type)&&kpi.length==20){
				try{
					Double.parseDouble(kpi[3].equals("")?"0":kpi[3]);
					Double.parseDouble(kpi[4].equals("")?"0":kpi[4]);
					Double.parseDouble(kpi[5].equals("")?"0":kpi[5]);
					Double.parseDouble(kpi[6].equals("")?"0":kpi[6]);
					Double.parseDouble(kpi[7].equals("")?"0":kpi[7]);
					Double.parseDouble(kpi[8].equals("")?"0":kpi[8]);
					Double.parseDouble(kpi[9].equals("")?"0":kpi[9]);
					Double.parseDouble(kpi[10].equals("")?"0":kpi[10]);
					Double.parseDouble(kpi[11].equals("")?"0":kpi[11]);
					Double.parseDouble(kpi[12].equals("")?"0":kpi[12]);
					Double.parseDouble(kpi[13].equals("")?"0":kpi[13]);
					Double.parseDouble(kpi[14].equals("")?"0":kpi[14]);
					Double.parseDouble(kpi[15].equals("")?"0":kpi[15]);
					Double.parseDouble(kpi[17].equals("")?"0":kpi[17]);
					Double.parseDouble(kpi[19].equals("")?"0":kpi[19]);

					checkRes = 0;
					
				}catch(Exception e){
					checkRes = 1;
				}
			}if("1".equals(type)&&kpi.length==8){
				try{
					Double.parseDouble(kpi[3].equals("")?"0":kpi[3]);
					Double.parseDouble(kpi[5].equals("")?"0":kpi[5]);
					Double.parseDouble(kpi[7].equals("")?"0":kpi[7]);
					checkRes = 0;
				}catch(Exception e){
					checkRes = 1;
				}
			}if("2".equals(type)&&kpi.length==12){
				try{
					Double.parseDouble(kpi[4].equals("")?"0":kpi[4]);
					Double.parseDouble(kpi[6].equals("")?"0":kpi[6]);
					Double.parseDouble(kpi[8].equals("")?"0":kpi[8]);
					Double.parseDouble(kpi[9].equals("")?"0":kpi[9]);
					Double.parseDouble(kpi[11].equals("")?"0":kpi[11]);
					checkRes = 0;
				}catch(Exception e){
					checkRes = 1;
				}
			}if("3".equals(type)&&kpi.length==20){
				try{
					Double.parseDouble(kpi[3].equals("")?"0":kpi[3]);
					Double.parseDouble(kpi[4].equals("")?"0":kpi[4]);
					Double.parseDouble(kpi[5].equals("")?"0":kpi[5]);
					Double.parseDouble(kpi[6].equals("")?"0":kpi[6]);
					Double.parseDouble(kpi[7].equals("")?"0":kpi[7]);
					Double.parseDouble(kpi[8].equals("")?"0":kpi[8]);
					Double.parseDouble(kpi[9].equals("")?"0":kpi[9]);
					Double.parseDouble(kpi[10].equals("")?"0":kpi[10]);
					Double.parseDouble(kpi[11].equals("")?"0":kpi[11]);
					Double.parseDouble(kpi[12].equals("")?"0":kpi[12]);
					Double.parseDouble(kpi[13].equals("")?"0":kpi[13]);
					Double.parseDouble(kpi[14].equals("")?"0":kpi[14]);
					Double.parseDouble(kpi[15].equals("")?"0":kpi[15]);
					Double.parseDouble(kpi[17].equals("")?"0":kpi[17]);
					Double.parseDouble(kpi[19].equals("")?"0":kpi[19]);
					checkRes = 0;
				}catch(Exception e){
					checkRes = 1;
				}
			}
		}
		return checkRes;
		
	}
	
	public static boolean checkFormat(Object... o){
		
		boolean resFormat = false;
		
		for (int i = 0; i < o.length; i++) {
			System.out.println(o[i]);
			System.out.println(o[i] instanceof Double);
			if(o[i] instanceof Double){
				resFormat = true;
			}
		}
		
		return resFormat;
	}
	

}
