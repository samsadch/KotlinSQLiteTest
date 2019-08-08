package com.samsad.notes

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import android.content.Intent
import android.view.MenuItem
import com.samsad.notes.Const.Companion.DB_DESC
import com.samsad.notes.Const.Companion.DB_ID
import com.samsad.notes.Const.Companion.DB_TITLE


class AddNotesActivity : AppCompatActivity() {

    lateinit var note:Note
    var id: Int = 0
    lateinit var title:String
    lateinit var desc:String
    var context: Context = this@AddNotesActivity

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                // app icon in action bar clicked; go home
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        var actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.title = getString(R.string.title_add_notes)
        actionbar.setIcon(R.drawable.ic_back)


        if(intent.getStringExtra("TITLE")!=null) {
            note = intent.getParcelableExtra("NOTE")
            actionbar.title = getString(R.string.title_edit_notes)
            title = intent.getStringExtra("TITLE")
            desc = intent.getStringExtra("DESC")
            id = intent.getIntExtra("ID", 0)
        }

        if(id!=0){
            titleEdt.setText(title)
            descriptionEdt.setText(desc)
            addNoteButton.text = getString(R.string.button_edit)
        }

        addNoteButton.setOnClickListener {
            var dbManager = DBManager(this)
            var values=ContentValues()
            values.put(DB_TITLE,titleEdt.text.toString())
            values.put(DB_DESC,descriptionEdt.text.toString())

            if(id==0) {
                if(titleEdt.text.toString()!="") {
                    val id = dbManager.Insert(values)

                    if (id > 0) {
                        Toast.makeText(context, getString(R.string.note_success_msg), Toast.LENGTH_LONG)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(context, getString(R.string.note_failure_msg), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, getString(R.string.note_empty_msg), Toast.LENGTH_SHORT).show()
                }
            }else{
                var selectionArgs = arrayOf(id.toString())
                val ID = dbManager.Update(values,"$DB_ID = ?",selectionArgs)

                if (ID > 0) {
                    Toast.makeText(context, getString(R.string.note_update_success_msg), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(context, getString(R.string.note_update_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
