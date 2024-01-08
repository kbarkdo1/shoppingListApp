package hu.bme.aut.shoppinglistapp.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

data class catTuple(
    @ColumnInfo(name = "category") val category: ItemCategory,
    @ColumnInfo(name = "price") val price: Float
)
@Dao
interface SLDAO {
    @Query("SELECT * from shoppinglisttable")
    fun getAllSLItems(): Flow<List<SLItem>>

    @Query("SELECT CATEGORY, SUM(PRICE) AS price FROM shoppinglisttable GROUP BY CATEGORY")
    fun getCategorySums(): Flow<List<catTuple>>

    @Query("Select SUM(PRICE) from shoppinglisttable")
    fun total(): Flow<List<Float>>

    @Query("SELECT * from shoppinglisttable")
    fun getAllItems(): List<SLItem>

    @Query("SELECT * from shoppinglisttable WHERE id = :id")
    fun getItem(id: Int): Flow<SLItem>

    @Query("SELECT COUNT(*) from shoppinglisttable")
    suspend fun getItemNum(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: SLItem)

    @Update
    suspend fun update(slItem: SLItem)

    @Delete
    suspend fun delete(slItem: SLItem)

    @Query("DELETE from shoppinglisttable")
    suspend fun deleteAllTodos()
}