package com.tokopedia.workshopnovember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tokopedia.workshopnovember.ui.detail.DetailFragment
import com.tokopedia.workshopnovember.ui.login.LoginFragment
import com.tokopedia.workshopnovember.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment())
                    .commitNow()
        }
    }

    override fun toDetail(id: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}