package zj.app.taipeizootour.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import zj.app.taipeizootour.R
import zj.app.taipeizootour.const.AnimConstants
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.databinding.ActivityMainBinding
import zj.app.taipeizootour.databinding.LayoutZooRecyclerviewItemBinding
import zj.app.taipeizootour.db.model.ZooAnimal
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.repo.IZooRepo
import zj.app.taipeizootour.viewmodel.MainActivityViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ZooAreaFragment.OnAreaSelected,
    AreaPlantListFragment.OnPlantSelected,
    AreaAnimalListFragment.OnAnimalSelected {

    private lateinit var vb: ActivityMainBinding
    private val vm: MainActivityViewModel by viewModels()

    @Inject
    lateinit var zooRepo: IZooRepo

    private val areaListTag = "areaList"
    private val areaDetailTag = "areaDetail"
    private val plantDetailTag = "plantDetail"
    private val animalDetailTag = "animalDetail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vm.fetchData(
            getString(R.string.query_meta_area_intro),
            getString(R.string.query_meta_plants),
            getString(R.string.query_meta_animals)
        )
    }

    override fun onAreaSelected(itemVb: LayoutZooRecyclerviewItemBinding, area: ZooArea) {
        vm.selectArea(area)
        supportFragmentManager.commit {
            supportFragmentManager.findFragmentByTag(areaListTag)?.run {
                exitTransition = Hold().apply {
                    duration = AnimConstants.SHARED_ELEMENT_DURATION
                }
            }
            val detailFragment = ZooAreaDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.ARG_KEY_AREA_ID, area.areaId)
                }
            }
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
        vm.selectPlant(plant)
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

    override fun onAnimalSelected(itemVb: LayoutZooRecyclerviewItemBinding, animal: ZooAnimal) {
        vm.selectAnimal(animal)
        supportFragmentManager.commit {
            val detailFragment = supportFragmentManager.findFragmentByTag(areaDetailTag)
            detailFragment?.exitTransition = Hold().apply {
                duration = AnimConstants.SHARED_ELEMENT_DURATION
            }
            val animalDetailFragment = AnimalDetailFragment()
            animalDetailFragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = AnimConstants.SHARED_ELEMENT_DURATION
            }

            setReorderingAllowed(true)
            addSharedElement(itemVb.ivPic, itemVb.ivPic.transitionName)
            replace(R.id.fragmentContainer, animalDetailFragment, animalDetailTag)
            addToBackStack(animalDetailTag)
        }
    }
}