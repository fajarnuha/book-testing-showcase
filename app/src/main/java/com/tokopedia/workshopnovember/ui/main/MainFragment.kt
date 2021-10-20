package com.tokopedia.workshopnovember.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.workshopnovember.Navigation
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.getViewVisibility
import com.tokopedia.workshopnovember.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val mAdapter = SearchResultAdapter(listener = ::onClickItem)
    private var navListener: Navigation? = null

    override fun onAttach(context: Context) {
        if (context is Navigation) {
            navListener = context
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = view.findViewById<EditText>(R.id.et_search)
        val loadingView = view.findViewById<ProgressBar>(R.id.progressBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv)

        editText.setOnEditorActionListener { v, actionId, event ->
            if (isSearchAction(actionId)) {
                hideKeyboard(context, editText)
                viewModel.search(v.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        with(recyclerView) {
            layoutManager = GridLayoutManager(
                requireContext(),
                3,
                RecyclerView.VERTICAL,
                false
            )
            adapter = mAdapter
        }

        viewModel.result.observe(viewLifecycleOwner) { list ->
            mAdapter.submitList(list)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            loadingView.visibility = getViewVisibility(isLoading)
            recyclerView.visibility = getViewVisibility(!isLoading)
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }

    private fun isSearchAction(actionId: Int) =
        actionId == EditorInfo.IME_ACTION_SEARCH
            || actionId == EditorInfo.IME_ACTION_UNSPECIFIED

    private fun onClickItem(id: String) {
        if (id.isEmpty()) {
            Toast.makeText(context, "ISBN is empty", Toast.LENGTH_SHORT).show()
            return
        }
        navListener?.toDetail(id)
    }

}