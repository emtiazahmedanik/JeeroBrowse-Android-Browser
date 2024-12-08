package com.andev.JeeroBrowse;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebSettingsManager {

        public static void FileAccess(WebView webView){
                WebSettings webSettings = webView.getSettings();
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowContentAccess(true);

        }

    public static void WebSettings(WebView webView) {

            WebSettings webSettings = webView.getSettings();

            // Enable JavaScript
            webSettings.setJavaScriptEnabled(true);

            // Enable support for Zoom controls
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setSupportMultipleWindows(false);

            // Enable DOM Storage
            webSettings.setDomStorageEnabled(true);
            webSettings.setMediaPlaybackRequiresUserGesture(false);

            // Enable caching
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

            // Enable database storage
            webSettings.setDatabaseEnabled(true);

            // Set other WebSettings as per your requirements
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);

            // Configure the WebView for more advanced settings
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(true);
            webView.evaluateJavascript("Object.defineProperty(window, 'RTCPeerConnection', { value: undefined });", null);


            webSettings.setJavaScriptCanOpenWindowsAutomatically(false); // Prevent opening new windows/tabs
            webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Mobile Safari/537.36");
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WebView.enableSlowWholeDocumentDraw();
            }
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

}
