package com.testbozz.testbozz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testbozz.testbozz.data.models.CommentsPostEntityItem
import com.testbozz.testbozz.databinding.ItemCommentsBinding

class CommentsAdapter (private val items: MutableList<CommentsPostEntityItem>
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    fun setData(post: List<CommentsPostEntityItem>){
        items.clear()
        items.addAll(post)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = ViewHolder(
        ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val itemBinding: ItemCommentsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(comment: CommentsPostEntityItem) {
            itemBinding.apply {
                tvEmail.text = comment.email
                tvTitle.text = comment.name
                tvBody.text = comment.body
            }
        }
    }
}