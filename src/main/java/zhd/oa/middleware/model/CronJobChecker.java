package zhd.oa.middleware.model;

public class CronJobChecker {
    private String res ;
    private String emps ;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getEmps() {
        return emps;
    }

    public void setEmps(String emps) {
        this.emps = emps;
    }

    @Override
    public String toString() {
        return "CronJobChecker{" +
                "res='" + res + '\'' +
                ", emps='" + emps + '\'' +
                '}';
    }
}
