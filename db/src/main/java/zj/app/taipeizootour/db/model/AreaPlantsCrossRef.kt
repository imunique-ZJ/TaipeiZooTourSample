package zj.app.taipeizootour.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["areaId", "plantId"])
data class AreaPlantsCrossRef(
    val areaId: Int,
    val plantId: Int
)