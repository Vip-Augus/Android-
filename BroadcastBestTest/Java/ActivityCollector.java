package com.example.jingqboom.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JingQBoom on 2016/4/5.
 */
public class ActivityCollector  {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }

    public static void finishAll()
    {
        for(Activity activity: activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();//销毁活动
            }
        }
    }
}
