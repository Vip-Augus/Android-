package com.example.androidthreadtest;

import android.os.Handler;//注意是android.os.Handler,导入包名错误将导致无法正确使用handleMessage方法
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int UPDATE_TEXT = 1;//定义整型常量,用于更新TextView这个动作.

    private TextView text;

    private Button changeText;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){//此时是在主线程当中云心的
            switch(msg.what){
                case UPDATE_TEXT:
                    //在这里进行UI操作
                    text.setText("Nice to meet you!");
                    break;
                default:
                    break;
            }
        }
    };//增加了一个Handler对象,重写弗雷的handleMessage()方法




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        changeText = (Button) findViewById (R.id.change_text);
        changeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.change_text:
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        Message message = new Message();
                        message.what = UPDATE_TEXT;//设置what字符段的值为UPDATE_TEXT
                        handler.sendMessage(message);//将Message对象发送出去
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
