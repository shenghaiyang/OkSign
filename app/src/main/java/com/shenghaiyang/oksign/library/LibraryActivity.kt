package com.shenghaiyang.oksign.library

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.shenghaiyang.oksign.R
import kotlinx.android.synthetic.main.library_activity.*
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import kotlinx.serialization.serializer

class LibraryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        libraries_view.layoutManager = LinearLayoutManager(this)
        val json = resources.openRawResource(R.raw.libraries).bufferedReader().readText()
        val libraries = JSON.parse(Library::class.serializer().list, json)
        libraries_view.adapter = LibraryAdapter(libraries)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
