package zj.app.taipeizootour.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["areaId", "animalId"])
data class AreaAnimalsCrossRef(
    val areaId: Int,
    val animalId: Int
)