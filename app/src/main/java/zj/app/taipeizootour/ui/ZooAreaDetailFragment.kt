package zj.app.taipeizootour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import zj.app.taipeizootour.R
import zj.app.taipeizootour.adapter.ZooPlantsAdapter
import zj.app.taipeizootour.databinding.FragmentZooAreaDetailBinding
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.ext.dpToPx
import zj.app.taipeizootour.viewmodel.DetailActivityViewModel


class ZooAreaDetailFragment: Fragment() {

    private var _vb: FragmentZooAreaDetailBinding? = null
    private val vb get() = _vb!!

    private val vm: DetailActivityViewModel by activityViewModels()
    private val plantsAdapter by lazy {
        ZooPlantsAdapter(object: ZooPlantsAdapter.OnPlantClick {
            override fun onClick(plant: ZooPlant) {}
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _vb = FragmentZooAreaDetailBinding.inflate(inflater, container, false)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> childFragmentManager.popBackStackImmediate()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAppBar() {
        (activity as? AppCompatActivity)?.run {
            setSupportActionBar(vb.toolbar)
            supportActionBar?.title = ""
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        vb.rvPlants.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vb.rvPlants.adapter = plantsAdapter
        vb.rvPlants.addItemDecoration(SpaceItemDecoration(4.dpToPx()))
    }

    private fun bindLiveData() {
        vm.areaWithPlantsLiveData.observe(viewLifecycleOwner, Observer { areaWithPlants ->
            vb.collapsingTbLayout.title = areaWithPlants?.area?.name
            vb.ivPic.load(areaWithPlants?.area?.picUrl) {
                placeholder(R.drawable.ic_baseline_pets_24)
                scale(Scale.FILL)
            }
            vb.tvDesc.text = areaWithPlants?.area?.info
            plantsAdapter.submitList(areaWithPlants?.plants)
        })
    }
}