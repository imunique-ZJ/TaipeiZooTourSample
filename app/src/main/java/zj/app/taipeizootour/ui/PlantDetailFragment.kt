package zj.app.taipeizootour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_plant_detail.*
import zj.app.taipeizootour.R
import zj.app.taipeizootour.adapter.PlantPictureAdapter
import zj.app.taipeizootour.databinding.FragmentPlantDetailBinding
import zj.app.taipeizootour.viewmodel.DetailActivityViewModel

class PlantDetailFragment: Fragment() {

    private var _vb: FragmentPlantDetailBinding? = null
    private val vb get() = _vb!!

    private val vm: DetailActivityViewModel by activityViewModels()
    private val plantPicsAdapter = PlantPictureAdapter()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _vb = FragmentPlantDetailBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        bindLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        plantPicsAdapter.unregisterAdapterDataObserver(pagerIndicator.adapterDataObserver)
        _vb = null
    }

    private fun setupViewPager() {
        vb.vpPlantPics.adapter = plantPicsAdapter
        vb.pagerIndicator.setViewPager(vb.vpPlantPics)
        plantPicsAdapter.registerAdapterDataObserver(pagerIndicator.adapterDataObserver)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun bindLiveData() {
        vm.selectedPlantIdLiveData.observe(viewLifecycleOwner, Observer { zooPlant ->
            zooPlant?.let { plant ->
                val pics = buildList {
                    plant.pic01Url?.takeIf { it.isNotEmpty() }?.let { add(it) }
                    plant.pic02Url?.takeIf { it.isNotEmpty() }?.let { add(it) }
                    plant.pic03Url?.takeIf { it.isNotEmpty() }?.let { add(it) }
                    plant.pic04Url?.takeIf { it.isNotEmpty() }?.let { add(it) }
                }
                plantPicsAdapter.submitList(pics)

                vb.layoutTitle.tvTitle.text = plant.chName
                vb.layoutTitle.tvContent.text = plant.enName

                vb.layoutAlias.tvTitle.text = getString(R.string.app_plant_alias)
                vb.layoutAlias.tvContent.text = plant.alsoKnown

                vb.layoutFamily.tvTitle.text = getString(R.string.app_plant_family)
                vb.layoutFamily.tvContent.text = plant.family

                vb.layoutGenus.tvTitle.text = getString(R.string.app_plant_genus)
                vb.layoutGenus.tvContent.text = plant.genus

                vb.layoutBrief.tvTitle.text = getString(R.string.app_plant_brief)
                vb.layoutBrief.tvContent.text = plant.brief

                vb.layoutFeature.tvTitle.text = getString(R.string.app_plant_feature)
                vb.layoutFeature.tvContent.text = plant.feature

                vb.layoutFunction.tvTitle.text = getString(R.string.app_plant_function)
                vb.layoutFunction.tvContent.text = plant.function

                vb.layoutLocation.tvTitle.text = getString(R.string.app_plant_location)
                vb.layoutLocation.tvContent.text = plant.location

                vb.tvUpdateDate.text = getString(R.string.app_plant_update_date, plant.updateDate)
            }
        })
    }
}