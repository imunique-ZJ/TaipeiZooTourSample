package zj.app.taipeizootour.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZooArea(
    @PrimaryKey
    val areaId: Int,
    val category: String,
    val name: String,
    val picUrl: String,
    val info: String,
    val memo: String,
    val latLng: String,
    val introUrl: String
)