package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText ph;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void btn1(View v) {
        //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。 
        //在存放资源代码的文件夹下下， 
        //但是似乎，从增加书的页面返回之后就不能继续展示，要不还是写在文件里面吧
        Intent i = new Intent(this, MainActivity3.class);
        //启动 
        startActivity(i);
    }

    public void btn2(View v) {
        ph = (EditText) findViewById(R.id.uid);
        password = (EditText) findViewById(R.id.ups);

        String phstr = ph.getText().toString();
        //  System.out.println(name);
        String pass = password.getText().toString();
        //  System.out.println(pass);
        Log.i("TAG", phstr+ "_" + pass);

        //先看看是否有账号
        DBManager db=new DBManager(MainActivity2.this);
        RateItem rat=db.findById(phstr);

        if(rat==null){
            Toast.makeText(MainActivity2.this, "账号不存在", Toast.LENGTH_LONG).show();
        }

        else{
           String pass2=rat.getPassword();
           if(pass2.equals(pass)==false){
               Toast.makeText(MainActivity2.this, "密码错误", Toast.LENGTH_LONG).show();
           }
           else {
               String na=rat.getUserName();
               Toast.makeText(MainActivity2.this, "登录成功", Toast.LENGTH_LONG).show();
               Intent intent = new Intent(this, MainActivity4.class);
               // intent.putExtra("userPh",phstr);
               SharedPreferences sharedPreferences = getSharedPreferences("usermess", Activity.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString("userid",phstr);
               editor.putString("uname",na);
               editor.commit();
               startActivity(intent);
           }
        }

    }
}