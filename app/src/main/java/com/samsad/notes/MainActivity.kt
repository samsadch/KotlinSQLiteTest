package com.samsad.notes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samsad.notes.Const.Companion.DB_DESC
import com.samsad.notes.Const.Companion.DB_ID
import com.samsad.notes.Const.Companion.DB_TITLE
import com.samsad.notes.Const.Companion.INTENT_NOTE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var noteList=ArrayList<Note>()
    lateinit var searchView:SearchView
    lateinit var adapter:NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.ThemeDark)
        setContentView(R.layout.activity_main)

        adapter=NotesAdapter(noteList,this)
        loadFromDb("%")
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadFromDb("%")
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        searchView = menu!!.findItem(R.id.search).actionView as SearchView
        val searchManger = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManger.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                loadFromDb("%$p0%")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                loadFromDb("%$p0%")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_menu -> {
                startActivity(Intent(this,AddNotesActivity::class.java))
                return true
            }
            R.id.search -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun loadFromDb(title:String) {
        var dbManager = DBManager(this)
        val selectionArgs = arrayOf(title)
        val projections = arrayOf(DB_ID,DB_TITLE,DB_DESC)
        val cursor = dbManager.Query(projections,"$DB_TITLE like ?",selectionArgs, DB_TITLE)
        noteList.clear()
        if(cursor.moveToFirst()){
            do {
                val ID = cursor.getInt(cursor.getColumnIndex(DB_ID))
                val title = cursor.getString(cursor.getColumnIndex(DB_TITLE))
                val desc = cursor.getString(cursor.getColumnIndex(DB_DESC))

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
            holder.descTxv.text=note.noteDesc


            /*var outValue:TypedValue = TypedValue()
            theme.resolveAttribute(R.attr.themeName, outValue, true);
            if ("dark".equals(outValue.string)) {
                    holder.topLlay.setBackgroundResource(R.drawable.dark_round_card)
            }*/

            holder.editButton.setOnClickListener {
                val intent = Intent(mContext, AddNotesActivity::class.java)
                intent.putExtra(INTENT_NOTE, note)
                startActivity(intent)
            }
            holder.deleteButton.setOnClickListener {

                var dbManager = DBManager(mContext)
                val selectionArg = arrayOf(note.noteId.toString())
                dbManager.Delete("$DB_ID = ?",selectionArg)
                loadFromDb("%")
            }
        }
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTxv: TextView = itemView.titile
            val descTxv = itemView.description!!
            val editButton: Button = itemView.editButton
            val deleteButton: Button = itemView.deleteButton
            val topLlay:LinearLayout = itemView.topLlay
        }

    }
}
