package com.andev.JeeroBrowse;

import static androidx.core.content.ContextCompat.startActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadDialog {

    public static void downloadDialog(Context context,final String url, final String userAgent, String contentDisposition, String mimetype,long contentLength) {
        //getting filename from url.
        String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);

        if (contentDisposition.contains("filename")) {
            String[] parts = contentDisposition.split("filename=");
            String fil=null;
            if (parts.length > 1) {
                fil = parts[1].replace("\"", "");  // Remove quotes around the filename
            }
            filename = fil;
            Log.d("mymepdf",filename);
        }




        //alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View vvr = LayoutInflater.from(context).inflate(R.layout.custom_dialog_for_download,null);
        Button buttonYes = vvr.findViewById(R.id.yes_button);
        Button buttonNo = vvr.findViewById(R.id.no_button);
        EditText EditTextFileName = vvr.findViewById(R.id.download_file_name_edittext);
        TextView EditTextFileUrl = vvr.findViewById(R.id.http_name_textview);
        TextView filesize = vvr.findViewById(R.id.filesize);
        Double FMB =  contentLength/1048576.0;
        Double totruncate = BigDecimal.valueOf(FMB)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        String fileinMB = String.valueOf(totruncate);
        filesize.setText("Size:"+fileinMB+" MB");

        builder.setView(vvr);
        final  AlertDialog alertDialogs = builder.create();
        alertDialogs.setCancelable(false);

        EditTextFileName.setText(filename);
        EditTextFileUrl.setText(url);



        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ur= EditTextFileUrl.getText().toString();
                String fi = EditTextFileName.getText().toString();

                Intent intentUrls = new Intent(view.getContext(),DownloadWithPauseResmueNew.class);
                intentUrls.putExtra("urlss",ur);
                intentUrls.putExtra("filenames", fi);
                intentUrls.putExtra("filesize",contentLength);

                context.startActivity(intentUrls);

                alertDialogs.dismiss();

            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialogs.dismiss();

            }
        });


        alertDialogs.show();

    }

}
