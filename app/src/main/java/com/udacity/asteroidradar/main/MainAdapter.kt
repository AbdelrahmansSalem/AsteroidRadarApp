package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class MainAdapter(val onclickListener:onClickListener) :ListAdapter<Asteroid,MainAdapter.AsteroidViewHolder>(DiffCallBack){
    class AsteroidViewHolder(private var binding: AsteroidItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid){
            binding.asteroid=asteroid
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        var item=getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onclickListener.onClick(item)
        }
    }

    class onClickListener(val clickListener: (asteroid:Asteroid)->Unit){
        fun onClick(asteroid: Asteroid)=clickListener(asteroid)
    }

}

object DiffCallBack:DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id==newItem.id
    }


}
