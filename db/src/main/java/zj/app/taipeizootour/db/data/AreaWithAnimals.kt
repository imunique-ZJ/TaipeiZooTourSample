package zj.app.taipeizootour.db.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import zj.app.taipeizootour.db.model.AreaAnimalsCrossRef
import zj.app.taipeizootour.db.model.ZooAnimal
import zj.app.taipeizootour.db.model.ZooArea

data class AreaWithAnimals(
    @Embedded val area: ZooArea,
    @Relation(
        parentColumn = "areaId",
        entityColumn = "animalId",
        associateBy = Junction(AreaAnimalsCrossRef::class)
    )
    val animals: List<ZooAnimal>
)