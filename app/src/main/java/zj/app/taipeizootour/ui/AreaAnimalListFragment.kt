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
import zj.app.taipeizootour.adapter.ZooAnimalsAdapter
import zj.app.taipeizootour.databinding.FragmentAreaDataListBinding
import zj.app.taipeizootour.databinding.LayoutZooRecyclerviewItemBinding
import zj.app.taipeizootour.db.model.ZooAnimal
import zj.app.taipeizootour.ext.dpToPx
import zj.app.taipeizootour.viewmodel.AreaAnimalListFragmentViewModel

@AndroidEntryPoint
class AreaAnimalListFragment: Fragment() {

    interface OnAnimalSelected {
        fun onAnimalSelected(itemVb: LayoutZooRecyclerviewItemBinding, animal: ZooAnimal)
    }

    private var _vb: FragmentAreaDataListBinding? = null
    private val vb get() = _vb!!
    private val vm: AreaAnimalListFragmentViewModel by viewModels()
    private var onAnimalSelected: OnAnimalSelected? = null

    private val animalsAdapter by lazy {
        ZooAnimalsAdapter { vb, animal ->
            onAnimalSelected?.onAnimalSelected(vb, animal)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAnimalSelected) {
            onAnimalSelected = context
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
        vb.rvPlants.adapter = animalsAdapter
        vb.rvPlants.addItemDecoration(SpaceItemDecoration(4.dpToPx()))
    }

    private fun bindLiveData() {
        vm.dataListLiveData.observe(viewLifecycleOwner, { data ->
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
            animalsAdapter.submitList(data?.animals)
        })
    }
}