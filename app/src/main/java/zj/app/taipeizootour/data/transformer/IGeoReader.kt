package zj.app.taipeizootour.data.transformer

import org.locationtech.jts.geom.Coordinate

interface IGeoReader {
    suspend fun read(wktGeoStr: String): List<Coordinate>?
}