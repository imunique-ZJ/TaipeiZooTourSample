package zj.app.taipeizootour.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import zj.app.taipeizootour.R
import zj.app.taipeizootour.api.TaipeiOpenDataApi
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.databinding.ActivityMainBinding
import zj.app.taipeizootour.db.ZooDatabase
import zj.app.taipeizootour.ext.getViewModel
import zj.app.taipeizootour.repo.ZooRepo
import zj.app.taipeizootour.viewmodel.MainActivityViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding
    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vm = getViewModel {
            val db = ZooDatabase.getDatabase(applicationContext)
            val timeFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault())
            MainActivityViewModel(
                getSharedPreferences(Constants.KEY_SHARED_PREF_NAME, Context.MODE_PRIVATE),
                timeFormat,
                ZooRepo(
                    TaipeiOpenDataApi,
                    db.zooAreaDao(),
                    db.zooPlantDao(),
                    db.areaPlantsDao()
                )
            )
        }
        vm.fetchData(getString(R.string.query_meta_area_intro), getString(R.string.query_meta_plants))
    }

}