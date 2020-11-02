package swufe.com.bookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void register(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ph",item.getPh());
        values.put("userName",item.getUserName());
        values.put("password",item.getPassword());
        values.put("sex",item.getSex());
        db.insert(TBNAME, null, values);
        db.close();
    }
    public RateItem findById(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ph=?", new String[]{String.valueOf(id)}, null, null, null);
        RateItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new RateItem();
            rateItem.setPh(cursor.getString(cursor.getColumnIndex("ph")));
            rateItem.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            rateItem.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            rateItem.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }

}


