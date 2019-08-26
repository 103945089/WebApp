package tmproject.hlhj.fhp.webapp

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebConfig
import com.just.agentweb.WebViewClient
import com.netease.readwap.view.ReadWebView
import kotlinx.android.synthetic.main.sec_web.*



class SecWebAty :AppCompatActivity() {
    private var webView:ReadWebView?=null
    var msg:Message?=null
    private var loadingDialog: LoadingDialog? = null
    override fun onBackPressed() {
        if (webView!!.canGoBack()){
            webView?.goBack()
        }else{
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sec_web)
        Log.e("fanfan","oncreate");
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        webView = ReadWebView(applicationContext)
           lo. addView(webView,params)
        loadingDialog=LoadingDialog(this)
        loadingDialog?.show()
        initWeb()
        msg=MainAty.msg
        Log.e("fanfan","看一下消息${msg}")
        val transport = msg!!.obj as WebView.WebViewTransport
        transport.webView = webView
        msg!!.sendToTarget()

        btGoBack.setOnClickListener {
            finish()
        }
        btGoBack.setOnTouchListener(object : View.OnTouchListener {
            private var lastY: Int = 0
            private var lastX: Int = 0
            private var dyy: Int = 0
            private var dxx: Int = 0
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val action = event.action
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.rawX.toInt()
                        dxx = event.rawX.toInt()
                        lastY = event.rawY.toInt()
                        dyy = event.rawY.toInt()
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        //移动中动态设置位置
                        val dx = event.rawX.toInt() - lastX
                        val dy = event.rawY.toInt() - lastY
                        var left = v.left + dx
                        val top = v.top + dy
                        var right = v.right + dx
                        val bottom = v.bottom + dy
                        if (left < 0) {
                            left = 0
                            right = left + v.width
                        }
                        v.layout(left, top, right, bottom)
                        lastX = event.rawX.toInt()
                        lastY = event.rawY.toInt()
                    }
                    MotionEvent.ACTION_UP -> {
                        val xabs = Math.abs(event.rawX - dxx)
                        val yabs = Math.abs(event.rawY - dyy)
                        if (xabs < 5 && yabs < 5) {
                                finish()
                        }
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onDestroy() {
//        loadingDialog=null
        //注意WebView的处理要写在super.onDestroy()之前
        webView?.let {
            it.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            lo.removeView(it)
            it.clearHistory()
            it.destroy()
        }
        webView=null
        Log.e("fanfan","销毁")
        super.onDestroy()

    }
    private fun initWeb() {
        val settings = webView!!.settings
        settings.javaScriptEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.pluginState = WebSettings.PluginState.ON
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.allowFileAccess = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = false
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.databaseEnabled = true
        settings.domStorageEnabled = true
        settings.setGeolocationEnabled(true)
        settings.setAppCacheEnabled(true)

        webView!!.webViewClient=object :android.webkit.WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.e("fanfan","开始载入页面")
                loadingDialog?.show()
                super.onPageStarted(view, url, favicon)
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.e("fanfan","页面载入完成")
                loadingDialog?.dismiss()
                super.onPageFinished(view, url)

            }
        }
        webView!!.webChromeClient=object :WebChromeClient(){
            override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
                MainAty.msg = resultMsg
                val intent = Intent(this@SecWebAty, SecWebAty::class.java)
                startActivity(intent)
                return true

            }
        }
    }
}