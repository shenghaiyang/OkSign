package com.shenghaiyang.oksign.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.shenghaiyang.oksign.R
import com.shenghaiyang.oksign.about.AboutActivity
import com.shenghaiyang.oksign.library.LibraryActivity
import kotlinx.android.synthetic.main.main_activity.*
import okio.ByteString

class MainActivity : AppCompatActivity() {

    private val tagPackages = "tagPackages"
    private val tagSignatures = "tagSignatures"
    private val signatures = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        package_view.setAdapter(
                ArrayAdapter(this, android.R.layout.simple_list_item_1, installedPackages()))
        package_view.setOnItemClickListener { parent, _, position, _ ->
            val packageName = parent.getItemAtPosition(position).toString()
            updateSignatures(packageName)
        }
        if (savedInstanceState != null) {
            savedInstanceState.getString(tagPackages)?.let {
                package_view.setText(it)
            }
            savedInstanceState.getStringArrayList(tagSignatures)?.let {
                signatures.clear()
                signatures.addAll(it)
            }
        }
        signatures_view.layoutManager = LinearLayoutManager(this)
        signatures_view.adapter = SignatureAdapter(signatures)
        find_view.setOnClickListener {
            val packageName = package_view.text.toString()
            if (packageName.isBlank()) {
                package_view.error = "Package cannot be empty."
            } else {
                updateSignatures(packageName)
            }
        }
    }

    private fun installedPackages() = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES)
            .map { it.packageName }.toTypedArray().sortedArray()

    private fun updateSignatures(packageName: String) {
        val info = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES).find {
            it.packageName == packageName
        }
        if (info == null) {
            package_view.error = "Cannot find package."
            return
        }
        signatures.clear()
        info.signatures.forEach {
            val byteString = ByteString.of(*it.toByteArray())
            signatures.add(byteString.md5().hex())
        }
        signatures_view.adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.main_about -> {
            startActivity(Intent(this, AboutActivity::class.java))
            true
        }
        R.id.main_library -> {
            startActivity(Intent(this, LibraryActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val packageName = package_view.text.toString()
        if (packageName.isNotBlank()) {
            outState.putString(tagPackages, packageName)
        }
        if (signatures.isNotEmpty()) {
            outState.putStringArrayList(tagSignatures, signatures)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(tagPackages)?.let {
            if (it.isNotBlank()) {
                package_view.setText(it)
            }
        }
    }

}
