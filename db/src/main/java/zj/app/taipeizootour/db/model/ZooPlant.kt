package zj.app.taipeizootour.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZooPlant(
    @PrimaryKey
    override val plantId: Int,
    override val latinName: String,
    override val chName: String,
    override val enName: String,
    override val alsoKnown: String,
    override val location: String,
    override val geo: String,
    override val brief: String,
    override val summary: String?,
    override val feature: String,
    override val family: String,
    override val genus: String,
    override val function: String,
    override val updateDate: String,
    override val pic01Url: String?,
    override val pic01Alt: String?,
    override val pic02Url: String?,
    override val pic02Alt: String?,
    override val pic03Url: String?,
    override val pic03Alt: String?,
    override val pic04Url: String?,
    override val pic04Alt: String?
): IZooPlant