package zj.app.taipeizootour.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import zj.app.taipeizootour.R
import zj.app.taipeizootour.api.TaipeiOpenDataApi
import zj.app.taipeizootour.const.AnimConstants
import zj.app.taipeizootour.databinding.ActivityDetailBinding
import zj.app.taipeizootour.databinding.LayoutZooRecyclerviewItemBinding
import zj.app.taipeizootour.db.ZooDatabase
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.ext.getViewModel
import zj.app.taipeizootour.repo.ZooRepo
import zj.app.taipeizootour.viewmodel.DetailActivityViewModel

class DetailActivity : AppCompatActivity(), ZooAreaDetailFragment.OnPlantSelected {

    private lateinit var vb: ActivityDetailBinding
    private lateinit var vm: DetailActivityViewModel

    private val areaDetailTag = "areaDetail"
    private val plantDetailTag = "plantDetail"

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

    override fun onPlantSelected(vb: LayoutZooRecyclerviewItemBinding, plant: ZooPlant) {
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
            addSharedElement(vb.root, vb.root.transitionName)
            replace(R.id.fragmentContainer, plantDetailFragment, plantDetailTag)
            addToBackStack(plantDetailTag)
        }
    }
}