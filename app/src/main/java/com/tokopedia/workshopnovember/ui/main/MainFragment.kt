package com.tokopedia.workshopnovember.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tokopedia.workshopnovember.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val mAdapter = SearchResultAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val q = view.findViewById<EditText>(R.id.et_search).text.toString()
            viewModel.search(q)
        }

        with(view.findViewById<RecyclerView>(R.id.rv)) {
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }

        viewModel.result.observe(viewLifecycleOwner) { list ->
            mAdapter.submitList(list)
            Log.d("TAG", "onBindViewHolder: got ${list.size}")
        }
    }

}