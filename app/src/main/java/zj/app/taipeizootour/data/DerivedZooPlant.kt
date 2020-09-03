package zj.app.taipeizootour.data

import android.text.Spannable
import zj.app.taipeizootour.db.model.IZooPlant
import zj.app.taipeizootour.db.model.ZooPlant

class DerivedZooPlant(plant: ZooPlant): IZooPlant by plant {
    var geoMapLocation: Spannable? = null
}