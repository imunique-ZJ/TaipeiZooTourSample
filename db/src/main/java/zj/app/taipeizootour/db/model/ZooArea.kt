package zj.app.taipeizootour.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZooArea(
    @PrimaryKey
    val areaId: String,
    val title: String,
    val type: String,
    val category: String,
    val metadataModified: String,
    val issued: String,
    val numberOfData: Int,
    val orgId: String,
    val orgName: String,
    val organizationName: String,
    val subOrgId: String,
    val subOrgName: String,
    val tag: String
)