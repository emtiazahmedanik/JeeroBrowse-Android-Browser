package com.andev.JeeroBrowse;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;

public class Utils {


    private Utils(){
        // empty const
    }
    public  static String getRootDirPath(Context context){
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
//                    null)[0];
//            return file.getAbsolutePath();
//        } else {
//            return context.getApplicationContext().getFilesDir().getAbsolutePath();
//        }

        

        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    }
    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }
}
