package tmproject.hlhj.fhp.webapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebViewClient;
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

import tmproject.hlhj.fhp.webapp.web.WebSocketFactory;

/**
 * Created by Never Fear   on 2018\9\20 0020.
 * Never More....
 */

public class MainAty2 extends AppCompatActivity implements IReadWapCallback, IRegisterNativeFunctionCallback {
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
    private String n4="/AnyTimeDiscount";
    private List<String> hisUrl=new ArrayList<>();
    private MyWebViewClient client;
    private LinearLayout lo;
    private AgentWeb agent;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getData();
        initView();
        client=new MyWebViewClient();
        initWeb();
        setWeb();
        setListener();
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
    @SuppressLint("JavascriptInterface")
    private void setWeb() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebSocketFactory(webView),"WebSocketFactory");
        webView.setWebViewClient(client);
        baseUrl=s;
        hisUrl.add(s);
        synCookies(this, baseUrl);
        webView.loadUrl(baseUrl);
    }
/*
*
* ;  protalnew=ff0d538ba46f4c29889d6da3cdaa27e5;  nohostname_ip=61099E93G125CF960A52E*/
/*
* ;nohostname_ip=61099E93G125CF960A530; protalnew=61b3d9847ff248478f885d330e3a26d5; .ASPXAUTHFORPORTAL2018=2A3B15B7A5485A399D2A3209407B5E72174BEB8B48D53FC3F77B68859C73A863B7408FECB6174C533585E45CB32E21C787B018E505D3A02FDABDAE284DDF23B8282A1897A3D2C0D768012CCCE33E9A8308FF7C4A6FF8785F805DA274DEFF1235BF918030C8551981BBEEB624CA6E34C6EAEEC96759C3789E8833A9A7AF452EB601D78221A3801C4A3EAE4AD9E83E108F0DB9C7715F14F7C6C2864ED4AFB66E8961620E7F6F81765767FA22505EAC0DA14F74F89FB8F85EF08F2178BEB52A135F5F5F0947359BB6D2758F37BC2C91125316744AD50F079A0AA506ADE5ABFCFC25151CFA481178C43AED42204850918DF6BC469F835B89ABFA15646C3CD9D61B572EF591CA
 */
    private void initView() {
        loadingDialog=new LoadingDialog(this);
//        s="https://www.we790.com/";
        getSupportActionBar().hide();
        lo = findViewById(R.id.lo);
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

    private void getData() {
        String url = getIntent().getStringExtra("url");
//        String url = "http://s1202.sg3.ledu.com/";
        s=url;
        baseUrl=s;
        n1=baseUrl+n1;
        n3=baseUrl+n3;
        n4=baseUrl+n4;

        String[] strings = n2.split("/");
        for (int i = 0; i < strings.length; i++) {
            Log.e("fanfan","----"+strings[i]);
        }
    }

    private void initWeb() {
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
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

    private void initPush() {
        PushManager.getInstance().initialize(this,MyService.class);
//        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyService2.class);

    }

    private void setListener() {
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(baseUrl);
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(-1);
                finish();
            }
        });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainAty2.this);
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
        if (loadingDialog!=null)
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
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            if (mIsLoading && mUrls.size() > 0) {
                mUrlBeforeRedirect = mUrls.pop();
            }
            Log.e("fanfan2","记录"+s);
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
        public void onPageFinished(WebView view, String url) {
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
            isFirst=false;
            if (loadingDialog!=null)
                loadingDialog.dismiss();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (lastUrl.contains("LoginToGame")){
                if (url.contains("Register")||url.contains("messenger")){
                    view.loadUrl(url);
                }else {
                    view.loadUrl(baseUrl);
                }
            }else {
                view.loadUrl(url); // 在当前的webview中跳转到新的url
            }
            view.loadUrl(url);
            lastUrl=url;
            Log.e("fanfan","载入"+url);
            hisUrl.add(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(-1);
        finish();
    }

    @Override
    public void doLogin(ISetSDKAuthListener iSetSDKAuthListener) {

    }

    @Override
    public void onHandle(String s, String s1, IHandlerCallback iHandlerCallback) {

    }
}
