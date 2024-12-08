package com.andev.JeeroBrowse;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Youtube extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    ImageButton reload_btn,copy_url_btn,slider_btn_1,home_button,facebook_btn,linkedin_btn,tiktok_btn,x_btn,github_btn,chatgpt_btn,y2mate_btn,proxy_btn,classroom_btn,google_btn,setting_btn,youtube_btn,grren_btn;
    TextView textView;
    HorizontalScrollView horizontalScrollView;
    boolean slidercontrol = false;
    String currenturl;
    private boolean isInstanceCreated = false;
    private ValueCallback<Uri[]> uploadMessage;
    private final static int FILE_CHOOSER_RESULT_CODE = 105;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isInstanceCreated = false;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView = findViewById(R.id.webview);
        webView.saveState(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        webView.saveState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_youtube);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


            camera_mic_permission();

            webView = findViewById(R.id.webview);
            webView.saveState(savedInstanceState);
            progressBar = findViewById(R.id.progress_bar);
            textView = findViewById(R.id.url_textview);
            copy_url_btn = findViewById(R.id.link_icon);
            horizontalScrollView = findViewById(R.id.scrollview_horizontal);
            slider_btn_1 = findViewById(R.id.slider_button_1);
            home_button = findViewById(R.id.home_button);
            facebook_btn = findViewById(R.id.facebook_button);
            linkedin_btn = findViewById(R.id.linkedin_button);
            tiktok_btn = findViewById(R.id.tiktok_button);
            x_btn = findViewById(R.id.x_button);
            github_btn = findViewById(R.id.github_btn);
            chatgpt_btn = findViewById(R.id.chatgpt_button);
            y2mate_btn = findViewById(R.id.Y2Mate_btn);
            proxy_btn = findViewById(R.id.proxy_button);
            classroom_btn = findViewById(R.id.classroom_button);
            google_btn = findViewById(R.id.google_button);
            setting_btn = findViewById(R.id.setting_button);
            youtube_btn = findViewById(R.id.youtube_button);
            grren_btn = findViewById(R.id.green_button);
            reload_btn = findViewById(R.id.relode_icon);



            Animation reload_anim = AnimationUtils.loadAnimation(Youtube.this,R.anim.rotate_anim);
            reload_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadurl(textView.getText().toString());
                    reload_btn.startAnimation(reload_anim);
                }
            });
            grren_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(Youtube.this, GreenUni.class);
                    //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(next);
                }
            });

            youtube_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadurl("https://m.youtube.com");
                }
            });

            setting_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(Youtube.this, DownloadWithPauseResmueNew.class);
                    //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(next);
                }
            });


        google_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(Youtube.this, Google.class);
                    //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(next);
                }
            });

            classroom_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(Youtube.this, ClassRoom.class);
                    //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(next);
                }
            });

            proxy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Youtube.this, Proxy.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
            });

            y2mate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Youtube.this, Y2Mate.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
            });

            chatgpt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Youtube.this, ChatGpt.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
            });


            github_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Youtube.this, GitHub.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });


            x_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Youtube.this, X.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        tiktok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Youtube.this, Tiktok.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Youtube.this, LinkedIn.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Youtube.this, FaceBook.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Youtube.this, MainActivity.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });
        copy_url_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToCopy = textView.getText().toString();

                // Get the Clipboard Manager
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Youtube.CLIPBOARD_SERVICE);

                // Create a new ClipData object to hold the text
                ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);

                // Set the clip to the clipboard
                clipboard.setPrimaryClip(clip);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toastText));


                TextView toastText = layout.findViewById(R.id.toastText);
                toastText.setText("Text copied to clipboard");


                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);


                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();


            }
        });
        slider_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidercontrol){
                    horizontalScrollView.setVisibility(View.GONE);
                    slider_btn_1.setImageDrawable(getResources().getDrawable(R.drawable.baseline_arrow_back_ios_24));
                    slidercontrol=false;
                }else {
                    horizontalScrollView.setVisibility(View.VISIBLE);
                    slider_btn_1.setImageDrawable(getResources().getDrawable(R.drawable.baseline_arrow_forward_ios_24));
                    slidercontrol = true;
                }
            }
        });


        webView.setWebViewClient(new MyWebviewClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        WebSettingsManager.FileAccess(webView);
        WebSettingsManager.WebSettings(webView);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                currenturl = view.getUrl();
                textView.setText(currenturl);
                progressBar.setProgress(newProgress);
            }
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                // Create an Intent to open the file chooser dialog
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");  // Allow all file types

                // Launch the file chooser
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILE_CHOOSER_RESULT_CODE);
                return true;
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //Checking runtime permission for devices above Marshmallow.
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted");
                    DownloadDialog.downloadDialog(Youtube.this,url, userAgent, contentDisposition, mimetype, contentLength);

                } else {

                    Log.v("TAG", "Permission is revoked");
                    //requesting permissions.
                    ActivityCompat.requestPermissions(Youtube.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    DownloadDialog.downloadDialog(Youtube.this,url, userAgent, contentDisposition, mimetype, contentLength);
                }
                //post notify

                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted");

                } else {

                    Log.v("TAG", "Permission is revoked");
                    //requesting permissions.
                    ActivityCompat.requestPermissions(Youtube.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 2);
                }
            }
        });

        if (!isInstanceCreated){
            loadurl("https://m.youtube.com");
            isInstanceCreated = true;
        }else {
            loadurl(currenturl);
        }


    }

    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
    void loadurl(String url){
        boolean matchUrl = Patterns.WEB_URL.matcher(url).matches();
        if(matchUrl){
            webView.loadUrl(url);
        }else{
            webView.loadUrl("google.com/search?q="+url);
        }
    }
    class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            textView.setText(webView.getUrl());
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void camera_mic_permission(){
        if (checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission is granted");

        } else {

            Log.v("TAG", "Permission is revoked");
            //requesting permissions.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        }

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission is granted");

        } else {

            Log.v("TAG", "Permission is revoked");
            //requesting permissions.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (uploadMessage == null) return;

            Uri[] results = null;
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            uploadMessage.onReceiveValue(results);
            uploadMessage = null;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}