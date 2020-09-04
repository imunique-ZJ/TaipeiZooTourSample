package zj.app.taipeizootour.data.transformer

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.io.WKTReader
import javax.inject.Inject

class DefaultGeoReader @Inject constructor(
    private val wktReader: WKTReader
): IGeoReader {

    override suspend fun read(wktGeoStr: String): List<Coordinate>? {
        return try {
            wktReader.read(wktGeoStr)?.coordinates?.toList()
        } catch (e: Exception) {
            null
        }
    }

}