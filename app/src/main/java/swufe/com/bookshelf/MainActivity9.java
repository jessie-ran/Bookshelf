package swufe.com.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity9 extends AppCompatActivity {
TextView t1;
    TextView t2;
    TextView t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        Intent intent=getIntent();
        //先获取，再展示
        String title = intent.getStringExtra("title");
        String author= intent.getStringExtra("author");
        String review= intent.getStringExtra("review");


        t1=findViewById(R.id.textView14);
        t2=findViewById(R.id.textView15);
        t3=findViewById(R.id.textView16);

        t1.setText(title);
        t2.setText(author);
        t3.setText(review);
    }
}