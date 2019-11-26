package com.dvinfosys.widgets.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

public class Constants {

    public static final int EVENT_MIN_INTERVAL = 1000 / 60; // 16ms

   public static final int SELECTOR_RADIUS_DP = 9;

    public static int dp2px(Context context, float dpValue) {
        if (dpValue <= 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float sp2px(Context context, float spValue) {
        if (spValue <= 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

    public static String formatNum(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

    public static String formatMillisecond(int millisecond) {
        String retMillisecondStr;

        if (millisecond > 99) {
            retMillisecondStr = String.valueOf(millisecond / 10);
        } else if (millisecond <= 9) {
            retMillisecondStr = "0" + millisecond;
        } else {
            retMillisecondStr = String.valueOf(millisecond);
        }

        return retMillisecondStr;
    }

    public static int getScreenHeight(Activity activity) {
        try {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        } catch (Exception e) {
            Log.e(activity.getClass().getName(), e.getMessage());
        }
        return 0;
    }

    public static int getFooterHeight(Activity activity, int height) {
        return getScreenHeight(activity) - height;
    }
}
