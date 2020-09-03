package zj.app.taipeizootour.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZooAnimal(
    @PrimaryKey
    override val animalId: Int,
    override val code: String,
    override val location: String,
    override val latinName: String,
    override val chName: String,
    override val enName: String,
    override val alsoKnown: String,
    override val geo: String,
    // 門
    override val phylum: String,
    // 綱
    override val clazz: String,
    // 目
    override val order: String,
    // 科
    override val family: String,
    override val behavior: String,
    override val distribution: String,
    override val feature: String,
    override val diet: String,
    override val habitat: String,
    override val conservation: String,
    override val crisis: String,
    override val interpretation: String,
    override val summary: String,
    override val updateDate: String,
    override val pic01Url: String,
    override val pic01Alt: String,
    override val pic02Url: String,
    override val pic02Alt: String,
    override val pic03Url: String,
    override val pic03Alt: String,
    override val pic04Url: String,
    override val pic04Alt: String,
    override val voice01Url: String,
    override val voice01Alt: String,
    override val voice02Url: String,
    override val voice02Alt: String,
    override val voice03Url: String,
    override val voice03Alt: String,
    override val videoUrl: String,
    override val pdf01Url: String,
    override val pdf01Alt: String,
    override val pdf02Url: String,
    override val pdf02Alt: String
): IZooAnimal