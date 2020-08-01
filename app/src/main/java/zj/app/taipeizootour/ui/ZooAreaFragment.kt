package zj.app.taipeizootour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import zj.app.taipeizootour.databinding.FragmentZooAreaBinding

class ZooAreaFragment: Fragment() {

    private var _vb: FragmentZooAreaBinding? = null
    private val vb get() = _vb!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _vb = FragmentZooAreaBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}