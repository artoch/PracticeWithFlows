package com.testbozz.testbozz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testbozz.testbozz.R
import com.testbozz.testbozz.aux_interface.AdapterOnClick
import com.testbozz.testbozz.aux_interface.CUD
import com.testbozz.testbozz.data.models.PostItemEntity
import com.testbozz.testbozz.databinding.ItemPostBinding

class PostAdapter (private val items: MutableList<PostItemEntity>,
                    private val adapterOnClick: AdapterOnClick<PostItemEntity>,
                    private val saveDeleteOnClick: CUD<PostItemEntity>,
                    private var type : Int
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    fun setData(post: List<PostItemEntity>){
        items.clear()
        items.addAll(post)
        this.notifyDataSetChanged()
    }

    fun setType(type:Int){
        this.type = type
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = ViewHolder(
        ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], adapterOnClick, saveDeleteOnClick, type)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val itemBinding: ItemPostBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(post: PostItemEntity , adapterOnClick: AdapterOnClick<PostItemEntity>, saveOnClick: CUD<PostItemEntity>, type:Int) {
            itemBinding.apply {
                tvTitle.text = post.title
                tvBody.text = post.body
                cvItem.setOnClickListener { adapterOnClick.adapterOnClick(post) }
                when (type){
                    CUD.SAVE    -> btnSave.setImageResource(R.drawable.ic_heart)
                    CUD.DELETE  -> btnSave.setImageResource(R.drawable.ic_delete)
                }
                btnSave.setOnClickListener { saveOnClick.onCUD(post, type) }
            }
        }
    }
}
