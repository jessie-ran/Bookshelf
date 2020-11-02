package swufe.com.bookshelf;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager2 {
    private DBHelper dbHelper2;
    private String TBNAME2;
    private String TBNAME3;
    //首先定义有多少天,这是这个数组的长度.但是长度现在还不确定
    int[] daynum;
    public DBManager2(Context context) {
        dbHelper2 = new DBHelper(context);
        TBNAME2 = DBHelper.TB_NAME2;
        TBNAME3=DBHelper.TB_NAME3;
    }

    //增加数据
    public void add(RateItem2 item) {
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("curname", item.getCurName());
        //values.put("currate", item.getCurRate());
        values.put("bookN",item.getRbookN());
        values.put("author",item.getRauthor());
        values.put("State",item.getRState());
        values.put("time",item.getRtime());
        values.put("review",item.getRreview());
        values.put("phs",item.getRphs());
        db.insert(TBNAME2, null, values);
       //本来是要关闭的，但是因为我增加了功能，所以现在还不能关掉
        // db.close();
        //每增加一次书籍，首先判断item.getRstate是不是已读，这是大的前提
        //如果是，那么应该找到该用户的信息，然后判断

        String stater=item.getRState();
        if(stater.equals("已读")) {

            String time_str2 = item.getRtime();
            String time_str = time_str2.substring(0, time_str2.indexOf(" "));
            //还是用截断吧
            Cursor cursor0 = db.query(TBNAME3,
                    null,
                    //time需要进行模糊匹配，要是like不行，我们到时候再返回来更改为切断字符串进行匹配
                    "phs=? AND time=?",
                    new String[]{String.valueOf(item.getRphs()), time_str},
                    null,
                    null,
                    null);
            //也就是有这一天的,那么就先取出数据，加一，再放进去
            if (cursor0 != null) {
                String booknum = cursor0.getString(cursor0.getColumnIndex("num"));
                //把字符串转化为数字，然后更新
                try {

                    int booknum2 = Integer.parseInt(booknum);
                    booknum2 = booknum2 + 1;
                    ContentValues values2 = new ContentValues();
                    values2.put("phs", item.getRphs());
                    values2.put("time", time_str);
                    values2.put("num", String.valueOf(booknum2));
                    db.update(TBNAME3, values, "phs=? AND time=?", new String[]{String.valueOf(item.getRphs()), time_str});
                } catch (NumberFormatException e) {

                    e.printStackTrace();

                }
            }
            //也就是这一天其实是第一次有读书，那么直接插入数据
            else {
                ContentValues values3 = new ContentValues();
                values3.put("phs", item.getRphs());
                values3.put("time", time_str);
                values3.put("num", "1");
                db.insert(TBNAME3, null, values3);
            }
        }

        db.close();
    }



    public void delete(String uid,String bid){
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        db.delete(TBNAME2, "phs=? AND bookN=?", new String[]{String.valueOf(uid),String.valueOf(bid)});
        db.close();
        //删除的时候，因为待会要重新增加，所以照样的，如果状态是已读的话，应该也要把数据库里面的数据减少一
        //但是这个时候，需要再次查询吗，还有是否涉及到新的范围
        //这里先埋下一个雷

    }

    //这里是查询有多少状态，因为不想写数组，所以打算偷个懒，多调用几次
    public  String[] count(String id,String[] state1){
        int num=0;
        int cou0=0;
        int cou1=0;
        int cou2=0;
        String[]  a=new String[3];
        a[0]="0";
        a[1]="0";
        a[2]="0";
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        //这里应该是查询语句而不是删除语句
        Cursor cursor0 = db.query(TBNAME2,
                null,
                "phs=? AND State=?",
                new String[]{String.valueOf(id),String.valueOf(state1[0])},
                null,
                null,
                null);
        Cursor cursor1 = db.query(TBNAME2,
                null,
                "phs=? AND State=?",
                new String[]{String.valueOf(id),String.valueOf(state1[1])},
                null,
                null,
                null);
        Cursor cursor2= db.query(TBNAME2,
                null,
                "phs=? AND State=?",
                new String[]{String.valueOf(id),String.valueOf(state1[2])},
                null,
                null,
                null);
     if(cursor0!=null){
         cursor0.moveToFirst();
    while(!cursor0.isAfterLast()) {
        cou0=cou0+1;
        cursor0.moveToNext();
    }
    //经过测试，是有数据的，只不过getCount取出来的数据为0

  //  n=20;
   a[0]=String.valueOf(cou0);
    }

        if(cursor1!=null){
            cursor1.moveToFirst();
            while(!cursor1.isAfterLast()) {
                cou1=cou1+1;
                cursor1.moveToNext();
            }
            //经过测试，是有数据的，只不过getCount取出来的数据为0

            //  n=20;
            a[1]=String.valueOf(cou1);
        }

        if(cursor2!=null){
            cursor2.moveToFirst();
            while(!cursor2.isAfterLast()) {
                cou2=cou2+1;
                cursor0.moveToNext();
            }
            //经过测试，是有数据的，只不过getCount取出来的数据为0

            //  n=20;
            a[2]=String.valueOf(cou2);
        }
            return a;
    }


    //全部展示
    public List<RateItem2> listAll() {
        List<RateItem2> rateList = null;
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<RateItem2>();
            while(cursor.moveToNext()){
                RateItem2 item = new RateItem2();
                // item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                // item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                //item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                item.setRbookN(cursor.getString(cursor.getColumnIndex("bookN")));
                item.setRauthor(cursor.getString(cursor.getColumnIndex("author")));
                item.setRState(cursor.getString(cursor.getColumnIndex("State")));
                item.setRtime(cursor.getString(cursor.getColumnIndex("time")));
                item.setRreview(cursor.getString(cursor.getColumnIndex("review")));
                item.setRphs(cursor.getString(cursor.getColumnIndex("phs")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;
    }

    public RateItem2 findById(String uid,String bid){
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2,
                null,
                "bookN=? AND phs=?",
                new String[]{String.valueOf(bid),String.valueOf(uid)},
                null,
                null,
                null);
        RateItem2 rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new RateItem2();
            rateItem.setRphs(cursor.getString(cursor.getColumnIndex("phs")));
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setRbookN(cursor.getString(cursor.getColumnIndex("bookN")));
            rateItem.setRState(cursor.getString(cursor.getColumnIndex("State")));
            rateItem.setRtime(cursor.getString(cursor.getColumnIndex("time")));
            rateItem.setRreview(cursor.getString(cursor.getColumnIndex("review")));
            //            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            //            rateItem.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }

//这里是取出数据
//最后方便画折线图
// 输入用户的号码，应该后面能够返回一个集合，那么就依次输出，
//还是要写在这里，因为MainActivity里面没有context
//我先不写函数，如果写函数，那么返回的就应该是键值对那种模式
    public  String[][] tongji(String id) {
        String[][]  mm=null;
        int cou=0;
        int  ran=0;
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME3,
                null,
                "phs=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor!=null){
            //依次取出，依次返回
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
              //getcount似乎取不出来
              //  那就麻烦一点，再依次读取一下数据;
                cou=cou+1;
//                mm[ran][0]=cursor.getString(cursor.getColumnIndex("time"));
//                mm[ran][1]=cursor.getString(cursor.getColumnIndex("num"));
                cursor.moveToNext();
//                ran=ran+1;
            }
        }
        //换种写法，因为之前的写法，是空的
if(cou!=0){
    mm=new String[cou][cou];
    cursor.moveToFirst();
    while(!cursor.isAfterLast()) {
        //getcount似乎取不出来
        //  那就麻烦一点，再依次读取一下数据;
               mm[ran][0]=cursor.getString(cursor.getColumnIndex("time"));
                mm[ran][1]=cursor.getString(cursor.getColumnIndex("num"));
                ran=ran+1;
                cursor.moveToNext();
    }
}
       //返回日期，读书的数量
        return mm;
        //返回去第一步，判断是否为空
        //第二个数据先可以转化为int
    }
}
