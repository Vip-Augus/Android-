package com.example.servicebestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by JingQBoom on 2016/4/24.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, LongRunningService.class);
        //构造出一个Intent对象,去启动LongRunningService这个服务
        context.startActivity(i);
    }
}
