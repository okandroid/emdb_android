package com.epigra.emdb.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.epigra.emdb.R
import com.epigra.emdb.core.BaseActivity
import org.jetbrains.anko.startActivity

class Activity_Splash : BaseActivity() {
    override fun getAnalyticsScreenName(): String? = javaClass.simpleName

    override fun getContentLayoutID(): Int = R.layout.activity_splash

    override fun findUI() {

    }

    override fun setupUI(savedInstanceState: Bundle?) {
        val h = Handler()
        h.postDelayed({
            startActivity<Activity_Main>()
            finish()
        }, 1500)
    }

    override fun onActivityResultFetched(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun onPermissionResultFetched(
        requestCode: Int,
        grantedPermissions: Array<String>,
        deniedPermissions: Array<String>
    ) {

    }

    override fun <E> onBusDataFetched(data: E) {

    }
}