package hu.bme.aut.shoppinglistapp.data

import androidx.compose.ui.res.stringResource
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.todoapp.R
import java.io.Serializable


@Entity(tableName = "shoppinglisttable")
data class SLItem(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "status") var status: Boolean,
    @ColumnInfo(name = "category") var category: ItemCategory
) : Serializable

enum class ItemCategory {
    FOOD, TECH, MED;

    fun getIcon(): Int {
        return if (this == FOOD) R.drawable.apple else if (this == TECH) R.drawable.phone else R.drawable.pill
    }

}

