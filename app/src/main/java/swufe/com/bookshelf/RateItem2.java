package swufe.com.bookshelf;

public class RateItem2 {
    private int id;
    private String RbookN;
    private String Rauthor;
    private String RState;
    private String Rtime;
    private String Rreview;
    private String Rphs;

    public RateItem2() {
        super();
        RbookN="";
        Rauthor="";
        RState="";
        Rtime="";
        Rreview="";
        Rphs="";
    }


    public RateItem2(String RbookN,String Rauthor,String RState,String Rtime,String Rreview,String Rphs) {
        super();
        this.RbookN=RbookN;
        this.Rauthor=Rauthor;
        this.RState=RState;
        this.Rtime=Rtime;
        this.Rreview=Rreview;
        this.Rphs=Rphs;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRbookN() {
        return  RbookN;
    }
    public void setRbookN(String RbookN) {
        this.RbookN=RbookN ;
    }

    public String getRauthor() {
        return Rauthor;
    }
    public void setRauthor(String Rauthor) {
        this.Rauthor= Rauthor;
    }

    public String getRState() {
        return  RState;
    }
    public void setRState(String  RState) {
        this. RState= RState ;
    }

    public String getRtime() {
        return Rtime;
    }
    public void setRtime(String Rtime) {
        this.Rtime=Rtime ;
    }

    public String getRreview() {
        return Rreview;
    }
    public void setRreview(String Rreview) {
        this.Rreview=Rreview ;
    }

    public String getRphs() {
        return Rphs;
    }
    public void setRphs(String Rphs) {
        this.Rphs=Rphs ;
    }
}


