package com.lari.socialapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lari.socialapp.daos.PostDao
import com.lari.socialapp.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // [START view_binding_configuration]
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        val view = binding.root
        // [END view_binding_configuration]
        setContentView(view)

        val postDao = PostDao()

        binding.button.setOnClickListener{
            val input = binding.postInputTextfield.text.toString().trim()
            if (input.isNotBlank() && input.isNotEmpty()) {
                postDao.addPost(input)
                finish()
            }
        }
    }
}