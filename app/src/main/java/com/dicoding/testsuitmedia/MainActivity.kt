package com.dicoding.testsuitmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.testsuitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {
            val sentence = binding.edPolindrome.text.toString()
            val isPalindrome = isPalindrome(sentence)
            val message = if (isPalindrome) "Palindrome" else "Not Palindrome"
            showMessage(message)
        }

        binding.btnNext.setOnClickListener {
            val inputText = binding.edName.text.toString().trim()

            if (inputText.isEmpty()) {
                // Tampilkan pesan kesalahan jika input kosong
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                // Lanjutkan jika input tidak kosong
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("input_text", inputText)
                }
                startActivity(intent)
            }
        }
    }

    private fun isPalindrome(sentence: String): Boolean {
        val cleanedSentence = sentence.replace("\\s".toRegex(), "").lowercase()
        return cleanedSentence == cleanedSentence.reversed()
    }

    private fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}