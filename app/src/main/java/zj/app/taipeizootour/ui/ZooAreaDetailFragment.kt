package zj.app.taipeizootour.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import dagger.hilt.android.AndroidEntryPoint
import zj.app.taipeizootour.R
import zj.app.taipeizootour.adapter.ZooPlantsAdapter
import zj.app.taipeizootour.databinding.FragmentZooAreaDetailBinding
import zj.app.taipeizootour.databinding.LayoutZooRecyclerviewItemBinding
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.ext.dpToPx
import zj.app.taipeizootour.viewmodel.MainActivityViewModel

@AndroidEntryPoint
class ZooAreaDetailFragment: Fragment() {

    interface OnPlantSelected {
        fun onPlantSelected(itemVb: LayoutZooRecyclerviewItemBinding, plant: ZooPlant)
    }

    private var _vb: FragmentZooAreaDetailBinding? = null
    private val vb get() = _vb!!

    private var onPlantSelected: OnPlantSelected? = null
    private val vm: MainActivityViewModel by activityViewModels()
    private val plantsAdapter by lazy {
        ZooPlantsAdapter(object: ZooPlantsAdapter.OnPlantClick {
            override fun onClick(vb: LayoutZooRecyclerviewItemBinding, plant: ZooPlant) {
                vm.selectPlant(plant)
                onPlantSelected?.onPlantSelected(vb, plant)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPlantSelected) {
            onPlantSelected = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _vb = FragmentZooAreaDetailBinding.inflate(inflater, container, false)
        prepareSharedElementTransition()
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        setupRecyclerView()
        bindLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    private fun prepareSharedElementTransition() {
        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String?>,
                    sharedElements: MutableMap<String?, View?>) {
                    vb.appBar.transitionName = names[0]
                    sharedElements[names[0]] = vb.appBar
                }
            })
    }

    private fun setupAppBar() {
        (activity as? AppCompatActivity)?.run {
            setSupportActionBar(vb.toolbar)
            supportActionBar?.title = ""
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        vb.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        vb.rvPlants.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vb.rvPlants.adapter = plantsAdapter
        vb.rvPlants.addItemDecoration(SpaceItemDecoration(4.dpToPx()))
    }

    private fun bindLiveData() {
        vm.areaWithPlantsLiveData.observe(viewLifecycleOwner, { areaWithPlants ->
            vb.collapsingTbLayout.title = areaWithPlants?.area?.name
            vb.ivPic.load(areaWithPlants?.area?.picUrl) {
                placeholder(R.drawable.ic_baseline_pets_24)
                scale(Scale.FILL)
            }
            vb.tvDesc.text = areaWithPlants?.area?.info

            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }

            plantsAdapter.submitList(areaWithPlants?.plants)
        })
    }
}