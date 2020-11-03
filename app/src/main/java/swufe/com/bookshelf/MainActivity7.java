package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity7 extends AppCompatActivity {
//
ListView listview;
    ArrayList<HashMap<String,String>> listItems;
    SimpleAdapter listItemAdapter;

    TextView greet;
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
       t_want.setText(cou_did[2]);
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

        LineChart line;
        int booknum=0;
        List<Entry> list=new ArrayList<>();          //实例化一个 List  用来保存你的数据
        line = (LineChart) findViewById(R.id.line);

                 //取出来了，那么数据应该如何依次取出
        int kk=0;

        for(RateItem3 rateItem : dbManager.tongji(str_phs)) {
            String m=rateItem.getNnum();
            int b=Integer.parseInt(m);
            list.add(new Entry(kk,b));
            kk=kk+1;
        }
        t_want.setText(String.valueOf(kk));
        String[][] mm=new  String[kk][2];
            //画图
//重头再来  //listItems数据源
    /*   List<String> retList = new ArrayList<String>();
          DBManager2 dbManager3= new DBManager2(MainActivity7.this);
              listItems = new ArrayList<HashMap<String, String>>();
               for(RateItem3 rateItem : dbManager3.tongji(str_phs)){
                  HashMap<String, String> map = new HashMap<String, String>();
                  map.put("ItemTime", rateItem.getNnum); // 标题文字
                  map.put("ItemStates",rateItem.getNtime)); // 详情描述
                  listItems.add(map);
                }


     */


        //这个数据是用来测试的，结果表明，没有数据
        //返回去检查建表是否发生了错误
        //返回去检查的地方：建表，插入数据，特别是截取字符串的地方
        //然后赋值的地方
        list.add(new Entry(1,1));
        list.add(new Entry(2,2));
        list.add(new Entry(3,1));
        list.add(new Entry(4,3));
        //list是你这条线的数据  "语文" 是你对这条线的描述（也就是图例上的文字）
        LineDataSet lineDataSet=new LineDataSet(list,"已读");
        LineData lineData=new LineData(lineDataSet);
        line.setData(lineData);

        //简单美化
        // X轴所在位置   默认为上面
        line.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏右边的Y轴
        line.getAxisRight().setEnabled(false);

    }
}