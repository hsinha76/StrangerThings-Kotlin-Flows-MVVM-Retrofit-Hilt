package com.hsdroid.strangerthings.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hsdroid.strangerthings.databinding.ItemsBinding
import com.hsdroid.strangerthings.network.model.Quotes

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private var list: List<Quotes>? = null
    private lateinit var binding: ItemsBinding

    inner class MyViewHolder(val binding: ItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerAdapter.MyViewHolder {
        binding = ItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {
        with(holder) {
            with(list!![position]) {
                binding.tvQuotes.text = quote
                binding.tvAuthor.text = author
            }
        }
    }

    override fun getItemCount(): Int {
        if(list == null) return 0
        return list!!.size
    }

    fun setList(list: List<Quotes>) {
        this.list = list
        notifyDataSetChanged()
    }
}