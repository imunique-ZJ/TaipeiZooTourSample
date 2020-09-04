package zj.app.taipeizootour.data.transformer

import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.text.inSpans
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.locationtech.jts.geom.Coordinate
import javax.inject.Inject

class DefaultGeoSpanGenerator @Inject constructor(): IGeoSpanGenerator {

    override suspend fun generate(
        location: String,
        delimiter: String,
        coordinates: List<Coordinate>?
    ): Spannable {
        return withContext(Dispatchers.Default) {
            SpannableStringBuilder().apply {
                if (coordinates != null) {
                    val locations = location.split(delimiter)
                    locations.forEachIndexed { index, loc ->
                        val span = GeoClickableSpan(coordinates[index], loc)
                        inSpans(span) { append(loc) }
                            .append(delimiter)
                    }
                } else {
                    append(location)
                }
            }
        }
    }

}