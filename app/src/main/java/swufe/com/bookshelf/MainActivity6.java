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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MainActivity6 extends AppCompatActivity {


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
        setContentView(R.layout.activity_main6);
//用来传入新的书籍

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

        DBManager2 dbm=new DBManager2(MainActivity6.this);
        List<RateItem2> rateList = new ArrayList<RateItem2>();

        RateItem2 rat = new RateItem2();
        rat.setRbookN(str_bookname);
        rat.setRauthor(str_author);
        rat.setRState(str_State);
        rat.setRtime(str_time);
        rat.setRreview(str_review);
        rat.setRphs(str_phs);

        //首先判断有没有，有的话先删除
        DBManager2 dbManager2 = new DBManager2(MainActivity6.this);
        dbManager2.delete(str_phs,str_bookname);


        //第二部放入item
        RateItem2 rateItem = new RateItem2(str_bookname,str_author,str_State,str_time,str_review,str_phs);
        //首先，通过书名和用户电话号码，判断是否有这条信息，有的话需要提前删除
        rateList.add(rateItem);
        DBManager2 dbManager = new DBManager2(MainActivity6.this);
        dbManager.add(rateItem);
        Log.i("db","添加新记录集");

        // rateList.add();
        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }

    public void open(View v){
        t1=findViewById(R.id.editText3);
        str_bookname=t1.getText().toString();
        if(str_bookname.equals("")){
            Toast.makeText(MainActivity6.this, "还没有输入要搜索的书籍", Toast.LENGTH_LONG).show();
        }
        else {
            Intent op = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.douban.com/search?cat=1001&q=" + str_bookname));
            startActivity(op);
        }
    }
}