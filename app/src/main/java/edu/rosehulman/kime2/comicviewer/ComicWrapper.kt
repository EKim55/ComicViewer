package edu.rosehulman.kime2.comicviewer

import android.os.Parcel
import android.os.Parcelable

data class ComicWrapper (var xkcdIssue: Int = 0, var color: Int = android.R.color.holo_green_light) : Parcelable {

    var comic: Comic? = null

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(xkcdIssue)
        parcel.writeInt(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicWrapper> {
        override fun createFromParcel(parcel: Parcel): ComicWrapper {
            return ComicWrapper(parcel)
        }

        override fun newArray(size: Int): Array<ComicWrapper?> {
            return arrayOfNulls(size)
        }
    }
}