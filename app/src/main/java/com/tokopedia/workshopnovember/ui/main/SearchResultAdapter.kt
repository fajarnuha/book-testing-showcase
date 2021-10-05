package com.tokopedia.workshopnovember.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.pojo.search.SearchResultListUi

class SearchResultAdapter: ListAdapter<SearchResultListUi, SearchResultAdapter.SearchResultViewHolder>(object : DiffUtil.ItemCallback<SearchResultListUi>() {
    override fun areItemsTheSame(
        oldItem: SearchResultListUi,
        newItem: SearchResultListUi
    ): Boolean {
        return oldItem.isbn == newItem.isbn
    }

    override fun areContentsTheSame(
        oldItem: SearchResultListUi,
        newItem: SearchResultListUi
    ): Boolean {
        return oldItem == newItem
    }
}){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: SearchResultListUi) {
            itemView.findViewById<TextView>(R.id.tv_title).text = item.title
            itemView.findViewById<TextView>(R.id.tv_author).text = item.author
        }

    }
}