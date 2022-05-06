package com.stepper.plugin

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.header
import androidx.slice.builders.list
import androidx.slice.core.SliceAction

class MySliceProvider : androidx.slice.SliceProvider() {
    /**
     * Instantiate any required objects. Return true if the provider was successfully created,
     * false otherwise.
     */
    override fun onCreateSliceProvider(): Boolean {
        return true
    }

    /**
     * Converts URL to content URI (i.e. content://com.stepper.plugin...)
     */
    override fun onMapIntentToUri(intent: Intent): Uri {
        // Note: implementing this is only required if you plan on catching URL requests.
        // This is an example solution.
        var uriBuilder: Uri.Builder = Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
        if (intent == null) return uriBuilder.build()
        val data = intent.data
        val dataPath = data?.path
        if (data != null && dataPath != null) {
            val path = dataPath.replace("/", "")
            uriBuilder = uriBuilder.path(path)
        }
        val context = context
        if (context != null) {
            uriBuilder = uriBuilder.authority(context.packageName)
        }
        return uriBuilder.build()
    }

    /**
     * Construct the Slice and bind data if available.
     */
    override fun onBindSlice(sliceUri: Uri): Slice {
        // Note: you should switch your build.gradle dependency to
        // slice-builders-ktx for a nicer interface in Kotlin.
        //val activityAction = createActivityAction() ?: return null
        return list(context!!, sliceUri, ListBuilder.INFINITY) {
            header {
                title = "Hello World"
            }
        }
    }

    private fun createActivityAction(): SliceAction? {
        return null
        /*
        Instead of returning null, you should create a SliceAction. Here is an example:
        return SliceAction.create(
            PendingIntent.getActivity(
                context, 0, Intent(context, MyActivityClass::class.java), 0
            ),
            IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground),
            ListBuilder.ICON_IMAGE,
            "Open App"
        )
        */
    }

    /**
     * Slice has been pinned to external process. Subscribe to data source if necessary.
     */
    override fun onSlicePinned(sliceUri: Uri) {
        // When data is received, call context.contentResolver.notifyChange(sliceUri, null) to
        // trigger MySliceProvider#onBindSlice(Uri) again.
    }

    /**
     * Unsubscribe from data source if necessary.
     */
    override fun onSliceUnpinned(sliceUri: Uri) {
        // Remove any observers if necessary to avoid memory leaks.
    }
}