import zhd.oa.middleware.utils.CommandUtil;

public class TestShell {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String result = CommandUtil.executeShell("erp");
		System.out.println("shell result:>>" + result);
	}

}
