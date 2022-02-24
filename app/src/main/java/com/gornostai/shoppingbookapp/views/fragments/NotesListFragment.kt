package com.gornostai.shoppingbookapp.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gornostai.shoppingbookapp.App
import com.gornostai.shoppingbookapp.MainViewModel
import com.gornostai.shoppingbookapp.databinding.FragmentNotesListBinding
import com.gornostai.shoppingbookapp.model.entities.NoteItem
import com.gornostai.shoppingbookapp.utils.ViewModelFactory
import com.gornostai.shoppingbookapp.views.activities.NewNoteActivity
import com.gornostai.shoppingbookapp.views.adapters.NoteAdapter

class NotesListFragment: BaseFragment(), NoteAdapter.Listener{

    private lateinit var binding: FragmentNotesListBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        ViewModelFactory((context?.applicationContext as App).dataBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun deleteItem(id: Int) {
        mainViewModel.deleteNote(id)
    }

    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity,NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY,note)
        }
        editLauncher.launch(intent)
    }

    private fun initRcView() = with(binding){
        fragmentNoteListRv.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        adapter = NoteAdapter(this@NotesListFragment)
        fragmentNoteListRv.adapter = adapter
    }

    private fun observer(){
        mainViewModel.allNotes.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    fun onEditResult(){
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                val editState = it.data?.getBooleanExtra(EDIT_STATE_KEY,false)
                if (editState == true){
                    mainViewModel.updateNotes(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                } else {
                    mainViewModel.insertNotes(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                }
            }
        }
    }

    companion object{
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_STATE_KEY = "edit_state_key"
        fun newInstance() = NotesListFragment()
    }
}