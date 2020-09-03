package zj.app.taipeizootour.data

import android.text.Spannable
import zj.app.taipeizootour.db.model.IZooAnimal
import zj.app.taipeizootour.db.model.ZooAnimal

class DerivedZooAnimal(animal: ZooAnimal): IZooAnimal by animal {
    var geoMapLocation: Spannable? = null
}