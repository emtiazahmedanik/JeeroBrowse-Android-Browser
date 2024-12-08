package com.andev.JeeroBrowse;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
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
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.URISyntaxException;

public class Google extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    ImageButton printer,reload_btn,copy_url_btn,slider_btn_1,home_button,youtube_btn,linkedin_btn,setting_btn,google_btn,grren_btn;
    ImageButton facebook_btn,titok_btn,x_btn,chatgpt_btn,github_btn,y2mate_btn,proxy_btn,classroom_btn;
    TextView textView;
    HorizontalScrollView horizontalScrollView;
    boolean slidercontrol = false;
    String currenturl;
    private boolean isInstanceCreated = false;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isInstanceCreated = false;
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
        setContentView(R.layout.activity_google);
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
        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.url_textview);
        copy_url_btn = findViewById(R.id.link_icon);
        horizontalScrollView = findViewById(R.id.scrollview_horizontal);
        slider_btn_1 = findViewById(R.id.slider_button_1);
        home_button = findViewById(R.id.home_button);
        youtube_btn = findViewById(R.id.youtube_button);
        linkedin_btn = findViewById(R.id.linkedin_button);
        facebook_btn = findViewById(R.id.facebook_button);
        titok_btn = findViewById(R.id.tiktok_button);
        x_btn = findViewById(R.id.x_button);
        chatgpt_btn = findViewById(R.id.chatgpt_button);
        github_btn = findViewById(R.id.github_btn);
        y2mate_btn = findViewById(R.id.Y2Mate_btn);
        proxy_btn = findViewById(R.id.proxy_button);
        classroom_btn = findViewById(R.id.classroom_button);
        setting_btn = findViewById(R.id.setting_button);
        google_btn = findViewById(R.id.google_button);
        grren_btn = findViewById(R.id.green_button);
        reload_btn = findViewById(R.id.relode_icon);
        printer = findViewById(R.id.print);


        Animation reload_anim = AnimationUtils.loadAnimation(Google.this,R.anim.rotate_anim);
        reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadurl(textView.getText().toString());
                reload_btn.startAnimation(reload_anim);
            }
        });
        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintManager printManager = (PrintManager) Google.this.getSystemService(GreenUni.PRINT_SERVICE);
                PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter();
                String jobname = getString(R.string.app_name)+"Print Test";
                printManager.print(jobname,printDocumentAdapter,new PrintAttributes.Builder().build());
            }
        });
        grren_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Google.this, GreenUni.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });


        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadurl("https://google.com");
            }
        });
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Google.this, DownloadWithPauseResmueNew.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });



        classroom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Google.this, ClassRoom.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        proxy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Google.this, Proxy.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        y2mate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Google.this, Y2Mate.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        github_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Google.this, GitHub.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        chatgpt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Google.this, ChatGpt.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });



        x_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Google.this,X.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        titok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Google.this,Tiktok.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Google.this,FaceBook.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Google.this, LinkedIn.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        youtube_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Google.this, Youtube.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Google.this, MainActivity.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });
        copy_url_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToCopy = textView.getText().toString();

                // Get the Clipboard Manager
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Google.CLIPBOARD_SERVICE);

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
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                // Block JavaScript alert popups
                return true; // Returning true means "cancel the alert"
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                // Block JavaScript confirm popups
                return true; // Cancel the confirm dialog
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // Block JavaScript prompt popups
                return true; // Cancel the prompt dialog
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //Checking runtime permission for devices above Marshmallow.
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted");
                    DownloadDialog.downloadDialog(Google.this,url, userAgent, contentDisposition, mimetype, contentLength);

                } else {

                    Log.v("TAG", "Permission is revoked");
                    //requesting permissions.
                    ActivityCompat.requestPermissions(Google.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    DownloadDialog.downloadDialog(Google.this,url, userAgent, contentDisposition, mimetype, contentLength);
                }
                //post notify

                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted");

                } else {

                    Log.v("TAG", "Permission is revoked");
                    //requesting permissions.
                    ActivityCompat.requestPermissions(Google.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 2);
                }
            }
        });

        if (!isInstanceCreated){
            loadurl("https://google.com");
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
            String url = request.getUrl().toString();


            if (url.contains("ak.phudauwy.com") || url.contains("zunsoach.com")||url.contains("kerasint.com")||url.contains("greengoaxi.com")||url.contains("branchid=131")|| url.startsWith("https://ak.")|| url.contains("x2tag.com")|| url.contains("adf.ly")|| url.contains("ouo.io")|| url.contains("popads.net")|| url.contains("sh.st")|| url.contains("adsterra.com")|| url.contains("onclickads.net")|| url.contains("propellerads.com")|| url.contains("reimageplus.com")|| url.contains("youradexchange.com")|| url.contains("coinhive.com")) {
                return true;  // Block the URL
            }

            else if (url.startsWith("http://") || url.startsWith("https://")) {
                view.loadUrl(url);
                return false;
            } else if (url.startsWith("mailto:")) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                view.getContext().startActivity(emailIntent);
                return true;
            } else if (url.startsWith("tel:")) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                view.getContext().startActivity(dialIntent);
                return true;
            } else if (url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        view.getContext().startActivity(intent);
                        return true;
                    }

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }

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

}