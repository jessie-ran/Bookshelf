package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
//大不了重头再来
//我一定要把登录注册页面写出来
//气死了气死了
//这是注册，然后再是登录

public class MainActivity3 extends AppCompatActivity {
    RadioGroup radiogroup;
    RadioButton femalebutton;
    RadioButton malebutton;

    EditText ph;
    EditText userName;
    EditText pas1;
    EditText pas2;
    Button bs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        radiogroup=(RadioGroup)findViewById(R.id.radioGroupId);
        femalebutton=(RadioButton)findViewById(R.id.femaleButtonId);
        malebutton=(RadioButton)findViewById(R.id.maleButtonId);
        //先把输入的信息和按钮联合起来
        ph=findViewById(R.id.phoneNumber);
        userName=findViewById(R.id.userName);
        pas1=findViewById(R.id.passWord1);
        pas2=findViewById(R.id.passWord2);
        bs=findViewById(R.id.sign);
    }

    public void btn(View v) {
        String phoneString=ph.getText().toString();
        String nameString = userName.getText().toString();
        String passString = pas1.getText().toString();
        String repassString=pas2.getText().toString();
        RadioButton ra1 = (RadioButton) findViewById(radiogroup.getCheckedRadioButtonId());
        String sexString = ra1.getText().toString();
        RateItem rat = new RateItem();
        rat.setPh(phoneString);
        rat.setUserName(nameString);
        rat.setPassword(passString);
        rat.setSex(sexString);
        //首先判断是否有这条数据存在
        DBManager db=new DBManager(MainActivity3.this);
        RateItem rat2=db.findById(phoneString);
        if(rat2!=null){
            Toast.makeText(MainActivity3.this, "账号已经存在", Toast.LENGTH_LONG).show();
        }
        if(passString.equals(repassString)==false){
            Toast.makeText(MainActivity3.this, "两次密码不一致", Toast.LENGTH_LONG).show();

        }
        else {
            RateItem rateItem = new RateItem(phoneString, nameString, passString, sexString);
            db.register(rateItem);
            Toast.makeText(MainActivity3.this, "注册成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }
    }
}

