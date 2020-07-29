package zj.app.taipeizootour.api.data.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Plant(
    @JsonProperty("F_Name_Latin")
    val latinName: String,

    @JsonProperty("\uFEFFF_Name_Ch")
    val chNameWithBom: String?,

    @JsonProperty("F_Name_Ch")
    val chName: String?,

    @JsonProperty("F_Name_En")
    val enName: String,

    @JsonProperty("F_AlsoKnown")
    val alsoKnown: String,

    @JsonProperty("F_Location")
    val location: String,

    @JsonProperty("F_Geo")
    val geo: String,

    @JsonProperty("F_Brief")
    val brief: String,

    @JsonProperty("F_Summary")
    val summary: String,

    @JsonProperty("F_Feature")
    val feature: String,

    @JsonProperty("F_Family")
    val family: String,

    @JsonProperty("F_Genus")
    val genus: String,

    @JsonProperty("F_Function＆Application")
    val function: String,

    @JsonProperty("F_Update")
    val updateDate: String,

    @JsonProperty("F_Pic01_URL")
    val pic01Url: String,

    @JsonProperty("F_Pic01_ALT")
    val pic01Alt: String,

    @JsonProperty("F_Pic02_URL")
    val pic02Url: String,

    @JsonProperty("F_Pic02_ALT")
    val pic02Alt: String,

    @JsonProperty("F_Pic03_URL")
    val pic03Url: String,

    @JsonProperty("F_Pic03_ALT")
    val pic03Alt: String,

    @JsonProperty("F_Pic04_URL")
    val pic04Url: String,

    @JsonProperty("F_Pic04_ALT")
    val pic04Alt: String
)