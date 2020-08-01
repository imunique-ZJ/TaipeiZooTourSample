package zj.app.taipeizootour.ext

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zj.app.taipeizootour.viewmodel.ViewModelProviderFactory

inline fun <reified T: ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, ViewModelProviderFactory(creator)).get(T::class.java)
    }
}
