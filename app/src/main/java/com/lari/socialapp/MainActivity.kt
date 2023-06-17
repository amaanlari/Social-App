package com.lari.socialapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lari.socialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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
    }
}