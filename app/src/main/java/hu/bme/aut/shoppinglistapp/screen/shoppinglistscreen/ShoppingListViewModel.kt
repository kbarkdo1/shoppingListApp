package hu.bme.aut.shoppinglistapp.screen.shoppinglistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.shoppinglistapp.data.SLDAO
import hu.bme.aut.shoppinglistapp.data.SLItem
import hu.bme.aut.shoppinglistapp.data.catTuple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val slDAO: SLDAO
) : ViewModel() {

    fun getAllShoppingList(): Flow<List<SLItem>> {
        return slDAO.getAllSLItems()
    }

    suspend fun getAllItemNum(): Int {
        return slDAO.getItemNum()
    }

    fun addItemtoList(item: SLItem) {
        viewModelScope.launch {
            slDAO.insert(item)
        }
    }


    fun removeItem(item: SLItem) {
        viewModelScope.launch {
            slDAO.delete(item)
        }
    }

    fun editItem(editedItem: SLItem) {
        viewModelScope.launch {
            slDAO.update(editedItem)
        }
    }

    fun changeItemState(item: SLItem, value: Boolean) {
        val newItem = item.copy()
        newItem.status = value
        viewModelScope.launch {
            slDAO.update(newItem)
        }
    }

    fun clearAllItems() {
        viewModelScope.launch {
            slDAO.deleteAllTodos()
        }
    }

    fun categorySummary(): Flow<List<catTuple>> {
        return slDAO.getCategorySums()
        /*val sLItemsList: List<SLItem> = todoDAO.getAllItems()
        var output = mutableMapOf<ItemCategory, Float>()
        for (value in ItemCategory.values()) {
            output[value] = 0f
        }
        for (items in sLItemsList) {
            output[items.category] = output[items.category]!! + items.price
        }
        val returnList = mutableListOf<Pair<ItemCategory, Float>>()
        for (value in ItemCategory.values()) {
            returnList.add(Pair(value, output[value]) as Pair<ItemCategory, Float>)
        }
        return returnList*/
    }

    fun total(): Flow<List<Float>> {
        /*var total = 0.0f
        val sLItemsList: List<SLItem> = todoDAO.getAllItems()
        for (items in sLItemsList) {
            total += items.price
        }

        return total*/
        return slDAO.total()
    }
}