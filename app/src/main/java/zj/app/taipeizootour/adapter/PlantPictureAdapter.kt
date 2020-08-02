package zj.app.taipeizootour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import zj.app.taipeizootour.databinding.LayoutPlantPicRecyclerviewItemBinding

class PlantPictureAdapter: ListAdapter<String, PlantPictureAdapter.PlantPictureViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantPictureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vb = LayoutPlantPicRecyclerviewItemBinding.inflate(inflater, parent, false)
        return PlantPictureViewHolder(vb)
    }

    override fun onBindViewHolder(holder: PlantPictureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlantPictureViewHolder(
        private val vb: LayoutPlantPicRecyclerviewItemBinding
    ): RecyclerView.ViewHolder(vb.root) {

        fun bind(picUrl: String) {
            vb.ivPic.load(picUrl) {
                allowHardware(false)
            }
        }
    }
}