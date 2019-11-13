package com.wind.yuanbin.smallJump;

import android.util.Log;

/**
 * Created by yuanb on 2018/2/5.
 */

public class L {
    public static void o(Object msg){
        System.out.println(msg);
    }
    //    public void showT(String sg){
//        if (mToast == null)
//            mToast = new Toast(BaseApplication);
//    }
    private static boolean isDebug = true;

    public static void i(String tag,String msg){
        if(isDebug){
            Log.i(tag, getTrace() + msg);
        }
    }
    public static void i(Object object,String msg){
        if(isDebug){
            Log.i(object.getClass().getSimpleName(), getTrace() + msg);
        }
    }

    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag, getTrace() + msg);
        }
    }
    public static void e(Object object,String msg){
        if(isDebug){
            Log.e(object.getClass().getSimpleName(), getTrace() + msg);
        }
    }

    private static String getTrace() {
        try {
            Throwable t = new Throwable();
            StackTraceElement trace = t.getStackTrace()[2];
            String method = trace.getClassName() + "." + trace.getMethodName()
                    + "[" + trace.getLineNumber() + "]line\n";
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
