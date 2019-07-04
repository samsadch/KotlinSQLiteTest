package com.samsad.notes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotesActivity : AppCompatActivity() {

    lateinit var note:Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        note = intent.getParcelableExtra("NOTE")
        if(note!=null){
            titleEdt.setText(note.noteName)
            descriptionEdt.setText(note.noteDesc)
            addNoteButton.text = "Edit"
        }

        addNoteButton.setOnClickListener {
            var dbManager = DBManager(this)
            var values=ContentValues()
            values.put("Title",titleEdt.text.toString())
            values.put("Description",descriptionEdt.text.toString())

            val id = dbManager.Insert(values)

            if(id>0){
                Toast.makeText(this,"Note is added with id="+id, Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Note cannot add",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
