package swufe.com.bookshelf;

public class RateItem {
    private String ph;
    private String userName;
    private String password;
    private String sex;

    public RateItem() {
        super();
        ph="";
        userName="";
        password="";
        sex="";
    }

    public  RateItem(String ph,String userName, String password, String sex) {
        super();
        this.ph=ph;
        this.userName = userName;
        this.password = password;
        this.sex = sex;
    }
    public String getPh() {
        return ph;
    }
    public void setPh(String PH) {
        this.ph = ph;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }


}
