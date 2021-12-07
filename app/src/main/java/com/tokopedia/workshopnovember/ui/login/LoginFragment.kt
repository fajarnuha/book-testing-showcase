package com.tokopedia.workshopnovember.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tokopedia.workshopnovember.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.login_fragment) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginButton = view.findViewById<Button>(R.id.button_login)
        val regisButton = view.findViewById<Button>(R.id.button_rgs)
        val editTextEmail = view.findViewById<EditText>(R.id.et_email)
        val editTextPasswd = view.findViewById<EditText>(R.id.et_passwd)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

        viewModel.options.observe(viewLifecycleOwner) {
            val loginVis = if (it.options.contains(0)) View.VISIBLE else View.GONE
            val regisVis = if (it.options.contains(1)) View.VISIBLE else View.GONE
            loginButton.visibility = loginVis
            regisButton.visibility = regisVis
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            val visibility = if (it) View.VISIBLE else View.GONE
            progressBar.visibility = visibility
        }

        viewModel.user.observe(viewLifecycleOwner) {
            // Set user text view, profile image, etc...
        }

        loginButton.setOnClickListener {
            val email = editTextEmail.text.toString()
            val pass = editTextPasswd.text.toString()
            progressBar.visibility = View.VISIBLE
            viewModel.login(email, pass)
        }
    }
}