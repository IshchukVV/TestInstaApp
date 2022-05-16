package com.example.testinstaapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.LazyHeaders

import com.bumptech.glide.load.model.GlideUrl
import com.example.testinstaapp.data.model.InstaPost
import com.example.testinstaapp.R


class InstaPostAdapter :
    PagingDataAdapter<InstaPost, InstaPostAdapter.ItemViewHolder>(MovieDifUtilCallback) {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postTextView: TextView = itemView.findViewById(R.id.post_text)
        private val postIdTextView: TextView = itemView.findViewById(R.id._id)
        private val postCoverImageView: ImageView = itemView.findViewById(R.id.post_cover)
        private var currentPost: InstaPost? = null

        fun bind(item: InstaPost?) {
            currentPost = item
            postTextView.text = item?.title
            postIdTextView.text = item?.id.toString()
            Glide.with(postCoverImageView)
                .asBitmap()
                .load(
                    GlideUrl(
                        item?.thumbnailUrl, LazyHeaders.Builder()
                            .addHeader
                                (
                                "User-Agent", "AndroidVersion is " +
                                    android.os.Build.VERSION.SDK_INT.toString() +
                                    ". AndroidModel is " +
                                    android.os.Build.MODEL.toString()
                            )
                            .build()
                    )
                )
                .into(postCoverImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        itemViewHolder.bind(getItem(position))
    }

    object MovieDifUtilCallback : DiffUtil.ItemCallback<InstaPost>() {
        override fun areItemsTheSame(oldItem: InstaPost, newItem: InstaPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InstaPost, newItem: InstaPost): Boolean {
            return oldItem == newItem
        }
    }
}