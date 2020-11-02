package swufe.com.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import swufe.com.bookshelf.*;

public class MainActivity4 extends AppCompatActivity implements AdapterView.OnItemClickListener {
//这里要开始做列表和做下拉框  √
    //做书架的展示，连接数据库
    //增加新的书籍和修改原来的内容都可以是一个页面，这里指都可以是5
//用户的id，也就是电话号码，需要从别的地方传送过来

    private static final String TAG = "MainActivity4";
    ListView listview;
    ArrayList<HashMap<String,String>> listItems;
    SimpleAdapter listItemAdapter;

    TextView greet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        greet=findViewById(R.id.gennied);

        Intent intent=getIntent();
        String userPh=intent.getStringExtra("userPh");
        //首先，要去数据表里面去查找用户的名字，然后输出
        //然后根据用户的书本的数据库去解决输出内容的问题
        ////ml文件：usermess中得到用户的电话号码：userid
        SharedPreferences sharedPreferences = getSharedPreferences("usermess", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        String  str_phs=sharedPreferences.getString("userid","no one");

        String userName1=sharedPreferences.getString("uname","no one");;
        DBManager2 uService = new DBManager2(MainActivity4.this);
       // String userName1= uService(userPh);


        greet.setText("Bonjour,"+userName1);

        //直接从数据库里面提取出来进行展示
        List<String> retList = new ArrayList<String>();
        DBManager2 dbManager2 = new DBManager2(MainActivity4.this);
//展示的时候应该区分用户，
        listItems = new ArrayList<HashMap<String, String>>();
        for(RateItem2 rateItem : dbManager2.listAll()){
            HashMap<String, String> map = new HashMap<String, String>();
            //            //"ItemTime", "ItemStates","ItemTitle","ItemAuthor"
            map.put("ItemTime", rateItem.getRtime()); // 标题文字
            map.put("ItemStates",rateItem.getRState()); // 详情描述
            map.put("ItemTitle",rateItem.getRbookN());
            map.put("ItemAuthor",rateItem.getRauthor());
            listItems.add(map);
//            retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
        }


     /*   listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            //"ItemTime", "ItemStates","ItemTitle","ItemAuthor"
            map.put("ItemTime", "Time： " + i); // 标题文字
            map.put("ItemStates", "States" + i); // 详情描述
            map.put("ItemTitle","Title"+i);
            map.put("ItemAuthor","Author"+i);
            listItems.add(map);
        }

      */

        listview = (ListView) findViewById(R.id.my_list);
        //  List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
        listItemAdapter = new SimpleAdapter(MainActivity4.this,
                listItems , //listItem数据源
                R.layout.hangbuju,
                //ListItem的XML布局实现
                new String[]{"ItemTime", "ItemStates","ItemTitle","ItemAuthor"},
                //数据的key
                new int[]{R.id.itemTime, R.id.itemStates,R.id.itemTitle,R.id.itemAuthor}//布局里的id，k与id一一匹配
        );
        //listview.setAdapter(myAdapter);
        listview.setAdapter(listItemAdapter);
        listview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //打开新的界面，传递参数,这里还要传过去的还有书名，那么在塞入数据库的时候应该和id一起
        Intent intent=getIntent();
        String userPh=intent.getStringExtra("userPh");
        //以书名作为关键字

        //通过view获取数据，这里好像有问题，因为得到的应该是行布局里面的内容
        TextView title=view.findViewById(R.id.itemTitle);
        TextView author=view.findViewById(R.id.itemAuthor);
        TextView states=view.findViewById(R.id.itemStates);
        String title2=String.valueOf(title.getText());
        String author2=String.valueOf(author.getText());
        String state2=String.valueOf(states.getText());

        Intent  intent2=new Intent(this,MainActivity5.class);
        //因为后面要进一步进行判断，所以这里就放在xml文件里面
        //把书名写到xml文件里面
        SharedPreferences sharedPreferences = getSharedPreferences("bookmess", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor2= sharedPreferences.edit();
        editor2.putString("bookname",title2);
        editor2.putString("author",author2);
        //editor.putFloat("euro_rate",euroRate);
        //editor.putFloat("won_rate",wonRate);
        editor2.commit();
        //依次按照页面设计，放入的是：事件，状态，书名，作者
        //好像XML文件会自己后来重复的写入,所以试试看bundle
        //放在一个盒子里面，然后传过去,接受到更改
        Bundle bdl = new Bundle();
        bdl.putString("bkn","on");
        // bdl.putFloat("b1", bd);
        //bdl.putFloat("b2", be);
        //bdl.putFloat("b3", bw);
        intent2.putExtras(bdl);
        startActivity(intent2);

    }

    //新增书单的button
    public void addbook(View v){
        Intent intent = new Intent(this, MainActivity6.class);
        startActivityForResult(intent, 2);
        //这里的增加之后的展示
        //简单一些，直接从上一个页面获取
        //那么上一个页面直接输入到数据库中
        //但是这样的话，不知道会不会展示两次
        SharedPreferences sharedPreferences = getSharedPreferences("addboo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor2= sharedPreferences.edit();
        editor2.putString("bookname","new");

    }
    public  void  Statistics(View v){
        //直接打开新的页面，在新的页面去从文件得到用户的id,然后统计
        Intent intent = new Intent(this, MainActivity7.class);
        startActivity(intent);
    }

}