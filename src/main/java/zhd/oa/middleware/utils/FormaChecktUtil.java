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
		
		int checkRes = 0;
		String[] datas = data.split("\\$");
		for (int i = 0; i < datas.length; i++) {
			String[] kpi = datas[i].split("\\|");
			if("0".equals(type)&&kpi.length!=20){
				checkRes  =  checkRes + 1;
			}if("1".equals(type)&&kpi.length!=8){
				checkRes  =  checkRes + 1;
			}if("2".equals(type)&&kpi.length!=12){
				checkRes  =  checkRes + 1;
			}if("3".equals(type)&&kpi.length!=20){
				checkRes  =  checkRes + 1;
			}
		}
		
		return checkRes;
		
	}
	

}
