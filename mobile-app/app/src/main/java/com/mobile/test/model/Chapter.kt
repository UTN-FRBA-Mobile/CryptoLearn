package com.mobile.test.model

import android.os.Parcel
import android.os.Parcelable

data class Chapter(
    val name: String?,
    val url: String?,
    val questions: MutableList<Question>?,
    val image: String?,
    val state: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        arrayListOf<Question>().apply {
            parcel.readArrayList(Question::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeList(questions)
        parcel.writeString(image)
        parcel.writeString(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chapter> {
        override fun createFromParcel(parcel: Parcel): Chapter {
            return Chapter(parcel)
        }

        override fun newArray(size: Int): Array<Chapter?> {
            return arrayOfNulls(size)
        }
    }
}