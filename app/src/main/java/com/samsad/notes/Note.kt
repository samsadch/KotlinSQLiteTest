package com.samsad.notes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Note() : Parcelable {

    var noteId:Int?=null
    var noteName:String?=null
    var noteDesc:String?=null

    constructor(noteId:Int,name:String,desc:String) : this() {
        this.noteId=noteId
        this.noteName=name
        this.noteDesc=desc
    }

}