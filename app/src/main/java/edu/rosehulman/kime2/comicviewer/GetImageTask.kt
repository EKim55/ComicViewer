package edu.rosehulman.kime2.comicviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.lang.Exception
import java.net.URL

class GetImageTask(private var imageConsumer: GetImageTask.ImageConsumer): AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg urlStrings: String?):  Bitmap?{
        val url = URL(urlStrings[0])
        return try {
            val inStream = url.openStream()
            val bitmap = BitmapFactory.decodeStream(inStream)
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        imageConsumer.onImageLoaded(result)
    }

    interface ImageConsumer {
        fun onImageLoaded(bitmap: Bitmap?)
    }
}