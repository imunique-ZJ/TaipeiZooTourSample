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
import zj.app.taipeizootour.db.model.ZooPlant

class ZooPlantsAdapter(private val onClick: OnPlantClick): ListAdapter<ZooPlant, ZooPlantsAdapter.ZooPlantViewHolder>(diffCallback) {

    interface OnPlantClick {
        fun onClick(vb: LayoutZooRecyclerviewItemBinding, plant: ZooPlant)
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<ZooPlant>() {
            override fun areItemsTheSame(oldItem: ZooPlant, newItem: ZooPlant): Boolean {
                return oldItem.plantId == newItem.plantId
            }

            override fun areContentsTheSame(oldItem: ZooPlant, newItem: ZooPlant): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZooPlantViewHolder {
        val vb = LayoutZooRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZooPlantViewHolder(vb, onClick)
    }

    override fun onBindViewHolder(holder: ZooPlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ZooPlantViewHolder(private val vb: LayoutZooRecyclerviewItemBinding,
                                   private val onClick: OnPlantClick): RecyclerView.ViewHolder(vb.root) {

        fun bind(plant: ZooPlant) {
            vb.ivPic.load(plant.pic01Url) {
                placeholder(R.drawable.ic_baseline_pets_24)
                scale(Scale.FILL)
                allowHardware(false)
            }
            ViewCompat.setTransitionName(vb.ivPic, plant.chName)
            ViewCompat.setTransitionName(vb.root, "${plant.plantId}_${plant.chName}")
            vb.tvTitle.text = plant.chName
            vb.tvDesc.text = plant.brief

            vb.root.setOnClickListener {
                onClick.onClick(vb, plant)
            }
        }
    }
}