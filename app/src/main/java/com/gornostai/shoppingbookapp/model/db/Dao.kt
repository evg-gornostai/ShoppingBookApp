package com.gornostai.shoppingbookapp.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gornostai.shoppingbookapp.model.entities.NoteItem
import com.gornostai.shoppingbookapp.model.entities.ShoppingListItem
import com.gornostai.shoppingbookapp.model.entities.ShoppingListNameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>
    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShoppingListNameItem>>
    @Query("SELECT * FROM shopping_list_item WHERE listId LIKE :listId")
    fun getAllShopListItems(listId: Int): Flow<List<ShoppingListItem>>
    @Query("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)
    @Query("DELETE FROM shopping_list_names WHERE id IS :id")
    suspend fun deleteShopListName(id: Int)
    @Query("DELETE FROM shopping_list_item WHERE listId LIKE :listId")
    suspend fun deleteShopListItemsByListId(listId: Int)

    @Insert
    suspend fun insertShopListName(nameItem: ShoppingListNameItem)
    @Insert
    suspend fun insertNote(note: NoteItem)
    @Insert
    suspend fun insertItem(shopListItem: ShoppingListItem)
    @Update
    suspend fun updateNote(note: NoteItem)
    @Update
    suspend fun updateListItem(item: ShoppingListItem)
    @Update
    suspend fun updateListName(shopListNameItem: ShoppingListNameItem)
}