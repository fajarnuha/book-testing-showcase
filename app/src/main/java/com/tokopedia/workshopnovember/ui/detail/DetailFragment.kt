package com.tokopedia.workshopnovember.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tokopedia.workshopnovember.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val viewModel: DetailViewModel by viewModels()

    companion object {
        private const val ARGS_ID = "ARGS_ID"
        fun newInstance(id: String): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_ID, id)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().getString(ARGS_ID).let {
            requireNotNull(it)
            viewModel.setId(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.book.observe(viewLifecycleOwner) {
            Toast.makeText(context, "${it.title}", Toast.LENGTH_SHORT).show()
        }
    }


}