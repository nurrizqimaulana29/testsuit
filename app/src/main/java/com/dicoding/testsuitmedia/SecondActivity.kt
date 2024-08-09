package com.dicoding.testsuitmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.testsuitmedia.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedText = intent.getStringExtra("input_text")

        binding.tvName.text = receivedText

        val userName = intent.getStringExtra("USER_NAME") ?: "Selected User Name"

        val userNameTextView = binding.selectedUserTextView
        userNameTextView.text = userName


        binding.chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}