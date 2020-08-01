package zj.app.taipeizootour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zj.app.taipeizootour.R
import zj.app.taipeizootour.adapter.ZooAreaAdapter
import zj.app.taipeizootour.databinding.FragmentZooAreaBinding
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.state.ZooAreaState
import zj.app.taipeizootour.viewmodel.MainActivityViewModel

class ZooAreaFragment: Fragment() {

    private var _vb: FragmentZooAreaBinding? = null
    private val vb get() = _vb!!

    private val vm: MainActivityViewModel by activityViewModels()
    private val zooAreaAdapter by lazy {
        ZooAreaAdapter(object: ZooAreaAdapter.OnAreaClick {
            override fun onClick(area: ZooArea) {}
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _vb = FragmentZooAreaBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        bindLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    private fun setupSwipeRefreshLayout() {
        vb.srlZooArea.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        vb.srlZooArea.setOnRefreshListener {
            vm.fetchData(getString(R.string.query_meta_area_intro), getString(R.string.query_meta_plants))
        }
    }


    private fun setupRecyclerView() {
        vb.rvZooAreas.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vb.rvZooAreas.adapter = zooAreaAdapter
    }

    private fun bindLiveData() {
        vm.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ZooAreaState.Loading -> vb.srlZooArea.isRefreshing = true
                is ZooAreaState.Finish -> vb.srlZooArea.isRefreshing = false
            }
        })

        vm.areaLiveData.observe(viewLifecycleOwner, Observer { areas ->
            zooAreaAdapter.submitList(areas)
        })
    }
}