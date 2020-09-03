package zj.app.taipeizootour.data.transformer

import android.content.Intent
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.text.inSpans
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.io.WKTReader
import zj.app.taipeizootour.data.DerivedZooPlant
import zj.app.taipeizootour.db.model.ZooPlant
import javax.inject.Inject

class ZooPlantTransformer @Inject constructor(
    private val wktReader: WKTReader
): IDataTransformer<ZooPlant, DerivedZooPlant> {

    private val schemePattern = "geo:%s,%s?q=%s"
    private val locationDelimiter = "；"

    private inner class GeoClickableSpan(
        private val coordinate: Coordinate,
        private val name: String
    ): ClickableSpan() {
        override fun onClick(widget: View) {
            val uri = Uri.parse(schemePattern.format(coordinate.y, coordinate.x, name))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            widget.context.startActivity(intent)
        }
    }

    override fun transform(data: ZooPlant?): DerivedZooPlant? {
        return data?.let { zooPlant ->
            DerivedZooPlant(zooPlant).apply {
                val coordinates = try {
                    wktReader.read(geo)?.coordinates
                } catch (e: Exception) {
                    null
                }

                val sb = SpannableStringBuilder()
                if (coordinates != null) {
                    val locations = location.split("；")
                    locations.forEachIndexed { index, loc ->
                        val coordinate = coordinates[index]
                        val span = GeoClickableSpan(coordinate, loc)
                        sb.inSpans(span) { append(loc) }
                            .append(locationDelimiter)
                    }
                } else {
                    sb.append(location)
                }
                geoMapLocation = sb
            }
        }
    }

}