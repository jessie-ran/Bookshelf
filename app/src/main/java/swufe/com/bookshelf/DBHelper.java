package swufe.com.bookshelf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //数据库名称
    private static final String DATABASE_NAME="jessie.db";
    //数据库版本号
    private static final int dbVersion=1;
    //数据库SQL语句 添加一个表
    public static final String TB_NAME2 = "bookShelf";
    public static final String TB_NAME = "userinfo";
    public static final String TB_NAME3 = "numinfo";
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public  DBHelper(Context context) {

        super(context,DATABASE_NAME , null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //表的内容有：电话、昵称、密码、性别
        db.execSQL("CREATE TABLE "+TB_NAME+"( ph TEXT PRIMARY KEY,userName TEXT,password TEXT,sex TEXT)");
        //db.execSQL(sql);
        //听说varchar类型简单的定义长度，但是如果超过长度不会被截断
        //这里声明外键的问题，本来是：用户表，书单表，书架表，但是由于缺少从网络爬取的数据，所以书单表没有，所以不需要利用外键
        //但是在存储的时候，需要每次都手动存储用户ID
        db.execSQL("CREATE TABLE "+TB_NAME2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,bookN TEXT,author TEXT,State TEXT,time TEXT,review TEXT,phs TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME3+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,phs TEXT,time TEXT,num TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
