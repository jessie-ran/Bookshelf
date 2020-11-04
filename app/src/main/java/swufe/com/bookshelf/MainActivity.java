package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

//这是欢迎界面
    public class MainActivity extends AppCompatActivity implements Runnable  {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            SharedPreferences sharedPreferences = getSharedPreferences("loginnum", Activity.MODE_PRIVATE);
            PreferenceManager.getDefaultSharedPreferences(this);
            String  lonum2=sharedPreferences.getString("lonum","1");
            if(lonum2.equals("1")){
                // //启动一个延迟线程
                         new  Thread(this).start();
            }
            else {
                //直接到4
                try {
                    Thread.sleep(3000);
                    Intent intent2 = new Intent();
                    intent2.setClass(this, MainActivity4.class);
                    startActivity(intent2);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//第一次登陆的时候写一个文件，
    //在这里进行判断是否存在
    //如果存在，直接跳转到mainactivity4
        @Override
        public void run() {
            //延迟1秒时间
            try {
                Thread.sleep(3000);
                Intent intent2 = new Intent();
                intent2.setClass(this, MainActivity2.class);
                startActivity(intent2);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
