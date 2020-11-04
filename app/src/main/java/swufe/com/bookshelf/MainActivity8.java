package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

//这是社区功能
//其实在数据库书架里面应该增加一条，收集了多少赞的列
public class MainActivity8 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayList<HashMap<String,String>> listItems;
    SimpleAdapter listItemAdapter;

    TextView greet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        //就是从数据库里面取出所有的值，然后展示
        //参照4

        //与页面布局文件中的listview关联
        GridView GridView = (GridView) findViewById(R.id.my_list2);
        DBManager2 dbManager = new DBManager2(MainActivity8.this);

        listItems = new ArrayList<HashMap<String, String>>();
        for(RateItem2 rateItem : dbManager.listAll2()){
            HashMap<String, String> map = new HashMap<String, String>();
            //            //"ItemTime", "ItemStates","ItemTitle","ItemAuthor"
         //   map.put("ItemTime", rateItem.getRtime()); // 标题文字

            map.put("ItemA","书名：  "+rateItem.getRbookN());
            map.put("ItemP","作者：  "+rateItem.getRauthor()); // 详情描述
            map.put("ItemR","感悟：  "+rateItem.getRreview());
            listItems.add(map);
//            retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
        }

        listItemAdapter = new SimpleAdapter(MainActivity8.this,
                listItems , //listItem数据源
                R.layout.hangbuju2,
                //ListItem的XML布局实现
                new String[]{"ItemA","ItemP", "ItemR"},
                //数据的key
                new int[]{ R.id.itemA,R.id.itemP,R.id.itemR}//布局里的id，k与id一一匹配
        );
        GridView.setAdapter(listItemAdapter);
        GridView.setOnItemClickListener(this);
    }

    public void  k(View v){
        Intent i = new Intent(this, MainActivity4.class);
        //启动 
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//通过view获取数据，这里好像有问题，因为得到的应该是行布局里面的内容
        TextView title=view.findViewById(R.id.itemA);
        TextView author=view.findViewById(R.id.itemP);
        TextView review=view.findViewById(R.id.itemR);
        String title2=String.valueOf(title.getText());
        String author2=String.valueOf(author.getText());
        String review2=String.valueOf(review.getText());
        //传给页面9
        // //打开新的页面，传入参数
          Intent rateCalc = new Intent(this,MainActivity9.class);
          rateCalc.putExtra("title",title2);
          rateCalc.putExtra("author",author2);
          rateCalc.putExtra("review",review2);
         startActivity(rateCalc);
    }
}