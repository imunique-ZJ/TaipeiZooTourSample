package zj.app.taipeizootour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import zj.app.taipeizootour.R
import zj.app.taipeizootour.databinding.LayoutZooRecyclerviewItemBinding
import zj.app.taipeizootour.db.model.ZooArea

class ZooAreaAdapter(private val onClick: OnAreaClick): ListAdapter<ZooArea, ZooAreaAdapter.ZooAreaViewHolder>(diffCallback) {

    interface OnAreaClick {
        fun onClick(vb: LayoutZooRecyclerviewItemBinding, area: ZooArea)
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<ZooArea>() {
            override fun areItemsTheSame(oldItem: ZooArea, newItem: ZooArea): Boolean {
                return oldItem.areaId == newItem.areaId
            }

            override fun areContentsTheSame(oldItem: ZooArea, newItem: ZooArea): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZooAreaViewHolder {
        val vb = LayoutZooRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZooAreaViewHolder(vb, onClick)
    }

    override fun onBindViewHolder(holder: ZooAreaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ZooAreaViewHolder(private val vb: LayoutZooRecyclerviewItemBinding,
                                  private val onClick: OnAreaClick): RecyclerView.ViewHolder(vb.root) {

        fun bind(zooArea: ZooArea) {
            ViewCompat.setTransitionName(vb.root, zooArea.name)
            vb.ivPic.load(zooArea.picUrl) {
                placeholder(R.drawable.ic_baseline_pets_24)
                error(R.drawable.ic_baseline_pets_24)
                scale(Scale.FILL)
                allowHardware(false)
            }
            vb.tvTitle.text = zooArea.name
            vb.tvDesc.text = zooArea.info
            vb.tvMemo.text = zooArea.memo

            vb.root.setOnClickListener {
                onClick.onClick(vb, zooArea)
            }
        }
    }
}