package com.samsad.notes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        addNoteButton.setOnClickListener {
            var dbManager = DBManager(this)
            var values=ContentValues()
            values.put("Title",titleEdt.text.toString())
            values.put("Description",descriptionEdt.text.toString())

            val id = dbManager.Inser(values)

            if(id>0){
                Toast.makeText(this,"Note is added with id="+id, Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Note cannot add",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
