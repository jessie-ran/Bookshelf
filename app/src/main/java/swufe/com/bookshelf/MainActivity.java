package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//这是欢迎界面
    public class MainActivity extends AppCompatActivity implements Runnable  {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //启动一个延迟线程
            new  Thread(this).start();
        }

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
