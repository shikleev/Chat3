package com.example.chat.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.presentation.ui.adapter.ChatAdapter
import com.example.chat.utils.observeViewState

class MainFragment : Fragment(R.layout.main_fragment) {

    private var rvChat: RecyclerView? = null
    private var chatAdapter: ChatAdapter? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvChat = view.findViewById<RecyclerView>(R.id.rv_chat)?.also {
            chatAdapter = ChatAdapter(requireContext())
            it.adapter = chatAdapter
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        }
        observeUser()
        observeChat()
    }

    private fun observeUser() {
        viewModel.viewState.observeViewState(viewLifecycleOwner) {
            onProgress {
                // TODO: 09.11.2021
            }
            onSuccess { user ->
                // TODO: 09.11.2021
            }
            onError { isShowError, error ->
                // TODO: 09.11.2021
            }
        }
    }

    private fun observeChat() {
        lifecycleScope.launchWhenStarted {
            viewModel.chat.observe(viewLifecycleOwner) {
                chatAdapter?.update(it)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}