package com.tokopedia.workshopnovember.ui.login

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tokopedia.workshopnovember.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

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
        val userTv = view.findViewById<TextView>(R.id.tv_user)

        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                LoginState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    loginButton.visibility = View.GONE
                    regisButton.visibility = View.GONE
                    userTv.visibility = View.GONE
                }
                is LoginState.LoginDisplay -> {
                    progressBar.visibility = View.GONE
                    val loginVis = if (it.options.options.contains(0)) View.VISIBLE else View.GONE
                    val regisVis = if (it.options.options.contains(1)) View.VISIBLE else View.GONE
                    loginButton.visibility = loginVis
                    regisButton.visibility = regisVis
                    userTv.visibility = View.GONE
                }
                is LoginState.UserDisplay -> {
                    progressBar.visibility = View.GONE
                    loginButton.visibility = View.GONE
                    regisButton.visibility = View.GONE
                    userTv.visibility = View.VISIBLE
                    userTv.text = it.user.name
                }
            }
        }

        viewModel.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        }

        loginButton.setOnClickListener {
            val email = editTextEmail.text.toString()
            val pass = editTextPasswd.text.toString()
            progressBar.visibility = View.VISIBLE
            viewModel.login(email, pass)
        }
    }
}