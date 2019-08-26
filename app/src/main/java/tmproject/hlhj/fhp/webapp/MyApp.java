package tmproject.hlhj.fhp.webapp;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

import cn.jpush.android.api.JPushInterface;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);

        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e("fanfanfan","加载内核是否成功");
            }
        });
    }
}
