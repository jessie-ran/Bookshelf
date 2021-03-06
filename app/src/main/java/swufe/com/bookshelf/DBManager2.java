package swufe.com.bookshelf;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBManager2 {
    private DBHelper dbHelper2;
    private String TBNAME2;
    private String TBNAME3;
    //首先定义有多少天,这是这个数组的长度.但是长度现在还不确定
    int[] daynum;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dmk;
    Date  dmk_f;
    public DBManager2(Context context) {
        dbHelper2 = new DBHelper(context);
        TBNAME2 = DBHelper.TB_NAME2;
        TBNAME3 = DBHelper.TB_NAME3;
    }

    //增加数据
    public void add(RateItem2 item) {
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("curname", item.getCurName());
        //values.put("currate", item.getCurRate());
        values.put("bookN", item.getRbookN());
        values.put("author", item.getRauthor());
        values.put("State", item.getRState());
        values.put("time", item.getRtime());
        values.put("review", item.getRreview());
        values.put("phs", item.getRphs());
        db.insert(TBNAME2, null, values);
        //本来是要关闭的，但是因为我增加了功能，所以现在还不能关掉
        // db.close();
        //每增加一次书籍，首先判断item.getRstate是不是已读，这是大的前提
        //如果是，那么应该找到该用户的信息，然后判断
        String phstri=item.getRphs();
        String stater = item.getRState();
        String time_str2 = item.getRtime();

        if (stater.equals("已读")) {
         //   String time_str = time_str2.substring(0, time_str2.indexOf(" "));
            String time_str = time_str2;
            //还是用截断吧
            Cursor cursor0 = db.query(TBNAME3,
                    null,
                    //time需要进行模糊匹配，要是like不行，我们到时候再返回来更改为切断字符串进行匹配
                    "phs=? AND time=?",
                    new String[]{phstri, time_str},
                    null,
                    null,
                    null);
            //也就是有这一天的,那么就先取出数据，加一，再放进去
            if (cursor0 != null) {
                String booknum = cursor0.getString(cursor0.getColumnIndex("num"));
                //把字符串转化为数字，然后更新
           //     try {
                    int booknum2 = Integer.parseInt(booknum);
                    booknum2 = booknum2 + 1;
                    ContentValues values2 = new ContentValues();
                    values2.put("phs", phstri);
                    values2.put("time", time_str);
                    values2.put("num", String.valueOf(booknum2));
                    db.update(TBNAME3, values, "phs=? AND time=?", new String[]{String.valueOf(item.getRphs()), time_str});
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
            }
            //也就是这一天其实是第一次有读书，那么直接插入数据
            else {
                ContentValues values3 = new ContentValues();
                values3.put("phs", phstri);
                values3.put("time", time_str);
                values3.put("num", "1");
                db.insert(TBNAME3, null, values3);
            }
        }

        db.close();
    }


    public void delete(String uid, String bid) {
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        db.delete(TBNAME2, "phs=? AND bookN=?", new String[]{String.valueOf(uid), String.valueOf(bid)});
        db.close();
        //删除的时候，因为待会要重新增加，所以照样的，如果状态是已读的话，应该也要把数据库里面的数据减少一
        //但是这个时候，需要再次查询吗，还有是否涉及到新的范围
        //这里先埋下一个雷

    }

    //这里是查询有多少状态，因为不想写数组，所以打算偷个懒，多调用几次
    public String[] count(String id, String[] state1) {
        int num = 0;
        int cou0 = 0;
        int cou1 = 0;
        int cou2 = 0;
        String[] a = new String[3];
        a[0] = "0";
        a[1] = "0";
        a[2] = "0";
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        //这里应该是查询语句而不是删除语句
        Cursor cursor0 = db.query(TBNAME2,
                null,
                "phs=? AND State=?",
                new String[]{String.valueOf(id), String.valueOf(state1[0])},
                null,
                null,
                null);
        Cursor cursor1 = db.query(TBNAME2,
                null,
                "phs=? AND State=?",
                new String[]{String.valueOf(id), String.valueOf(state1[1])},
                null,
                null,
                null);
        Cursor cursor2 = db.query(TBNAME2,
                null,
                "phs=? AND State=?",
                new String[]{String.valueOf(id), String.valueOf(state1[2])},
                null,
                null,
                null);
        if (cursor0 != null) {
            cursor0.moveToFirst();
            while (!cursor0.isAfterLast()) {
                cou0 = cou0 + 1;
                cursor0.moveToNext();
            }
            //经过测试，是有数据的，只不过getCount取出来的数据为0

            //  n=20;
            a[0] = String.valueOf(cou0);
            cursor0.close();
        }

        if (cursor1 != null) {
            cursor1.moveToFirst();
            while (!cursor1.isAfterLast()) {
                cou1 = cou1 + 1;
                cursor1.moveToNext();
            }
            //经过测试，是有数据的，只不过getCount取出来的数据为0

            //  n=20;
            a[1] = String.valueOf(cou1);
            cursor1.close();
        }

        if (cursor2 != null) {
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {
                cou2 = cou2 + 1;
                cursor0.moveToNext();
            }
            //经过测试，是有数据的，只不过getCount取出来的数据为0

            //  n=20;
            a[2] = String.valueOf(cou2);
            cursor2.close();
        }

        db.close();
        return a;
    }


    //全部展示
    public List<RateItem2> listAll(String id) {
        List<RateItem2> rateList = null;
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2,
                null,
                "phs=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if (cursor != null) {
            rateList = new ArrayList<RateItem2>();
            while (cursor.moveToNext()) {
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

    public RateItem2 findById(String uid, String bid) {
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2,
                null,
                "bookN=? AND phs=?",
                new String[]{String.valueOf(bid), String.valueOf(uid)},
                null,
                null,
                null);
        RateItem2 rateItem = null;
        if (cursor != null && cursor.moveToFirst()) {
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
    public List<RateItem3> tongji(String id){
        List<RateItem3> rateList = null;
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME3,
                null,
                "phs=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
//因为需要返回键值对，所以我写了RateItem3

        RateItem3 item = null;
        if(cursor!=null){
            rateList = new ArrayList<RateItem3>();
        while(cursor.moveToNext()){
                item = new RateItem3();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setNphs(cursor.getString(cursor.getColumnIndex("phs")));
                item.setNtime(cursor.getString(cursor.getColumnIndex("time")));
                item.setNnum(cursor.getString(cursor.getColumnIndex("num")));
//               // item.setNnum(cursor.toString(cursor.getColumnIndex("num")));
            rateList.add(item);
          }
            cursor.close();
        }
        db.close();
        return rateList;
    }


    //展示一部分
    //全部展示
    public List<RateItem2> listAll2() {
        List<RateItem2> rateList = null;
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2,
                null,
                null,
               null,
                null,
                null,
                null);
        if (cursor != null) {
            rateList = new ArrayList<RateItem2>();
            while (cursor.moveToNext()) {
                RateItem2 item = new RateItem2();
                // item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                // item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                //item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                item.setRbookN(cursor.getString(cursor.getColumnIndex("bookN")));
                item.setRtime(cursor.getString(cursor.getColumnIndex("time")));
                item.setRreview(cursor.getString(cursor.getColumnIndex("review")));
                item.setRauthor(cursor.getString(cursor.getColumnIndex("author")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;
    }


    //我就不信，数据读不出来
    //重新写统计函数，上个统计函数要么返回有错误，要么就是一开始插入数据库那里字符串截取出错
    //参照一下四，她也是返回一些值
    public  float ss(String id,String timeyd) {
        String m=null;
        float nk=0.0f;
        int cou0=0;
        //查询，然后计算分数，返回，展示
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        //这里应该是查询语句而不是删除语句
        //时间模糊匹配的话，应该匹配年月，至于几号，就不用管了
        //模糊查询不知道对不对
        Cursor cursor0 = db.query(TBNAME2,
                null,
                "phs=? AND State=? AND time like ?",
                new String[]{String.valueOf(id), "已读",timeyd+'%'},
                null,
                null,
                null);
//依次计算，
String tn=timeyd+"-01";
//tn,只是代表这个月，
        String t=null;
        float mk=0.0f;
        float fmk=0.0f;
        dmk_f= new Date();
        long begin;

        dmk= new Date();

        long end;
        //dmk是有到今天
        if (cursor0 != null) {
            cursor0.moveToFirst();

            while (!cursor0.isAfterLast()) {
                cou0 = cou0 + 1;
                t=cursor0.getString(cursor0.getColumnIndex("time"));
                 try {
                     dmk_f=simpleDateFormat.parse(tn);
                     begin=dmk_f.getTime();
                     dmk=simpleDateFormat.parse(t);
                    //接下来计算是否相差一，第一条数据就和该月的1号进行计算
                     //现在的格式：yyyy-MM-dd，或者直接截取号数，但是月初都是有0的
                     //所以一开始就直接看看是几号，然后依次循环
                     end=dmk.getTime();
                     mk=(float) ((end - begin) / (1000 * 3600 * 24));
                     //这里判断相差几天
                     fmk=fmk+mk+1;
                     begin=end;
                       } catch (ParseException e) {
                           e.printStackTrace();
                     }
               //
                //现在dmk就变成了时间格式

                cursor0.moveToNext();
            }
            //字符串转化为日期
            // Date date = simpleDateFormat.parse("2019-10-31");
            //经过测试，是有数据的，只不过getCount取出来的数据为0

            //  n=20;
            cursor0.close();
        }
        nk= (float) (cou0*0.5+fmk*0.143);
        return nk;
    }

}