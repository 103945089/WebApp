package tmproject.hlhj.fhp.webapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle

class LoadingDialog(private  val  c:Context) :Dialog(c,R.style.MyDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_l)
    }
}