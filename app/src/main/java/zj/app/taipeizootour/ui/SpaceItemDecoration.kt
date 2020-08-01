package zj.app.taipeizootour.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val divideSize: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.left = divideSize
        outRect.top = divideSize
        outRect.right = divideSize
        outRect.bottom = divideSize
    }
}