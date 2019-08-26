package tmproject.hlhj.fhp.webapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.aty_init.*
import java.lang.Exception

class InitAty :AppCompatActivity() {
    private var url="http://www.baidu.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aty_init)
        supportActionBar?.hide()
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        OkGo.get<String>("http://app.schlhjnetworktesturl.com/app/v1/public/config")
                .execute(object :StringCallback(){
                    override fun onSuccess(response: Response<String>) {
                        val json = Gson().fromJson(response.body(), A::class.java)
                        try {
                            Glide.with(this@InitAty).load(json.data.back).into(bg)
                        }catch (e:Exception){
                        }
                        url=json.data.url;
                        Thread(Runnable {
                            Thread.sleep(3000)
                            runOnUiThread {
                                val intent = Intent(this@InitAty, MainAty::class.java)
                                intent.putExtra("url",url)
                                startActivity(intent)
                                finish()
                            }
                        }).start()
                    }
                })


    }
}