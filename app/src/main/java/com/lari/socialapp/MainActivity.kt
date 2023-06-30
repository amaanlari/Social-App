package com.lari.socialapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.lari.socialapp.daos.PostDao
import com.lari.socialapp.databinding.ActivityMainBinding
import com.lari.socialapp.models.Post

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // [START view_binding_configuration]
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        // [END view_binding_configuration]
        setContentView(view)

        binding.fab.setOnClickListener {
            val createPostActivityintent = Intent(this, CreatePostActivity::class.java)
            startActivity(createPostActivityintent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollection
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}