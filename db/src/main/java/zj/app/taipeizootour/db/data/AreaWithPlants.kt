package zj.app.taipeizootour.db.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import zj.app.taipeizootour.db.model.AreaPlantsCrossRef
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.db.model.ZooPlant

data class AreaWithPlants(
    @Embedded val area: ZooArea,
    @Relation(
        parentColumn = "areaId",
        entityColumn = "plantId",
        associateBy = Junction(AreaPlantsCrossRef::class)
    )
    val plants: List<ZooPlant>
)