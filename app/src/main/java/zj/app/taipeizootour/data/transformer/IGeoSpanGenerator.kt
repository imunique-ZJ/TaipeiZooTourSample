package zj.app.taipeizootour.data.transformer

import android.text.Spannable
import org.locationtech.jts.geom.Coordinate

interface IGeoSpanGenerator {
    suspend fun generate(location: String,
                         delimiter: String,
                         coordinates: List<Coordinate>?): Spannable
}