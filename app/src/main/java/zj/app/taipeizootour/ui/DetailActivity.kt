package zj.app.taipeizootour.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import zj.app.taipeizootour.api.TaipeiOpenDataApi
import zj.app.taipeizootour.databinding.ActivityDetailBinding
import zj.app.taipeizootour.db.ZooDatabase
import zj.app.taipeizootour.ext.getViewModel
import zj.app.taipeizootour.repo.ZooRepo
import zj.app.taipeizootour.viewmodel.DetailActivityViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var vb: ActivityDetailBinding
    private lateinit var vm: DetailActivityViewModel

    companion object {
        const val INTENT_KEY_AREA_ID = "areaId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vm = getViewModel {
            val db = ZooDatabase.getDatabase(applicationContext)
            DetailActivityViewModel(
                ZooRepo(
                    TaipeiOpenDataApi,
                    db.zooAreaDao(),
                    db.zooPlantDao(),
                    db.areaPlantsDao()
                )
            )
        }
        intent?.getIntExtra(INTENT_KEY_AREA_ID, -1)
            ?.takeIf { it != -1 }
            ?.let { vm.fetchData(it) }
    }
}