package com.gornostai.shoppingbookapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gornostai.shoppingbookapp.model.db.MainDataBase
import com.gornostai.shoppingbookapp.model.entities.NoteItem
import com.gornostai.shoppingbookapp.model.entities.ShoppingListItem
import com.gornostai.shoppingbookapp.model.entities.ShoppingListNameItem
import kotlinx.coroutines.launch

class MainViewModel(dataBase: MainDataBase): ViewModel() {
    val dao = dataBase.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames: LiveData<List<ShoppingListNameItem>> = dao.getAllShopListNames().asLiveData()

    fun getAllItemsFromList(listId: Int): LiveData<List<ShoppingListItem>>{
        return dao.getAllShopListItems(listId).asLiveData()
    }

    fun insertNotes(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertShopListItem(shopListItem: ShoppingListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
    }

    fun insertShoppingListName(listNameItem: ShoppingListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listNameItem)
    }

    fun updateNotes(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun updateListItem(item: ShoppingListItem) = viewModelScope.launch {
        dao.updateListItem(item)
    }

    fun updateListName(shopListNameItem: ShoppingListNameItem) = viewModelScope.launch {
        dao.updateListName(shopListNameItem)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun deleteShopList(id: Int) = viewModelScope.launch {
        dao.deleteShopListName(id)
        dao.deleteShopListItemsByListId(id)
    }

    fun clearShopList(id: Int) = viewModelScope.launch {
        dao.deleteShopListItemsByListId(id)
    }

}