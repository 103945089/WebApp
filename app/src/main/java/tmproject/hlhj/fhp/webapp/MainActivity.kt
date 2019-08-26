package tmproject.hlhj.fhp.webapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    private var s=""
    private var url=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        encode()

        OkGo.get<String>("http://119.29.29.29/d?dn=${encodeedStr}&id=8038")
                .execute(object :StringCallback(){
                    override fun onSuccess(response: Response<String>?) {

                    }
                })
    }

    private val encKey="lATgLQXR"

    private var encryptedString: String?=null
    private var encodeedStr=""
    private fun encode(){
        try {
            //初始化密钥
            val keySpec = SecretKeySpec(encKey.toByteArray(charset("utf-8")), "DES")
            //选择使用 DES 算法，ECB 方式，填充方式为 PKCS5Padding
            val cipher = Cipher.getInstance("DES/ECB/PKCS5Padding")
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            //获取加密后的字符串
            val encryptedString = cipher.doFinal(s.toByteArray(charset("utf-8")))
            encodeedStr =  String(encryptedString!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
