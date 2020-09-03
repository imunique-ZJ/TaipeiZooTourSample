package zj.app.taipeizootour.data.transformer

import android.content.Intent
import android.net.Uri
import android.text.style.ClickableSpan
import android.view.View
import org.locationtech.jts.geom.Coordinate
import zj.app.taipeizootour.ext.safeStartActivity

class GeoClickableSpan(
    private val coordinate: Coordinate,
    private val name: String
): ClickableSpan() {

    private val schemePattern = "geo:%s,%s?q=%s"

    override fun onClick(widget: View) {
        val encodedName = Uri.encode(name)
        val uri = Uri.parse(schemePattern.format(coordinate.y, coordinate.x, encodedName))
        val mapIntent = Intent(Intent.ACTION_VIEW, uri).setPackage("com.google.android.apps.maps")
        widget.context.safeStartActivity(mapIntent)
    }
}