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
import zj.app.taipeizootour.db.model.ZooAnimal

class ZooAnimalsAdapter(
    private val onClick: OnAnimalClicked
): ListAdapter<ZooAnimal, ZooAnimalsAdapter.ZooAnimalViewHolder>(diffCallback) {

    fun interface OnAnimalClicked {
        fun onClick(vb: LayoutZooRecyclerviewItemBinding, animal: ZooAnimal)
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<ZooAnimal>() {
            override fun areItemsTheSame(oldItem: ZooAnimal, newItem: ZooAnimal): Boolean {
                return oldItem.animalId == newItem.animalId
            }

            override fun areContentsTheSame(oldItem: ZooAnimal, newItem: ZooAnimal): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZooAnimalViewHolder {
        val vb = LayoutZooRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZooAnimalViewHolder(vb, onClick)
    }

    override fun onBindViewHolder(holder: ZooAnimalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ZooAnimalViewHolder(private val vb: LayoutZooRecyclerviewItemBinding,
                                    private val onClick: OnAnimalClicked): RecyclerView.ViewHolder(vb.root) {

        fun bind(animal: ZooAnimal) {
            vb.ivPic.load(animal.pic01Url) {
                placeholder(R.drawable.ic_baseline_pets_24)
                error(R.drawable.ic_baseline_pets_24)
                scale(Scale.FILL)
                allowHardware(false)
            }
            ViewCompat.setTransitionName(vb.ivPic, animal.chName)
            vb.tvTitle.text = animal.chName
            vb.tvDesc.text = animal.interpretation.takeIf { it.isNotEmpty() } ?: animal.behavior

            vb.root.setOnClickListener {
                onClick.onClick(vb, animal)
            }
        }
    }
}