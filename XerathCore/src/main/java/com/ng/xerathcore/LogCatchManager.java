package com.ng.xerathcore;

import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/5
 */
public class LogCatchManager {

    public LogCatchManager() {
    }

    public void start() {
        try {
            Process process = Runtime.getRuntime().exec("logcat -e");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                Log.d("nangua","收集到的日志:" + line);
            }
        } catch (IOException e) {
        }



    }
}
