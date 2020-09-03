package zj.app.taipeizootour.data.transformer

import android.text.SpannableStringBuilder
import androidx.core.text.inSpans
import org.locationtech.jts.io.WKTReader
import zj.app.taipeizootour.data.DerivedZooPlant
import zj.app.taipeizootour.db.model.ZooPlant
import javax.inject.Inject

class ZooPlantTransformer @Inject constructor(
    private val wktReader: WKTReader
): IDataTransformer<ZooPlant, DerivedZooPlant> {

    private val locationDelimiter = "；"

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