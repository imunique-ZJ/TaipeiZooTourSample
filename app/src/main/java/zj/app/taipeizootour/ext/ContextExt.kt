package zj.app.taipeizootour.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

fun Context.safeStartActivity(intent: Intent,
                              queryFlag: Int = PackageManager.MATCH_DEFAULT_ONLY,
                              onSuccess: (() -> Unit)? = null,
                              noActivityFound: (() -> Unit)? = null) {
    val activities = packageManager.queryIntentActivities(intent, queryFlag)
    if (activities.isNotEmpty()) {
        startActivity(intent)
        onSuccess?.invoke()
    } else {
        noActivityFound?.invoke()
    }
}