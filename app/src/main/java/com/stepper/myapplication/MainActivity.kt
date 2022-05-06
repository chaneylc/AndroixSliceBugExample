package com.stepper.myapplication

import android.content.ComponentName
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.slice.SliceViewManager
import androidx.slice.widget.SliceLiveData
import androidx.slice.widget.SliceView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sliceView = findViewById<SliceView>(R.id.slice)

//        val uri = Uri.parse("content://com.stepper.plugin/").apply {
//            authority = "com.stepper.plugin"
//        }

        val uri = Uri.Builder().authority("com.stepper.plugin")
            .scheme(ContentResolver.SCHEME_CONTENT)
            .path("/")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            //component = ComponentName("com.stepper.plugin", "com.stepper.plugin.MySliceProvider")
            //setPackage("com.stepper.plugin")
        }

        val slice = SliceViewManager.getInstance(this)
            .bindSlice(uri)

        SliceLiveData.fromIntent(this, intent).observe(this) { s ->

            sliceView.slice = s
        }

        val client = contentResolver.acquireContentProviderClient(uri)
        val uclient = contentResolver.acquireUnstableContentProviderClient(uri)

        val res = contentResolver.query(uri, null, "*", null, null)
        Log.d("test", "")
    }
}