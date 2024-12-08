package com.andev.JeeroBrowse;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    EditText urlinput;
    ImageButton reload_btn,copy_url_btn,slider_btn_1,home_button,youtube_btn,linkedin_btn,setting_btn,cancel_btn,grren_btn;
    ImageButton facebook_btn,titok_btn,x_btn,chatgpt_btn,github_btn,y2mate_btn,proxy_btn,classroom_btn,google_btn;
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
        setContentView(R.layout.activity_main);
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

        permission();

        cancel_btn = findViewById(R.id.clear_text_icon);
        reload_btn = findViewById(R.id.relode_icon);
        urlinput = findViewById(R.id.url_input);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_bar);
        //textView = findViewById(R.id.url_textview);
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
        grren_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, GreenUni.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });



        Animation reload_anim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate_anim);
        reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadurl(urlinput.getText().toString());
                reload_btn.startAnimation(reload_anim);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlinput.setText("");
            }
        });
        urlinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urlinput.getWindowToken(),0);
                if(i == EditorInfo.IME_ACTION_GO  || i == EditorInfo.IME_ACTION_DONE){
                    loadurl(urlinput.getText().toString());
                    return true;
                }
                return false;
            }
        });

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, DownloadWithPauseResmueNew.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });



        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, Google.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });
        classroom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, ClassRoom.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        proxy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, Proxy.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        y2mate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, Y2Mate.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        github_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, GitHub.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        chatgpt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, ChatGpt.class);
                //next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });



        x_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(MainActivity.this,X.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        titok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(MainActivity.this,Tiktok.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(MainActivity.this,FaceBook.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(MainActivity.this, LinkedIn.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        youtube_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(MainActivity.this, Youtube.class);
                //home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadurl("https://duckduckgo.com");
            }
        });
        copy_url_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToCopy =urlinput.getText().toString();

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

        WebSettingsManager.WebSettings(webView);

        webView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                currenturl = view.getUrl();
                urlinput.setText(currenturl);
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
                    DownloadDialog.downloadDialog(MainActivity.this,url, userAgent, contentDisposition, mimetype, contentLength);

                } else {

                    Log.v("TAG", "Permission is revoked");
                    //requesting permissions.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    DownloadDialog.downloadDialog(MainActivity.this,url, userAgent, contentDisposition, mimetype, contentLength);
                }
                //post notify

                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted");

                } else {

                    Log.v("TAG", "Permission is revoked");
                    //requesting permissions.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 2);
                }
            }
        });

        if (!isInstanceCreated){
            loadurl("https://duckduckgo.com");
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
            urlinput.setText(webView.getUrl());
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    public void permission(){
        List<String> permissionsNeeded = new ArrayList<>();

// Check WRITE_EXTERNAL_STORAGE permission
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "WRITE_EXTERNAL_STORAGE Permission is granted");
        } else {
            Log.v("TAG", "WRITE_EXTERNAL_STORAGE Permission is revoked");
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

// Check POST_NOTIFICATIONS permission
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "POST_NOTIFICATIONS Permission is granted");
        } else {
            Log.v("TAG", "POST_NOTIFICATIONS Permission is revoked");
            permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS);
        }

// Check CAMERA permission
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "CAMERA Permission is granted");
        } else {
            Log.v("TAG", "CAMERA Permission is revoked");
            permissionsNeeded.add(Manifest.permission.CAMERA);
        }

// Check RECORD_AUDIO permission
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "RECORD_AUDIO Permission is granted");
        } else {
            Log.v("TAG", "RECORD_AUDIO Permission is revoked");
            permissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

// Check READ_EXTERNAL_STORAGE permission
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "READ_EXTERNAL_STORAGE Permission is granted");
        } else {
            Log.v("TAG", "READ_EXTERNAL_STORAGE Permission is revoked");
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

// Request all missing permissions at once
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), 1);
        }

    }

}