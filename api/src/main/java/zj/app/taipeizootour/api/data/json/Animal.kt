package zj.app.taipeizootour.api.data.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Animal(
    @JsonProperty("_id")
    val id: Int,

    @JsonProperty("A_Code")
    val code: String,

    @JsonProperty("A_Location")
    val location: String,

    @JsonProperty("A_Name_Latin")
    val latinName: String,

    @JsonProperty("\uFEFFA_Name_Ch")
    val chName: String,

    @JsonProperty("A_Name_En")
    val enName: String,

    @JsonProperty("A_AlsoKnown")
    val alsoKnown: String,

    @JsonProperty("A_Geo")
    val geo: String,

    // 門
    @JsonProperty("A_Phylum")
    val phylum: String,

    // 綱
    @JsonProperty("A_Class")
    val clazz: String,

    // 目
    @JsonProperty("A_Order")
    val order: String,

    // 科
    @JsonProperty("A_Family")
    val family: String,

    @JsonProperty("A_Behavior")
    val behavior: String,

    @JsonProperty("A_Distribution")
    val distribution: String,

    @JsonProperty("A_Feature")
    val feature: String,

    @JsonProperty("A_Diet")
    val diet: String,

    @JsonProperty("A_Habitat")
    val habitat: String,

    @JsonProperty("A_Conservation")
    val conservation: String,

    @JsonProperty("A_Crisis")
    val crisis: String,

    @JsonProperty("A_Interpretation")
    val interpretation: String,

    @JsonProperty("A_Summary")
    val summary: String,

    @JsonProperty("A_Update")
    val updateDate: String,

    @JsonProperty("A_Pic01_URL")
    val pic01Url: String,

    @JsonProperty("A_Pic01_ALT")
    val pic01Alt: String,

    @JsonProperty("A_Pic02_URL")
    val pic02Url: String,

    @JsonProperty("A_Pic02_ALT")
    val pic02Alt: String,

    @JsonProperty("A_Pic03_URL")
    val pic03Url: String,

    @JsonProperty("A_Pic03_ALT")
    val pic03Alt: String,

    @JsonProperty("A_Pic04_URL")
    val pic04Url: String,

    @JsonProperty("A_Pic04_ALT")
    val pic04Alt: String,

    @JsonProperty("A_Voice01_URL")
    val voice01Url: String,

    @JsonProperty("A_Voice01_ALT")
    val voice01Alt: String,

    @JsonProperty("A_Voice02_URL")
    val voice02Url: String,

    @JsonProperty("A_Voice02_ALT")
    val voice02Alt: String,

    @JsonProperty("A_Voice03_URL")
    val voice03Url: String,

    @JsonProperty("A_Voice03_ALT")
    val voice03Alt: String,

    @JsonProperty("A_Vedio_URL")
    val videoUrl: String,

    @JsonProperty("A_pdf01_URL")
    val pdf01Url: String,

    @JsonProperty("A_pdf01_ALT")
    val pdf01Alt: String,

    @JsonProperty("A_pdf02_URL")
    val pdf02Url: String,

    @JsonProperty("A_pdf02_ALT")
    val pdf02Alt: String
)
