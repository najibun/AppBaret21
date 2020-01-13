package com.najibun.nim175410171.AppBaret21

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.najibun.nim175410171.AppBaret21.catatan.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pengurus.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonAddNote.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditNoteActivity::class.java),
                ADD_NOTE_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = NoteAdapter()
        recycler_view.adapter = adapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer<List<Note>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //showDialog()
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Kegiatan dihapus!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(baseContext, AddEditNoteActivity::class.java)
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                intent.putExtra(AddEditNoteActivity.EXTRA_JUDUL, note.title)
                intent.putExtra(AddEditNoteActivity.EXTRA_DESKRIPSI, note.description)
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITAS, note.priority)

                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Daftar Kegiatan"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean{
        super.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                showDialog()
//                noteViewModel.deleteAllNotes()
//                Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val newNote = Note(
                data!!.getStringExtra(AddEditNoteActivity.EXTRA_JUDUL),
                data.getStringExtra(AddEditNoteActivity.EXTRA_DESKRIPSI),
                data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITAS, 1)
            )
            noteViewModel.insert(newNote)
            Toast.makeText(this, "Kegaitan disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }

            val updateNote = Note(
                data!!.getStringExtra(AddEditNoteActivity.EXTRA_JUDUL),
                data.getStringExtra(AddEditNoteActivity.EXTRA_DESKRIPSI),
                data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITAS, 1)
            )
            updateNote.id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            noteViewModel.update(updateNote)
        } else {
            Toast.makeText(this, "Kegiatan tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog(){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Yakin untuk Hapus Semua ???")

        // Set a message for alert dialog
        builder.setMessage("Ini akan menghapus semua data, dan tidak bisa dikembalikan.")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> toast("Yes, Dihapus!")
                DialogInterface.BUTTON_NEGATIVE -> toast("No button clicked")
                DialogInterface.BUTTON_NEUTRAL -> toast("Cancel button clicked.")
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES"){dialog, which ->
        noteViewModel.deleteAllNotes()
        Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
        }


        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("CANCEL",dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }
    // Extension function to show toast message
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

