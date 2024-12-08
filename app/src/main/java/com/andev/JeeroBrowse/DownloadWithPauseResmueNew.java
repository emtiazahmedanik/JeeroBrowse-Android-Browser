package com.andev.JeeroBrowse;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class DownloadWithPauseResmueNew extends AppCompatActivity {

    HashMap<String, String> map = new HashMap<>();

    Button buttonone, buttonCancelOne;
    TextView textViewProgressOne, textViewfilenameset,downloaderrmsg;
    ProgressBar progressBarOne;
    FrameLayout sheet;
    int downloadIdOne;
    RelativeLayout relativeLayout;
    private static String dirPath;
    long filesizeByte;
    int currentProgress;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    String urlsss = "", filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_download_with_pause_resmue_new);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        createNotificationChannel();
        checkNotificationPermission();

        bottomSheetBehavior = BottomSheetBehavior.from(sheet);

        // Optional: Customize BottomSheetBehavior
        bottomSheetBehavior.setPeekHeight(150);
        bottomSheetBehavior.setHideable(false);


        //dirPath = Utils.getRootDirPath(getApplicationContext());
        urlsss = getIntent().getStringExtra("urlss");
        filename = getIntent().getStringExtra("filenames");
        filesizeByte = getIntent().getLongExtra("filesize", 0);

        textViewfilenameset.setText(filename);

        if (StorageUtils.isEnoughSpaceForDownload(filesizeByte)) {
            // Proceed with download
            buttonone.setVisibility(View.VISIBLE);
        } else {
            // Show message to user that there's not enough space
            Toast.makeText(getApplicationContext(), "Not enough storage space to download this file.", Toast.LENGTH_LONG).show();
        }

        map.put(urlsss,filename);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            onClickListenerOne(entry.getKey(),entry.getValue());
        }






    }

    private void init() {
        buttonone = findViewById(R.id.buttonone);

        buttonCancelOne = findViewById(R.id.buttonCancelOne);

        textViewProgressOne = findViewById(R.id.textViewProgressOne);
        textViewfilenameset = findViewById(R.id.textViewfilenameset);
        downloaderrmsg = findViewById(R.id.downerror);
        relativeLayout  = findViewById(R.id.layout2);

        progressBarOne = findViewById(R.id.progressBarOne);
        sheet = findViewById(R.id.sheet);

        Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blinking_back);
        buttonone.startAnimation(blinkAnimation);
        Animation Animation = AnimationUtils.loadAnimation(this, R.anim.animation2);
        downloaderrmsg.setAnimation(Animation);



        Toast toast = Toast.makeText(this, "To start downloading TAP to START", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0); // 100 is the y-offset (distance from the top)
        toast.show();


    }



    private void onClickListenerOne(String urlsss,String filename) {
        buttonone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonone.clearAnimation();

                if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.pause(downloadIdOne);
                    return;
                }
                buttonone.setEnabled(false);
                progressBarOne.setIndeterminate(true);
                progressBarOne.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);


                if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.resume(downloadIdOne);
                    return;
                }
                downloadIdOne = PRDownloader.download(urlsss, Utils.getRootDirPath(getApplicationContext()), ""+ filename)
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {

                                progressBarOne.setIndeterminate(false);
                                buttonone.setEnabled(true);
                                buttonone.setText("Pause");
                                buttonCancelOne.setEnabled(true);


                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {

                                buttonone.setText("Resume");

                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {

                                buttonone.setText("Start");
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setProgress(0);
                                textViewProgressOne.setText("");
                                downloadIdOne = 0;
                                progressBarOne.setIndeterminate(false);


                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {

                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                //currentProgress = (int)progressPercent;
                                progressBarOne.setProgress((int) progressPercent);
                                textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {

                                buttonone.setEnabled(false);
                                buttonCancelOne.setEnabled(false);
                                buttonone.setText("Done");
                                sendDownloadCompleteNotification();
                                map.remove(urlsss);


                            }

                            @Override
                            public void onError(Error error) {
                                buttonone.setText("Start");
                                Toast.makeText(getApplicationContext(), "Error occcur" + " " + "1", Toast.LENGTH_SHORT).show();
                                textViewProgressOne.setText("");
                                progressBarOne.setProgress(0);
                                downloadIdOne = 0;
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setIndeterminate(false);
                                buttonone.setEnabled(true);


                            }

                        });

            }
        });
        buttonCancelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PRDownloader.cancel(downloadIdOne);

            }
        });
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "DownloadChannel";
            String description = "Channel for download notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("download_channel", name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendDownloadCompleteNotification() {
        Log.d("NotificationDebug", "Preparing to send notification");

        // Create the notification
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "download_channel")
                    .setSmallIcon(R.drawable.baseline_cloud_download_24)
                    .setContentTitle("Download Complete")
                    .setContentText("Your file has been downloaded successfully.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            Intent intent = new Intent(this, DownloadWithPauseResmueNew.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(1, builder.build());

            Log.d("NotificationDebug", "Notification sent");
        } catch (Exception e) {
            Log.e("NotificationError", "Error sending notification", e);
        }
    }

    private void checkNotificationPermission() {
        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission is granted");

        } else {

            Log.v("TAG", "Permission is revoked");
            //requesting permissions.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 2);
        }
    }



}
