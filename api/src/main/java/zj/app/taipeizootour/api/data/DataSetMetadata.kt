package zj.app.taipeizootour.api.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataSetMetadata(
    val id: String,
    val title: String,
    val type: String,
    val category: String,
    @JsonProperty("metadata_modified")
    val metadataModified: String,
    val issued: String,
    val numberOfData: Int,
    val orgId: String,
    val orgName: String,
    val organizationName: String,
    val subOrgId: String,
    val subOrgName: String,
    val tag: String,
    val resources: List<Resource>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Resource(
    val resourceId: String,
    val resourceName: String,
    val resourceDescription: String?,
    val resourceUpdate: String,
    val resourceEncoding: String,
    val format: String
)