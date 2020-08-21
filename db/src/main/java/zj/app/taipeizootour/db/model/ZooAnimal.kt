package zj.app.taipeizootour.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZooAnimal(
    @PrimaryKey
    val animalId: Int,
    val code: String,
    val location: String,
    val latinName: String,
    val chName: String,
    val enName: String,
    val alsoKnown: String,
    val geo: String,
    // 門
    val phylum: String,
    // 綱
    val clazz: String,
    // 目
    val order: String,
    // 科
    val family: String,
    val behavior: String,
    val distribution: String,
    val feature: String,
    val diet: String,
    val habitat: String,
    val conservation: String,
    val crisis: String,
    val interpretation: String,
    val summary: String,
    val updateDate: String,
    val pic01Url: String,
    val pic01Alt: String,
    val pic02Url: String,
    val pic02Alt: String,
    val pic03Url: String,
    val pic03Alt: String,
    val pic04Url: String,
    val pic04Alt: String,
    val voice01Url: String,
    val voice01Alt: String,
    val voice02Url: String,
    val voice02Alt: String,
    val voice03Url: String,
    val voice03Alt: String,
    val videoUrl: String,
    val pdf01Url: String,
    val pdf01Alt: String,
    val pdf02Url: String,
    val pdf02Alt: String
)