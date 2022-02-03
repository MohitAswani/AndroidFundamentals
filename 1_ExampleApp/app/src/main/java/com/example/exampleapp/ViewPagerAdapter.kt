package com.example.exampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exampleapp.databinding.ItemPageViewBinding

class ViewPagerAdapter(val images:List<Int>) : RecyclerView.Adapter<ViewPagerAdapter.ImageViewHolder>(){

    inner class ImageViewHolder(val binding: ItemPageViewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun setImage(image:Int){
            binding.image.setImageResource(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemPageViewBinding=ItemPageViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(itemPageViewBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.setImage(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}