package cn.leancloud.demo.vivopush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        mTextView = findViewById(R.id.tv);
        mBackBtn = findViewById(R.id.back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getDAta(getIntent());
    }

    private void getDAta(Intent intent) {
        //获取通知消息的messagId
        long messageId = intent.getLongExtra("vivo_push_messageId",0l);

        //获取单个自定义透传参数值
        String log = "";
        if (null != intent) {
            String key1 = intent.getStringExtra("key1");
            int kye2 = intent.getIntExtra("key2", -1);
            Log.d("CustomActivity", " messageId= " + messageId + " key1= " + key1 + " kye2= " + kye2);
            log = " messageId= " + messageId + " key1= " + key1 + " kye2= " + kye2;

        }

        //遍历所有自定义参数
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                if (!TextUtils.isEmpty(key)) {
                    //需要注意参数类型。
                    String content = bundle.getString(key);
                    log  += " key= " + key + " content= " + content ;
                    Log.d("CustomActivity", " 自定义参数= " + log );
                }
            }
        }
        mTextView.setText(log);

    }
}