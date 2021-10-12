package com.tokopedia.workshopnovember.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.pojo.search.BookUiModel

class SearchResultAdapter(private var listener: ((String?) -> Unit)? = null) :
    ListAdapter<BookUiModel, SearchResultAdapter.SearchResultViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        view.setOnClickListener {

        }
        return SearchResultViewHolder(view).apply {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition].isbn)
            }
        }
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BookUiModel) {
            itemView.findViewById<TextView>(R.id.tv_title).text = item.title
            itemView.findViewById<TextView>(R.id.tv_author).text = item.author

            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
            Glide.with(itemView.context)
                .load(item.src)
                .placeholder(R.drawable.book_cover_placeholder)
                .centerCrop()
                .into(imageView)
        }

    }

    class DiffCallBack : DiffUtil.ItemCallback<BookUiModel>() {
        override fun areItemsTheSame(
            oldItem: BookUiModel,
            newItem: BookUiModel
        ): Boolean {
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(
            oldItem: BookUiModel,
            newItem: BookUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}

