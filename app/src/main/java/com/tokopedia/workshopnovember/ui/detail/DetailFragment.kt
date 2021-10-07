package com.tokopedia.workshopnovember.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tokopedia.workshopnovember.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

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

    private val viewModel: DetailViewModel by viewModels()

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
            view.findViewById<ImageView>(R.id.iv_cover).run {
                Glide.with(requireContext())
                    .load(it.src)
                    .into(this)
            }
            view.findViewById<TextView>(R.id.tv_title).text = it.title
        }
    }


}