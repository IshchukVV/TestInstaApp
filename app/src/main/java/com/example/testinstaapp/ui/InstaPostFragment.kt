package com.example.testinstaapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testinstaapp.R
import kotlinx.android.synthetic.main.post_fragment.*
import kotlinx.coroutines.flow.collectLatest

class InstaPostFragment : Fragment() {
    private lateinit var viewModel: InstaPostViewModel
    private lateinit var recyclerViewAdapter: InstaPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[InstaPostViewModel::class.java]
        lifecycleScope.launchWhenCreated {
            viewModel.getPosts().collectLatest{
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        recycler_view.apply{
            layoutManager= LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            recyclerViewAdapter = InstaPostAdapter()
            adapter = recyclerViewAdapter
        }
    }
}