package zj.app.taipeizootour.db.model

interface IZooPlant {
    val plantId: Int
    val latinName: String
    val chName: String
    val enName: String
    val alsoKnown: String
    val location: String
    val geo: String
    val brief: String
    val summary: String?
    val feature: String
    val family: String
    val genus: String
    val function: String
    val updateDate: String
    val pic01Url: String?
    val pic01Alt: String?
    val pic02Url: String?
    val pic02Alt: String?
    val pic03Url: String?
    val pic03Alt: String?
    val pic04Url: String?
    val pic04Alt: String?
}