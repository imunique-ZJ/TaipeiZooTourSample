package zj.app.taipeizootour.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import zj.app.taipeizootour.adapter.ZooPlantsAdapter
import zj.app.taipeizootour.databinding.FragmentAreaDataListBinding
import zj.app.taipeizootour.databinding.LayoutZooRecyclerviewItemBinding
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.ext.dpToPx
import zj.app.taipeizootour.viewmodel.AreaPlantListFragmentViewModel

@AndroidEntryPoint
class AreaPlantListFragment: Fragment() {

    interface OnPlantSelected {
        fun onPlantSelected(itemVb: LayoutZooRecyclerviewItemBinding, plant: ZooPlant)
    }

    private var _vb: FragmentAreaDataListBinding? = null
    private val vb get() = _vb!!
    private val vm: AreaPlantListFragmentViewModel by viewModels()
    private var onPlantSelected: OnPlantSelected? = null

    private val plantsAdapter by lazy {
        ZooPlantsAdapter(object: ZooPlantsAdapter.OnPlantClick {
            override fun onClick(vb: LayoutZooRecyclerviewItemBinding, plant: ZooPlant) {
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
        savedInstanceState: Bundle?
    ): View? {
        _vb = FragmentAreaDataListBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        bindLiveData()
    }

    private fun setupRecyclerView() {
        vb.rvPlants.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vb.rvPlants.adapter = plantsAdapter
        vb.rvPlants.addItemDecoration(SpaceItemDecoration(4.dpToPx()))
    }

    private fun bindLiveData() {
        vm.dataListLiveData.observe(viewLifecycleOwner, { areaWithPlants ->
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
            plantsAdapter.submitList(areaWithPlants?.plants)
        })
    }
}