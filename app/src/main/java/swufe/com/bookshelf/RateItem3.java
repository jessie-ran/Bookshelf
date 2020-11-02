package swufe.com.bookshelf;

public class RateItem3 {
    private int id;
    private String Nphs;
    private String Ntime;
    private String Nnum;
    public RateItem3() {
        super();
        Nphs="";
        Ntime="";
        Nnum="";
    }
    public RateItem3(String Nphs,String Ntime,String Nnum) {
        super();
        this.Nphs=Nphs;
        this.Ntime=Ntime;
        this.Nnum=Nnum;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNphs() {
        return Nphs ;
    }
    public void setNphs(String Nphs) {
        this.Nphs=Nphs ;
    }
    public String getNtime() {
        return Ntime ;
    }
    public void setNtime(String Ntime) {
        this.Ntime=Ntime;
    }
    public String getNnum() {
        return  Nnum;
    }
    public void setNnum(String Nnum) {
        this.Nnum=Nnum;
    }
}
