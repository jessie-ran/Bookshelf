package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
public class MainActivity5 extends AppCompatActivity {
    Date dt;

    EditText t1;
    EditText t2;
    EditText t3;
    EditText t4;
    RadioGroup rap;
    RadioButton state1, state2,state3;

    //b1是相应增加书单
    Button b1;
    String str_bookname="没有信息";
    String str_author="没有信息";
    String str_review="没有信息";
    String str_time="没有信息";
    String str_State="没有信息";
    String str_phs="没有消息";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        t1=findViewById(R.id.editText3);
        t2=findViewById(R.id.editText);
        t3=findViewById(R.id.editText2);

        //第三步骤，调用函数
        //判断是不是通过点击事件过来的，判断是否有buddle
        //如果没有，那就不用显示，如果有，再做下面的操作
        //似乎不用上面的方法，直接用默认内容
        //不管如何，先把用户的ID读出来
        SharedPreferences sharedPreferences = getSharedPreferences("usermess", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        str_phs=sharedPreferences.getString("userid","no one");
        //然后另一个关键字（这里先这样说）是书的名字

        //然后判断bookname是否等于no
        RateItem2 rateItem = new RateItem2();
        Intent intent=getIntent();
        //先从上一个页面得到，然后再从这个页面取出

        SharedPreferences sharedPreferences3 = getSharedPreferences("addboo", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        str_bookname=sharedPreferences3.getString("bookname","no");

        if(str_bookname.equals("new")==false){
            //感觉没有办法，去判断，是不是空，要不然还是写在XML文件里

            SharedPreferences sharedPreferences2 = getSharedPreferences("bookmess", Activity.MODE_PRIVATE);
            PreferenceManager.getDefaultSharedPreferences(this);
            str_bookname=sharedPreferences2.getString("bookname","no");
            str_author=sharedPreferences2.getString("author","no");
            //如果不是等于no的时候，那么就应该是从数据库里面获取
            DBManager2 dbManager = new DBManager2(MainActivity5.this);
            rateItem=dbManager.findById(str_phs,str_bookname);
            if(rateItem!=null) {
                str_review = rateItem.getRreview();
                t3.setText(str_review);
                t1.setText(str_bookname);
                t2.setText(str_author);

            }

        }

    }

    public void open(View v){
        t1=findViewById(R.id.editText3);
        str_bookname=t1.getText().toString();
        if(str_bookname.equals("")){
            Toast.makeText(MainActivity5.this, "还没有输入要搜索的书籍", Toast.LENGTH_LONG).show();
        }
        else {
            Intent op = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.douban.com/search?cat=1001&q=" + str_bookname));
            startActivity(op);
        }
    }

    public  void pushdata(View v){
        dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
        str_time = sdf.format(dt);
        //以上，时间时可以成功获取的
        //然后就应该是·增添数据到数据库里面
        //增添的时候传入的是：add(RateItem item)
        //第一步：获取获取数据
        //先把buttongroup搞出来
        rap=findViewById(R.id.radioGroupId);
        state1=findViewById(R.id.wantButtonId);
        state2=findViewById(R.id.doButtonId);
        state3=findViewById(R.id.didButtonId);

        RadioButton ra1=(RadioButton)findViewById(rap.getCheckedRadioButtonId());
        str_State=ra1.getText().toString();
        //ml文件：usermess中得到用户的电话号码：userid
        SharedPreferences sharedPreferences = getSharedPreferences("usermess", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        str_phs=sharedPreferences.getString("userid","no one");
        //用户ID是可以取出来的
        //        float a1= sharedPreferences.getFloat("dollar_rate",0.0f);

        t1=findViewById(R.id.editText3);
        t2=findViewById(R.id.editText);
        t3=findViewById(R.id.editText2);

        //这个button是去相应修改，或者增添数据。反正到时候直接判断，有就先删除，再插入
        b1=findViewById(R.id.button3);
        str_bookname=t1.getText().toString();
        str_author=t2.getText().toString();
        str_review=t3.getText().toString();

        DBManager2 dbm=new DBManager2(MainActivity5.this);
        List<RateItem2> rateList = new ArrayList<RateItem2>();

        RateItem2 rat = new RateItem2();
        rat.setRbookN(str_bookname);
        rat.setRauthor(str_author);
        rat.setRState(str_State);
        rat.setRtime(str_time);
        rat.setRreview(str_review);
        rat.setRphs(str_phs);

        //首先判断有没有，有的话先删除
        DBManager2 dbManager2 = new DBManager2(MainActivity5.this);
        dbManager2.delete(str_phs,str_bookname);


        //第二部放入item
        RateItem2 rateItem = new RateItem2(str_bookname,str_author,str_State,str_time,str_review,str_phs);
        //首先，通过书名和用户电话号码，判断是否有这条信息，有的话需要提前删除
        rateList.add(rateItem);
        DBManager2 dbManager = new DBManager2(MainActivity5.this);
        dbManager.add(rateItem);
        Log.i("db","添加新记录集");

        // rateList.add();
        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);

    }

}