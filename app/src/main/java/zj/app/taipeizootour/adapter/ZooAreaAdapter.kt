package zj.app.taipeizootour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zj.app.taipeizootour.databinding.ItemZooAreaBinding
import zj.app.taipeizootour.db.model.ZooArea

class ZooAreaAdapter(private val onClick: OnAreaClick): ListAdapter<ZooArea, ZooAreaAdapter.ZooAreaViewHolder>(diffCallback) {

    interface OnAreaClick {
        fun onClick(area: ZooArea)
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
        val vb = ItemZooAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZooAreaViewHolder(vb, onClick)
    }

    override fun onBindViewHolder(holder: ZooAreaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ZooAreaViewHolder(private val vb: ItemZooAreaBinding,
                                  private val onClick: OnAreaClick): RecyclerView.ViewHolder(vb.root) {

        fun bind(zooArea: ZooArea) {
            vb.tvAreaName.text = zooArea.name
            vb.tvAreaDesc.text = zooArea.info
            vb.tvAreaMemo.text = zooArea.memo

            vb.root.setOnClickListener {
                onClick.onClick(zooArea)
            }
        }
    }
}