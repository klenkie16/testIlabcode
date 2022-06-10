package reporting;

public class ReportBean {

    private static int TESTCASENO=0;
    private static int RESULT=1;
    private static int REASONFORFAILURE=2;

    public static int getTESTCASENO() {
        return TESTCASENO;
    }

    public static void setTESTCASENO(int TESTCASENO) {
        ReportBean.TESTCASENO = TESTCASENO;
    }

    public static int getRESULT() {
        return RESULT;
    }

    public static void setRESULT(int RESULT) {
        ReportBean.RESULT = RESULT;
    }

    public static int getREASONFORFAILURE() {
        return REASONFORFAILURE;
    }

    public static void setREASONFORFAILURE(int REASONFORFAILURE) {
        ReportBean.REASONFORFAILURE = REASONFORFAILURE;
    }
}
