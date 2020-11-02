package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.preference.PreferenceManager;
public class MainActivity7 extends AppCompatActivity {
//
    TextView t_did;
    TextView t_doing;
    TextView t_want;
    TextView t1;
    TextView t2;
    TextView t3;
    String str_want;
    String str_doing;
    String str_did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);


        //从数据库里面拿出数据
        SharedPreferences sharedPreferences = getSharedPreferences("usermess", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        String str_phs=sharedPreferences.getString("userid","no one");

        t_did=findViewById(R.id.textView4);
        t_doing=findViewById(R.id.textView10);
        t_want=findViewById(R.id.textView12);
        t1=findViewById(R.id.textView3);
        t2=findViewById(R.id.textView9);
        t3=findViewById(R.id.textView11);

        str_did=t_did.getText().toString();
        str_doing=t_doing.getText().toString();
        str_want=t_want.getText().toString();
        //下面应该是从数据库里面统计得到数据，然后输出
        //首先要拿到用户的id

       String[] a={"已读","在读","想读"};

        DBManager2 dbManager = new DBManager2(MainActivity7.this);
        String[] cou_did=dbManager.count(str_phs,a);
      //上面换成  str_did  就跑不出来
        t_did.setText(cou_did[0]);
        t_doing.setText(cou_did[1]);
      //  t_want.setText(cou_did[2]);
        //偷懒是不行的，还是要换成数组
        //因为他会不显示

//下面是关于统计显示的问题：
        //那么目前我们需要解决的问题是：
        //1、要不要新建一张表作为数据，
        //读一本书肯定是是以周为单位，但是现在问题是，项目剩下的时间不到一周，所以以天为单位.到时候需要改的话，再改一改就行
        //折线图要能够滑动
        //建表的好处在于之后的数据不用每次运行的时候再去计算，emmmm。还是建张表吧。
        //每次只要是更改数据，改为已读状态，那就修改这张有：用户Id，时间，数量的表。为了方便，还是再建立一个ID
        //也可以直接查数据库，每次模糊匹配
        //当数据量一旦增长，那么运行时间必然会变得很长，所以还是数据库好一点
        //数据库里面的为ID,phs,time,num
        //先把相同phs取出来，作为临时表，然后依次取出数据
        //已经·取了出来
        DBManager2 dbManager2 = new DBManager2(MainActivity7.this);
        String[][] mm=dbManager2.tongji(str_phs);
        //测试表明，数据是空的，也就是说mm是空的
        if(mm!=null){
            //也就是说，这个人，他是有值存在于数据库里面的
           //
          t_want.setText(mm[0][0]);
        }
        //用来测试
        else {
            t_want.setText("mm返回为空");
        }
    }
}