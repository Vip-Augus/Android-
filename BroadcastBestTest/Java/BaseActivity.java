package com.example.jingqboom.broadcastbestpractice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by JingQBoom on 2016/4/5.
 */
public class BaseActivity extends Activity {//定义一个用于继承的父类

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
