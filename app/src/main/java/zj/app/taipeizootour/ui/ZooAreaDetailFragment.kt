package zj.app.taipeizootour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.api.load
import coil.size.Scale
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import zj.app.taipeizootour.R
import zj.app.taipeizootour.adapter.AreaDataListPagerAdapter
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.databinding.FragmentZooAreaDetailBinding
import zj.app.taipeizootour.viewmodel.MainActivityViewModel

@AndroidEntryPoint
class ZooAreaDetailFragment: Fragment() {

    private var _vb: FragmentZooAreaDetailBinding? = null
    private val vb get() = _vb!!

    private val vm: MainActivityViewModel by activityViewModels()

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
        arguments?.getInt(Constants.ARG_KEY_AREA_ID)?.let { areaId ->
            setupViewPager(areaId)
        } ?: throw IllegalArgumentException("missing areaId")

        bindLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
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

    private fun setupViewPager(areaId: Int) {
        vb.pager.adapter = AreaDataListPagerAdapter(this, areaId)
        TabLayoutMediator(vb.tabLayout, vb.pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.app_tab_title_plants)
                1 -> getString(R.string.app_tab_title_animals)
                else -> ""
            }
        }.attach()
    }

    private fun bindLiveData() {
        vm.selectedAreaLiveData.observe(viewLifecycleOwner, { area ->
            vb.collapsingTbLayout.title = area?.name
            vb.ivPic.load(area?.picUrl) {
                placeholder(R.drawable.ic_baseline_pets_24)
                scale(Scale.FILL)
            }
            vb.tvDesc.text = area?.info

            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        })
    }
}