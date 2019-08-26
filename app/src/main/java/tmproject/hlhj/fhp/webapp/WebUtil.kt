package tmproject.hlhj.fhp.webapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by Never Fear   on 2018\9\20 0020.
Never More....
 */
object WebUtil {
    @SuppressLint("SetJavaScriptEnabled", "WrongConstant")
    fun initWeb(webView: WebView, context: Context){
        val webSettings = webView.settings
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(1)
        }

        webSettings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19

        if (Build.VERSION.SDK_INT >= 11) {
            webView.setLayerType(1, null as Paint?)
        }

        webView.setLayerType(2, null as Paint?)
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.setSupportMultipleWindows(true);
        webSettings.useWideViewPort = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.loadWithOverviewMode = true
        webSettings.databaseEnabled = true
        webSettings.savePassword = true
        webSettings.domStorageEnabled = true
        webView.isSaveEnabled = true
        webView.keepScreenOn = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.e("fanfanfan","ssss${url}")
                return super.shouldOverrideUrlLoading(view, url)

            }
        }
    }
    fun setClient(webView: WebView,client:WebViewClient){
        webView.webViewClient=client
    }
    fun setClient(webView: WebView,client:WebChromeClient){
        webView.webChromeClient=client
    }
    fun loadWeb(webView: WebView,web:String){
        webView.loadUrl(web)
    }
    fun loadUrl(webView: WebView, content:String){
        webView.loadDataWithBaseURL(null,content,"text/html", "utf-8",null)
    }
}