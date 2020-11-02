package swufe.com.bookshelf;

import android.content.Context;

public class DBManager3 {
    //因为直接从2添加那种方法，实在读出来的数据还是为空，所以
    //从表2里面读出"已读"数据
    //然后计算日期
    private DBHelper dbHelper2;
    private String TBNAME2;
    private String TBNAME3;

    public DBManager3(Context context) {
        dbHelper2 = new DBHelper(context);
        TBNAME2 = DBHelper.TB_NAME2;
        TBNAME3 = DBHelper.TB_NAME3;
    }

    //先从里面读取数据，然后可以直接返回，可以不要数据库


}
