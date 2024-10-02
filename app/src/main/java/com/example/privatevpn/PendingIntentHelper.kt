import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object PendingIntentHelper {

    fun getPendingIntent(context: Context, intent: Intent, requestCode: Int): PendingIntent {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        return PendingIntent.getActivity(context, requestCode, intent, flags)
    }
}
