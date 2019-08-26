package tmproject.hlhj.fhp.webapp

import android.content.Context

object SPUtil {
    private var key="cook"

    public fun saveCook(c:Context,cook:String?){
        cook?.let {
            c.getSharedPreferences(key,Context.MODE_PRIVATE).edit().putString("cooki",cook).apply()

        }
    }

    fun getCook(c:Context): String {
        return c.getSharedPreferences(key,Context.MODE_PRIVATE).getString("cooki","");
    }
}