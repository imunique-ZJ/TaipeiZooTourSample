package zj.app.taipeizootour.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zj.app.taipeizootour.R
import zj.app.taipeizootour.const.AnimConstants
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.databinding.ActivityMainBinding
import zj.app.taipeizootour.databinding.LayoutZooRecyclerviewItemBinding
import zj.app.taipeizootour.db.ZooDatabase
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.ext.getViewModel
import zj.app.taipeizootour.repo.IZooRepo
import zj.app.taipeizootour.viewmodel.MainActivityViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ZooAreaFragment.OnAreaSelected,
    ZooAreaDetailFragment.OnPlantSelected {

    private lateinit var vb: ActivityMainBinding
    private lateinit var vm: MainActivityViewModel

    @Inject
    lateinit var zooRepo: IZooRepo

    private val areaListTag = "areaList"
    private val areaDetailTag = "areaDetail"
    private val plantDetailTag = "plantDetail"

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
                zooRepo
            )
        }
        vm.fetchData(getString(R.string.query_meta_area_intro), getString(R.string.query_meta_plants))
    }

    override fun onAreaSelected(itemVb: LayoutZooRecyclerviewItemBinding, area: ZooArea) {
        lifecycleScope.launch {
            vm.fetchAreaPlants(area.areaId)
        }
        supportFragmentManager.commit {
            supportFragmentManager.findFragmentByTag(areaListTag)?.run {
                exitTransition = Hold().apply {
                    duration = AnimConstants.SHARED_ELEMENT_DURATION
                }
            }
            val detailFragment = ZooAreaDetailFragment()
            detailFragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = AnimConstants.SHARED_ELEMENT_DURATION
                scrimColor = ContextCompat.getColor(this@MainActivity, android.R.color.white)
            }

            setReorderingAllowed(true)
            addSharedElement(itemVb.root, itemVb.root.transitionName)
            replace(R.id.fragmentContainer, detailFragment, areaDetailTag)
            addToBackStack(areaDetailTag)
        }
    }

    override fun onPlantSelected(itemVb: LayoutZooRecyclerviewItemBinding, plant: ZooPlant) {
        supportFragmentManager.commit {
            val detailFragment = supportFragmentManager.findFragmentByTag(areaDetailTag)
            detailFragment?.exitTransition = Hold().apply {
                duration = AnimConstants.SHARED_ELEMENT_DURATION
            }
            val plantDetailFragment = PlantDetailFragment()
            plantDetailFragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = AnimConstants.SHARED_ELEMENT_DURATION
            }

            setReorderingAllowed(true)
            addSharedElement(itemVb.ivPic, itemVb.ivPic.transitionName)
            replace(R.id.fragmentContainer, plantDetailFragment, plantDetailTag)
            addToBackStack(plantDetailTag)
        }
    }
}