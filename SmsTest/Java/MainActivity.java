package com.example.smstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView sender;

    private TextView content;

    private IntentFilter receiveFilter;

    private MessageReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sender = (TextView) findViewById(R.id.sender);
        content = (TextView) findViewById(R.id.content);
        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }


    class MessageReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle bundle = intent.getExtras();//从Intent参数中取出一个Bundle对象
            Object[] pdus = (Object[]) bundle.get("pdus");//提取短信短信
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu( (byte[]) pdus[i]);//将每一个pdu字节数组转换成SMSMessage对象
            }
            String address = messages[0].getOriginatingAddress();//获取发送方号码
            String fullMessage = "";
            for ( SmsMessage message : messages)
            {
                fullMessage += message.getMessageBody();//获取短信内容
            }
            sender.setText(address);
            content.setText(fullMessage);
        }
    }
}
