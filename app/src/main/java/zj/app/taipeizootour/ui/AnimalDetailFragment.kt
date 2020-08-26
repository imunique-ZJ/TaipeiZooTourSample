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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_plant_detail.*
import zj.app.taipeizootour.R
import zj.app.taipeizootour.adapter.PictureAdapter
import zj.app.taipeizootour.databinding.FragmentAnimalDetailBinding
import zj.app.taipeizootour.viewmodel.MainActivityViewModel

@AndroidEntryPoint
class AnimalDetailFragment : Fragment() {

    private var _vb: FragmentAnimalDetailBinding? = null
    private val vb get() = _vb!!

    private val vm: MainActivityViewModel by activityViewModels()
    private val picsAdapter = PictureAdapter()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _vb = FragmentAnimalDetailBinding.inflate(layoutInflater, container, false)
        prepareSharedElementTransition()
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        setupViewPager()
        bindLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        picsAdapter.unregisterAdapterDataObserver(pagerIndicator.adapterDataObserver)
        _vb = null
    }

    private fun setupAppBar() {
        (activity as? AppCompatActivity)?.run {
            setSupportActionBar(vb.toolbar)
            vb.toolbar.title = ""
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
                    vb.vpAnimalPics.transitionName = names[0]
                    sharedElements[names[0]] = vb.vpAnimalPics
                }
            })
    }

    private fun setupViewPager() {
        vb.vpAnimalPics.adapter = picsAdapter
        vb.pagerIndicator.setViewPager(vb.vpAnimalPics)
        picsAdapter.registerAdapterDataObserver(pagerIndicator.adapterDataObserver)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun bindLiveData() {
        vm.selectedAnimalLiveData.observe(viewLifecycleOwner, { zooAnimal ->
            zooAnimal?.let { animal ->
                val pics = buildList {
                    animal.pic01Url.takeIf { it.isNotEmpty() }?.let { add(it) }
                    animal.pic02Url.takeIf { it.isNotEmpty() }?.let { add(it) }
                    animal.pic03Url.takeIf { it.isNotEmpty() }?.let { add(it) }
                    animal.pic04Url.takeIf { it.isNotEmpty() }?.let { add(it) }
                    // if no picture urls, add an empty path for displaying placeholder
                    if (isEmpty()) {
                        add("")
                    }
                }
                picsAdapter.submitList(pics)

                (view?.parent as? ViewGroup)?.doOnPreDraw {
                    startPostponedEnterTransition()
                }

                vb.layoutTitle.tvTitle.text = animal.chName
                vb.layoutTitle.tvContent.text = animal.enName

                vb.layoutAlias.tvTitle.text = getString(R.string.app_plant_alias)
                vb.layoutAlias.tvContent.text = animal.alsoKnown

                vb.layoutPhylum.tvTitle.text = getString(R.string.app_animal_phylum)
                vb.layoutPhylum.tvContent.text = animal.phylum

                vb.layoutClazz.tvTitle.text = getString(R.string.app_animal_clazz)
                vb.layoutClazz.tvContent.text = animal.clazz

                vb.layoutOrder.tvTitle.text = getString(R.string.app_animal_order)
                vb.layoutOrder.tvContent.text = animal.order

                vb.layoutFamily.tvTitle.text = getString(R.string.app_plant_family)
                vb.layoutFamily.tvContent.text = animal.family

                vb.layoutInterpretation.tvTitle.text = getString(R.string.app_animal_interpretation)
                vb.layoutInterpretation.tvContent.text = animal.interpretation

                vb.layoutBehavior.tvTitle.text = getString(R.string.app_animal_behavior)
                vb.layoutBehavior.tvContent.text = animal.behavior

                vb.layoutHabitat.tvTitle.text = getString(R.string.app_animal_habitat)
                vb.layoutHabitat.tvContent.text = animal.habitat

                vb.layoutDiet.tvTitle.text = getString(R.string.app_animal_diet)
                vb.layoutDiet.tvContent.text = animal.diet

                vb.layoutFeature.tvTitle.text = getString(R.string.app_animal_feature)
                vb.layoutFeature.tvContent.text = animal.feature

                vb.layoutCrisis.tvTitle.text = getString(R.string.app_animal_crisis)
                vb.layoutCrisis.tvContent.text = animal.crisis

                vb.layoutConservation.tvTitle.text = getString(R.string.app_animal_conservation)
                vb.layoutConservation.tvContent.text = animal.conservation

                vb.layoutDistribution.tvTitle.text = getString(R.string.app_animal_distribution)
                vb.layoutDistribution.tvContent.text = animal.distribution

                vb.layoutLocation.tvTitle.text = getString(R.string.app_plant_location)
                vb.layoutLocation.tvContent.text = animal.location

                vb.tvUpdateDate.text = getString(R.string.app_plant_update_date, animal.updateDate)
            }
        })
    }
}