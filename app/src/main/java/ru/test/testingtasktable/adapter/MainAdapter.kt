package ru.test.testingtasktable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.test.testingtasktable.databinding.CardviewInputBinding

class MainAdapter : RecyclerView.Adapter<ItemHolder>() {

    private var listOfItems = arrayListOf<ItemData>()
    var listener: OnInputClickListener? = null

    interface OnInputClickListener {
        fun onClick(item: ItemData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CardviewInputBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(listOfItems[position])
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    fun setData(list: ArrayList<ItemData>) {
        listOfItems.clear()
        notifyItemRangeRemoved(0, list.size)
        listOfItems.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}