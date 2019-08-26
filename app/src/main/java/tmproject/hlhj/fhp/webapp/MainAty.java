package tmproject.hlhj.fhp.webapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.WebViewClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.netease.readwap.IHandlerCallback;
import com.netease.readwap.IReadWapCallback;
import com.netease.readwap.IRegisterNativeFunctionCallback;
import com.netease.readwap.ISetSDKAuthListener;
import com.netease.readwap.view.ReadWebView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.microedition.khronos.opengles.GL;

import tmproject.hlhj.fhp.webapp.web.WebSocketFactory;

/**
 * Created by Never Fear   on 2018\9\20 0020.
 * Never More....
 */

public class MainAty extends AppCompatActivity implements IReadWapCallback, IRegisterNativeFunctionCallback {
    private String s ="app.3555and.com";//载入网址
    private String eStr="app.3555and.com";//载入网址 两个都需要改
    private String encKey="lATgLQXR";//DNS key
    private String encId="8038";//DNS ID
    private String tittle="奔驰宝马";//标题
    private boolean isDns=true;//是否开启DNS  true开启，false关闭
    private byte[] encryptedString;
    private ImageView btHome,btRefresh,btForward,btBack;
    private TextView btClear,tvTittle;
    private String ueStr;
    private ReadWebView webView;
    private String baseUrl="";
    private ImageView imageView;
    private RelativeLayout root;
    private boolean isGoBack = false;
    private boolean isFirst = true;
    private LoadingDialog loadingDialog;
    private String lastUrl="";
    private int lastNum=0;
    private String n1="/Promotion";
    private String n2="https://www.hd0385.com/wap.html";
    private String n3="/Financial";
    private ImageView imgBg;
    private String n4="/AnyTimeDiscount";
    private List<String> hisUrl=new ArrayList<>();
    private MyWebViewClient client;
    private LinearLayout lo;
    private RelativeLayout lo2;
    private AgentWeb agent;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imgBg=findViewById(R.id.imgBg);
        OkGo.<String>get("https://bm.appbaoma.com/app/v1/public/config")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        A bean = new Gson().fromJson(response.body(), A.class);
                        try {
                            Glide.with(MainAty.this).load(bean.getData().getBack()).into(imgBg);
                        }catch (Exception e){

                        }
                        getData(bean.getData().getUrl());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imgBg.setVisibility(View.GONE);
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
        initView();
        client=new MyWebViewClient();
        initWeb();
        setListener();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public boolean pageGoBack(ReadWebView web, MyWebViewClient client) {
        final String url = client.popLastPageUrl();
        if (url != null) {
            web.loadUrl(url);
            return true;
        }
        finish();
        return false;
    }
    public static Message msg;
    @SuppressLint("JavascriptInterface")
    private void setWeb() {
        android.webkit.WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebSocketFactory(webView),"WebSocketFactory");
        webView.setWebViewClient(client);
        baseUrl=s;
        hisUrl.add(s);
//        synCookies(this, baseUrl,SPUtil.INSTANCE.getCook(this));
        webView.loadUrl(baseUrl);
        loadingDialog.show();
        Log.e("fanfan","加载"+baseUrl);
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                MainAty.msg=resultMsg;
                Intent intent = new Intent(MainAty.this, SecWebAty.class);
                MainAty.this.startActivityForResult(intent,110);
                return true;

            }

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                lo2.removeView(window);
                window=null;
            }
        });
    }

    private void initView() {
        loadingDialog=new LoadingDialog(this);
//        s="https://www.we790.com/";
        getSupportActionBar().hide();
        lo = findViewById(R.id.lo);
        lo2 = findViewById(R.id.lo2);
        root=findViewById(R.id.rooView);
        btClear=findViewById(R.id.btClear);
        webView=findViewById(R.id.webView);
        tvTittle=findViewById(R.id.tvTittle);
        btBack=findViewById(R.id.btGoBack);
        btRefresh=findViewById(R.id.btRefresh);
        btForward=findViewById(R.id.btFor);
        btHome=findViewById(R.id.btHome);
        tvTittle.setText(tittle);
    }

    private void getData(String ss) {
        String url = ss;
        s=url;
        baseUrl=s;
        n1=baseUrl+n1;
        n3=baseUrl+n3;
        n4=baseUrl+n4;
        setWeb();

    }

    private void initWeb() {
        android.webkit.WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setAppCacheEnabled(true);
    }

    public void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
        CookieSyncManager.getInstance().sync();
    }
    public void synCookies(Context context, String url,String cookies) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, cookies);
        CookieSyncManager.getInstance().sync();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(baseUrl);
            }
        });
        btBack.setOnTouchListener(new View.OnTouchListener() {
            private int lastY;
            private int lastX;
            private int dyy;
            private int dxx;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        dxx = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        dyy = (int) event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //移动中动态设置位置
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }
                        v.layout(left, top, right, bottom);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                        case MotionEvent.ACTION_UP:
                            Log.e("fanfan","------------------------chakan"+event.getRawX()+"--"+dxx+"---"+event.getRawY()+"--"+dyy);
                            float xabs = Math.abs(event.getRawX() - dxx);
                            float yabs = Math.abs(event.getRawY() - dyy);
                            if (xabs<5&&yabs<5){
                                if (webView.canGoBack()){
                                    webView.goBack();
                                }else {
                                    finish();
                                }
                            }
                            return true;
                }
                return false;
        }});




        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()){
                    webView.goForward();
                }
            }
        });
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainAty.this);
                builder.setTitle("清除缓存吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                webView.clearCache(true);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog=null;
        webView=null;
    }

    class MyWebViewClient extends WebViewClient {
        /**
         * 记录URL的栈
         */
        private final Stack<String> mUrls = new Stack<>();
        /**
         * 判断页面是否加载完成
         */
        private boolean mIsLoading;
        private String mUrlBeforeRedirect;
        @Override
        public void onPageStarted(android.webkit.WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            if (mIsLoading && mUrls.size() > 0) {
                mUrlBeforeRedirect = mUrls.pop();
            }
            recordUrl(s);
            if (loadingDialog!=null)
            loadingDialog.show();
            this.mIsLoading = true;
        }
        /**
         * 记录非重定向链接, 避免刷新页面造成的重复入栈
         *
         * @param url 链接
         */
        private void recordUrl(String url) {
            //这里还可以根据自身业务来屏蔽一些链接被放入URL栈
            if (!TextUtils.isEmpty(url) && !url.equalsIgnoreCase(getLastPageUrl())) {
                mUrls.push(url);
            } else if (!TextUtils.isEmpty(mUrlBeforeRedirect)) {
                mUrls.push(mUrlBeforeRedirect);
                mUrlBeforeRedirect = null;
            }
        }
        /**
         * 获取上一页的链接
         **/
        private synchronized String getLastPageUrl() {
            return mUrls.size() > 0 ? mUrls.peek() : null;
        }
        /**
         * 推出上一页链接
         */
        public String popLastPageUrl() {
            if (mUrls.size() >= 2) {
                mUrls.pop(); //当前url
                return mUrls.pop();
            }
            return null;
        }
        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
            if (this.mIsLoading) {
                this.mIsLoading = false;
            }
            if (loadingDialog!=null)
            loadingDialog.dismiss();
            String sUrl = url.replace("/", "");
            if (sUrl.equals(s.replace("/",""))
                    || sUrl.equals(n1.replace("/",""))
                    ||sUrl.equals(n3.replace("/",""))
                    ||sUrl.equals(n4.replace("/",""))){
                btBack.setVisibility(View.GONE);
            }else {
                btBack.setVisibility(View.VISIBLE);
            }
            //获取cookies
            CookieManager cm = CookieManager.getInstance();
            String cookies = cm.getCookie(url);
            SPUtil.INSTANCE.saveCook(getApplicationContext(),cookies);
            isFirst=false;
            if (loadingDialog!=null)
            loadingDialog.dismiss();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /*if (lastUrl.contains("LoginToGame")){
                if (url.contains("Register")||url.contains("messenger")){
                    view.loadUrl(url);
                }else {
                    view.loadUrl(baseUrl);
                }
            }else {
                view.loadUrl(url); // 在当前的webview中跳转到新的url
            }
            */
            lastUrl=url;
            hisUrl.add(url);
            Log.e("fanfan","载入Url"+url);
            view.loadUrl(url); // 在当前的webview中跳转到新的url

            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=-1)return;
        webView.loadUrl(baseUrl);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
//        pageGoBack(webView,client);
    }

    @Override
    public void doLogin(ISetSDKAuthListener iSetSDKAuthListener) {

    }

    @Override
    public void onHandle(String s, String s1, IHandlerCallback iHandlerCallback) {

    }
}
