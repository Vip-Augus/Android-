package com.example.smstest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView sender;

    private TextView content;

    private IntentFilter receiveFilter;

    private MessageReceiver messageReceiver;

    private EditText to;

    private EditText msgInput;

    private Button send;

    private IntentFilter sendFilter;

    private SendStatusReceiver sendStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sender = (TextView) findViewById(R.id.sender);//发送人信息TextView
        content = (TextView) findViewById(R.id.content);//发送人短信内容TextView
        receiveFilter = new IntentFilter();//InFilter实例创建
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");//监听android.provider.Telephony.SMS_RECEIVED这条广播
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);//动态注册
        to = (EditText) findViewById(R.id.to);//发送给对方的号码注册
        msgInput = (EditText) findViewById(R.id.msg_input);
        send = (Button) findViewById(R.id.send);

        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();//调用SMSManager的getDefault()方法或取代SMSManager的实例
                Intent sentIntent = new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sentIntent, 0);
                smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), pi, null);//接收五个参数,第一个用于指定接收人的手机号码,第三个用于指定短信的内容
            }
        });


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
            String format = intent.getStringExtra("format");
            Bundle bundle = intent.getExtras();//从Intent参数中取出一个Bundle对象
            Object[] pdus = (Object[]) bundle.get("pdus");//提取短信
            //返回的实际类型是byte[][]，二维数组的每一个子数组就是一个pdu。
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu( (byte[]) pdus[i],format);//将每一个pdu字节数组转换成SMSMessage对象
                //此方适用于API23以上的版本,之后将会弃用createFromPdu( (byte[]) pdus),改为createFromPdu( (byte[]) pdus[i],format)
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

    class SendStatusReceiver extends BroadcastReceiver
    {
        //广播接收,
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (getResultCode() == RESULT_OK)
            {
                //发送成功
                Toast.makeText(context, "Send succeeded", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, "Send failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
