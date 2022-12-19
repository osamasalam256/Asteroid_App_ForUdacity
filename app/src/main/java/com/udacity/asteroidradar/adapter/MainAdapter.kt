package com.udacity.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemListBinding
import com.udacity.asteroidradar.domain.AsteroDomain


class MainAdapter(private val onClickListener: OnClickListener) : ListAdapter<AsteroDomain,
        MainAdapter.AsteroidViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.fromViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroProperty = getItem(position)!!
        holder.bind(asteroProperty, onClickListener)
    }

    class AsteroidViewHolder(private var binding: AsteroidItemListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun fromViewHolder(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemListBinding.inflate(layoutInflater,parent,false)
                return AsteroidViewHolder(binding)
            }
        }

        fun bind(asteroProperty: AsteroDomain, onClickListener: OnClickListener) {
            binding.asteroid = asteroProperty
            binding.root.setOnClickListener { onClickListener.onClick(asteroProperty) }
            binding.executePendingBindings()
        }

    }


    companion object DiffCallback : DiffUtil.ItemCallback<AsteroDomain>() {
        override fun areItemsTheSame(oldItem: AsteroDomain, newItem: AsteroDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroDomain, newItem: AsteroDomain): Boolean {
            return oldItem == newItem
        }
    }
    class OnClickListener(val clickListener: (asteroProperty: AsteroDomain) -> Unit) {
        fun onClick(asteroProperty: AsteroDomain) = clickListener(asteroProperty)
    }
}
