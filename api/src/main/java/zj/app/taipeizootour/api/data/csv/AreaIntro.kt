package zj.app.taipeizootour.api.data.csv

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AreaIntro(
    @JsonProperty("E_no")
    val no: String?,
    @JsonProperty("E_Category")
    val category: String?,
    @JsonProperty("E_Name")
    val name: String?,
    @JsonProperty("E_Pic_URL")
    val picUrl: String?,
    @JsonProperty("E_Info")
    val info: String?,
    @JsonProperty("E_Memo")
    val memo: String?,
    @JsonProperty("E_Geo")
    val latLng: String,
    @JsonProperty("E_URL")
    val introUrl: String
): ICsvDataType