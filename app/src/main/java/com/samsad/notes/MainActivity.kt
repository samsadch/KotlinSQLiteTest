package com.samsad.notes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var noteList=ArrayList<Note>()
    lateinit var adapter:NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter=NotesAdapter(noteList,this)
        loadFromDb("%")
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addFloat.setOnClickListener {
            startActivity(Intent(this,AddNotesActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadFromDb("%")
    }

    private fun loadFromDb(title:String) {
        var dbManager = DBManager(this)
        val selectionArgs = arrayOf(title)
        val projections = arrayOf("ID","Title","Description")
        val cursor = dbManager.Query(projections,"Title like ?",selectionArgs,"Title")
        noteList.clear()
        if(cursor.moveToFirst()){
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val desc = cursor.getString(cursor.getColumnIndex("Description"))

                noteList.add(Note(ID,title,desc))

            }while (cursor.moveToNext())
        }
        adapter.notifyDataSetChanged()

    }

    inner class NotesAdapter(list:ArrayList<Note>,mContext:Context): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

        var list =ArrayList<Note>()
        var mContext:Context
        var inflater:LayoutInflater

        init {
            this.list=list
            this.mContext=mContext
            this.inflater= LayoutInflater.from(mContext)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = inflater.inflate(R.layout.ticket,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val note = list.get(position)
            holder.titleTxv.text=note.noteName
            holder.descRxv.text=note.noteDesc


            holder.editButton.setOnClickListener {
               /* var intent = Intent(mContext,AddNotesActivity::class.java)
                intent.putExtra("NOTE",note)
                startActivity(intent)*/
                val intent = Intent(mContext, AddNotesActivity::class.java)
                intent.putExtra("NOTE", note)
                startActivity(intent)
            }
            holder.deleteButton.setOnClickListener {
                var dbManager = DBManager(mContext)
                val selectionArg = arrayOf(note.noteId.toString())
                dbManager.Delete("ID = ?",selectionArg)
                loadFromDb("%")
            }
        }
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTxv = itemView.titile
            val descRxv = itemView.description
            val editButton = itemView.editButton
            val deleteButton = itemView.deleteButton
        }

    }
}
