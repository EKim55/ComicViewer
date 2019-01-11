package edu.rosehulman.kime2.comicviewer

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import edu.rosehulman.comicviewer.Utils

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class ComicsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val comicWrappers = ArrayList<ComicWrapper>()
    val colors : IntArray = intArrayOf(android.R.color.holo_green_light, android.R.color.holo_blue_light,
        android.R.color.holo_orange_light, android.R.color.holo_red_light)
    var index: Int = 0

    init {
        for (i in 0..4) {
            createComic()
        }
    }

    fun createComic() {
        val comic = ComicWrapper(Utils.randomCleanIssueNumber(), colors[index])
        index = (index + 1) % 4
        comicWrappers.add(comic)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ComicFragment (defined as a static inner class below).
        return ComicFragment.newInstance(comicWrappers.get(position))
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Issue " + comicWrappers.get(position).xkcdIssue
    }

    override fun getCount(): Int {
        return comicWrappers.size
    }
}