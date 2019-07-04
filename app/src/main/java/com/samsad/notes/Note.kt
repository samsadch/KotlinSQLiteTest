package com.samsad.notes

class Note {

    var noteId:Int?=null
    var noteName:String?=null
    var noteDesc:String?=null

    constructor(noteId:Int,name:String,desc:String){
        this.noteId=noteId
        this.noteName=name
        this.noteDesc=desc
    }


}