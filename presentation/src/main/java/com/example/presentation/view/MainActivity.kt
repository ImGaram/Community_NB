package com.example.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.view.fragment.FreeBoardFragment
import com.example.presentation.view.fragment.InquiryFragment
import com.example.presentation.view.fragment.NoticeFragment
import com.example.presentation.view.fragment.StoryBoardFragment
import com.example.presentation.view.user.UserInfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, FreeBoardFragment().apply {
            arguments = Bundle().apply {
                Log.d("SUCCESS", "onNavigationItemSelected intent extra: ${intent.getIntExtra("userId", 0)}")
                putInt("id", intent.getIntExtra("userId", 0))
            }
        }).commit()
        setView()

        binding.userNameText.setOnClickListener {
            val name = intent.getStringExtra("userName")
            val email = intent.getStringExtra("email")
            val password = intent.getStringExtra("password")

            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, UserInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", intent.getIntExtra("userId", 0))
                    putString("name", name)
                    putString("email", email)
                    putString("password", password)
                }
            }).commit()
        }
    }

    private fun setView() {
        val username = intent.getStringExtra("userName")
        Log.d("SUCCESS", "setView user name: $username")

        if (username != null) {
            val dataId = intent.getIntExtra("userId", 0)
            if (dataId != 0) {
                binding.userNameText.text = username
            } else {
                binding.userNameText.text = username
            }

        } else {
            binding.userNameText.text = "Guest"
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.freeBoard -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FreeBoardFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", intent.getIntExtra("userId", 0))
                        putString("username", intent.getStringExtra("userName"))
                    }
                }).commitAllowingStateLoss()
                return true
            }
            R.id.inquiry -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, InquiryFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", intent.getIntExtra("userId", 0))
                    }
                }).commitAllowingStateLoss()
                return true
            }
            R.id.notice -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, NoticeFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", intent.getIntExtra("userId", 0))
                    }
                }).commitAllowingStateLoss()
                return true
            }
            R.id.storyBoard -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, StoryBoardFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", intent.getIntExtra("userId", 0))
                    }
                }).commitAllowingStateLoss()
                return true
            }
        }
        return false
    }
}