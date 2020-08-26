package zj.app.taipeizootour.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import zj.app.taipeizootour.ui.AreaAnimalListFragment
import zj.app.taipeizootour.ui.AreaPlantListFragment

class AreaDataListPagerAdapter(
    fragment: Fragment,
    private val areaId: Int
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AreaPlantListFragment().apply {
                arguments = Bundle().apply {
                    putInt("areaId", areaId)
                }
            }
            else -> AreaAnimalListFragment().apply {
                arguments = Bundle().apply {
                    putInt("areaId", areaId)
                }
            }
        }
    }
}