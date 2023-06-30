package com.lari.socialapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lari.socialapp.databinding.ItemPostBinding
import com.lari.socialapp.models.Post

class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: IPostAdapter) :
    FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(options) {

    inner class PostViewHolder(val binding: ItemPostBinding):
        ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val viewHolder = PostViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.binding.likeButton.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        Glide.with(holder.binding.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.binding.userImage)
        holder.binding.userName.text = model.text
        holder.binding.createdAt.text = Utils.getTimeAgo(model.createdAt)
        holder.binding.postTitle.text = model.text
        holder.binding.likeCount.text = model.likedBy.size.toString()

        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked) {
            holder.binding.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.binding.likeButton.context, R.drawable.ic_liked))
        } else {
            holder.binding.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.binding.likeButton.context, R.drawable.ic_unliked))
        }
    }

}

interface IPostAdapter {
    fun onLikeClicked(postId: String)
}