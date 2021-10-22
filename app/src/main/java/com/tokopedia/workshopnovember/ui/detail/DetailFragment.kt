package com.tokopedia.workshopnovember.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coverView = view.findViewById<ImageView>(R.id.iv_cover)
        val titleView = view.findViewById<TextView>(R.id.tv_title)
        val msgView = view.findViewById<TextView>(R.id.tv_message)
        val loadingView = view.findViewById<ProgressBar>(R.id.pb)
        val checkFav = view.findViewById<CheckBox>(R.id.cb_fav)
        viewModel.state.observe(viewLifecycleOwner) {
            // default state
            msgView.visibility = View.INVISIBLE
            loadingView.visibility = View.INVISIBLE
            when (it) {
                is DetailState.Detail -> {
                    coverView.run {
                        Glide.with(requireContext())
                            .load(it.data.src)
                            .into(this)
                    }
                    titleView.text = it.data.title
                    checkFav.isChecked = it.isFavorite
                    checkFav.tag = it.data.id

                    checkFav.visibility = View.VISIBLE
                }
                is DetailState.Error -> {
                    msgView.visibility = View.VISIBLE
                    msgView.text = "Error: ${it.msg}"

                    checkFav.visibility = View.GONE
                }
                DetailState.Loading -> {
                    loadingView.visibility = View.VISIBLE

                    checkFav.visibility = View.GONE
                }
            }

        }

        checkFav.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("BookShowcase", "onViewCreated: $isChecked")
            buttonView.tag?.let {
                if (it is String) viewModel.setFavorite(it, isChecked)
            }
        }
    }


}