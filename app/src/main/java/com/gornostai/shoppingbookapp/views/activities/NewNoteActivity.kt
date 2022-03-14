package com.gornostai.shoppingbookapp.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.databinding.ActivityNewNoteBinding
import com.gornostai.shoppingbookapp.model.entities.NoteItem
import com.gornostai.shoppingbookapp.utils.TimeManager
import com.gornostai.shoppingbookapp.views.fragments.NotesListFragment

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getNote()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_note,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.new_note_menu_save -> {
                setMainResult()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMainResult(){
        var editState = false
        val tempNote = if (note == null){
            createNewNote()
        } else {
            editState = true
            updateNote()
        }
        val intent = Intent().apply {
            putExtra(NotesListFragment.NEW_NOTE_KEY,tempNote)
            putExtra(NotesListFragment.EDIT_STATE_KEY,editState)
        }
        setResult(RESULT_OK,intent)
        finish()
    }

    private fun updateNote(): NoteItem? = with(binding){
        note?.copy(
            title = newNoteTitleEd.text.toString(),
            description = newNoteDescriptionEd.text.toString()
        )
    }

    private fun createNewNote(): NoteItem{
        return NoteItem(
            null,
            binding.newNoteTitleEd.text.toString(),
            binding.newNoteDescriptionEd.text.toString(),
            TimeManager.getCurrentTime(),
        )
    }

    private fun getNote() {
        val sNote = intent.getSerializableExtra(NotesListFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote() = with(binding) {
        newNoteTitleEd.setText(note?.title)
        newNoteDescriptionEd.setText(note?.description)
    }

}