package tmproject.hlhj.fhp.webapp

import android.content.Context
import android.util.Log
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage

/**
 * Created by Never Fear   on 2018\9\20 0020.
Never More....
 */
class MyService2 : GTIntentService() {
    override fun onReceiveMessageData(p0: Context?, p1: GTTransmitMessage?) {
        Log.e("fhpp","1")
    }

    override fun onNotificationMessageArrived(p0: Context?, p1: GTNotificationMessage?) {
        Log.e("fhpp","2")

    }

    override fun onReceiveServicePid(p0: Context?, p1: Int) {
        Log.e("fhpp","3")

    }

    override fun onNotificationMessageClicked(p0: Context?, p1: GTNotificationMessage?) {
        Log.e("fhpp","4")

    }

    override fun onReceiveCommandResult(p0: Context?, p1: GTCmdMessage?) {
        Log.e("fhpp","5")

    }

    override fun onReceiveClientId(p0: Context?, p1: String?) {
        Log.e("fhpp","6")

    }

    override fun onReceiveOnlineState(p0: Context?, p1: Boolean) {
        Log.e("fhpp","7")

    }
}