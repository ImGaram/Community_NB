package com.example.presentation.view.fragment

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.freeboard.getpostall.DomainGetAllFreeBoardResponse
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentFreeBoardBinding
import com.example.presentation.view.freeboard.AddFreeBoardActivity
import com.example.presentation.view.freeboard.adapter.FreeBoardPostAdapter
import com.example.presentation.view.freeboard.adapter.SpacesItemDecoration
import com.example.presentation.view.freeboard.info.PostInfoActivity
import com.example.presentation.view.login.LoginActivity
import com.example.presentation.viewmodel.NbViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FreeBoardFragment : BaseFragment<FragmentFreeBoardBinding>(R.layout.fragment_free_board), View.OnClickListener {
    private val nbViewModel by activityViewModels<NbViewModel>()

    override fun init() {
        binding.free = this
        viewSetting()
        getUser()
        getPost()

        binding.addPost.bringToFront()
        binding.sangText.bringToFront()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.goLoginText.id -> startActivity(Intent(context, LoginActivity::class.java))
            binding.addPost.id -> {
                val id = arguments?.getInt("id")
                val intent = Intent(context, AddFreeBoardActivity::class.java)
                    .putExtra("createUser", id)
                startActivity(intent)
            }
        }
    }

    private fun getPost() {
        nbViewModel.getPostLogic()
        nbViewModel.getPostAllApiCallResult.observe(viewLifecycleOwner) {
            val adapter = FreeBoardPostAdapter(it, requireContext(), resources)
            binding.freeBoardRecyclerView.setHasFixedSize(true)
            binding.freeBoardRecyclerView.adapter = adapter
            binding.freeBoardRecyclerView.layoutManager = GridLayoutManager(activity, 3)
            binding.freeBoardRecyclerView.addItemDecoration(SpacesItemDecoration(10))
            itemClick(it ,adapter)
        }
    }

    private fun getUser() {
        val id = arguments?.getInt("id")
        if (id != null) {
            nbViewModel.getUserLogic(id)
            nbViewModel.userInfoApiCallResult.observe(viewLifecycleOwner) {
                if (it != null) {   // 로그인한 유저가 있음
                    binding.goLoginText.visibility = View.GONE
                } else {
                    Log.d("SUCCESS", "guest user")
                }
            }
        } else {
            Log.d("FAIL", "id: $id")
        }
    }

    private fun itemClick(item: List<DomainGetAllFreeBoardResponse>, adapter: FreeBoardPostAdapter) {
        adapter.itemClick = object : FreeBoardPostAdapter.ItemClick {
            override fun onClick(view: View, data: DomainGetAllFreeBoardResponse, position: Int) {
                val post = item[position]

                val intent = Intent(context, PostInfoActivity::class.java)
                    .putExtra("title", post._title)
                    .putExtra("content", post._context)
                    .putExtra("id", post._id)   // 아직 안씀
                    .putExtra("createUser", post._createUser)
                    .putExtra("createDate", post._createDate)
                    .putExtra("correctionDate", post._correctionDate)
                    .putExtra("userIdIdx", arguments?.getInt("id"))
                startActivity(intent)
            }
        }
    }

    private fun viewSetting() {
        binding.apply {
            goLoginText.setOnClickListener(this@FreeBoardFragment)
            addPost.setOnClickListener(this@FreeBoardFragment)

            if (arguments?.getInt("id") == 0) {
                binding.addPost.visibility = View.GONE
            } else {
                binding.addPost.visibility = View.VISIBLE
            }
        }
    }

}
