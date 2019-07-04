package com.samsad.notes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotesActivity : AppCompatActivity() {

    lateinit var note:Note
    var id: Int = 0
    lateinit var title:String
    lateinit var desc:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)


        title = intent.getStringExtra("TITLE")
        desc = intent.getStringExtra("DESC")
        id = intent.getIntExtra("ID",0)


        if(id!=0){
            titleEdt.setText(title)
            descriptionEdt.setText(desc)
            addNoteButton.text = "Edit"
        }

        addNoteButton.setOnClickListener {
            var dbManager = DBManager(this)
            var values=ContentValues()
            values.put("Title",titleEdt.text.toString())
            values.put("Description",descriptionEdt.text.toString())

            if(id==0) {

                val id = dbManager.Insert(values)

                if (id > 0) {
                    Toast.makeText(this, "Note is added with id=" + id, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Note cannot add", Toast.LENGTH_SHORT).show()
                }
            }else{
                var selectionArgs = arrayOf(id.toString())
                val ID = dbManager.Update(values,"ID = ?",selectionArgs)

                if (ID > 0) {
                    Toast.makeText(this, "Note is Updated", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Note cannot be updated", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
