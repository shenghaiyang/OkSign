package com.shenghaiyang.oksign.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.shenghaiyang.oksign.BuildConfig
import com.shenghaiyang.oksign.R
import kotlinx.android.synthetic.main.about_activity.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        version_view.text = BuildConfig.VERSION_NAME
        github_view.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(github_view.text.toString())
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
