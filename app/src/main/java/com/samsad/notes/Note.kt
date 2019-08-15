package com.samsad.notes

import java.io.Serializable


class Note() : Serializable {

    var noteId:Int?=null
    var noteName:String?=null
    var noteDesc:String?=null

    constructor(noteId:Int,name:String,desc:String) : this() {
        this.noteId=noteId
        this.noteName=name
        this.noteDesc=desc
    }

}