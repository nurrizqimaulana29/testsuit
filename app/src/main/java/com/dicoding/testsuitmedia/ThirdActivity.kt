package com.dicoding.testsuitmedia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.testsuitmedia.adapter.UserAdapter
import com.dicoding.testsuitmedia.databinding.ActivityThirdBinding
import com.dicoding.testsuitmedia.remote.response.DataItem
import com.dicoding.testsuitmedia.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private var users = mutableListOf<DataItem>()
    private var currentPage = 1
    private var isLoading = false

    private lateinit var binding : ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvUsers
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = UserAdapter(users) { user ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("USER_NAME", "${user.firstName} ${user.lastName}")
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        setupRecyclerView()
        fetchData()

        binding.swipeRefresh.setOnRefreshListener {
            currentPage = 1
            users.clear()
            fetchData()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == users.size - 1) {
                    currentPage++
                    fetchData()
                }
            }
        })
    }

    private fun fetchData() {
        isLoading = true
        binding.progressBar.visibility =View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = ApiConfig.getApiService().getUsers(currentPage, 6)
                if (!response.data.isNullOrEmpty()) {
                    val nonNullUsers = response.data.filterNotNull()
                    users.addAll(nonNullUsers)
                    adapter.notifyDataSetChanged()
                } else if (currentPage == 1) {
                    binding.emptyState.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                isLoading = false
            }
        }
    }

}