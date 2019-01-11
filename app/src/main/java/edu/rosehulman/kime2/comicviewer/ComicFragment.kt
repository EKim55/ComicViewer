package edu.rosehulman.kime2.comicviewer

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*


private const val ARG_COMIC = "param1"

/**
 * A placeholder fragment containing a simple view.
 */
class ComicFragment : Fragment(), GetComicTask.ComicConsumer, GetImageTask.ImageConsumer {

    private var comicWrapper: ComicWrapper? = null
    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            comicWrapper = it.getParcelable(ARG_COMIC)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        rootView.setBackgroundResource(comicWrapper!!.color)
        this.rootView = rootView
        if (comicWrapper != null) {
            var urlString = "https://xkcd.com/${comicWrapper?.xkcdIssue}/info.0.json"
            GetComicTask(this).execute(urlString)
        }
        return rootView
    }

    override fun onComicLoaded(comic: Comic?) {
        comicWrapper?.comic = comic
        rootView!!.comicTitle.text = comic?.safe_title
        GetImageTask(this).execute(comic?.img)
    }

    override fun onImageLoaded(bitmap: Bitmap?) {
        rootView!!.imageView.setImageBitmap(bitmap)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var builder = AlertDialog.Builder(this.context!!)
        builder.setTitle(getString(R.string.alt_text_title, comicWrapper?.xkcdIssue))
        builder.setMessage(comicWrapper?.comic?.alt)
        builder.create().show()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(comicWrapper: ComicWrapper) =
            ComicFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_COMIC, comicWrapper)
                }
            }
    }
}