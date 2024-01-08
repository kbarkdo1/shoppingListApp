package hu.bme.aut.shoppinglistapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SLItem::class], version = 4, exportSchema = false)
abstract class ShoppingListAppDatabase : RoomDatabase() {

    abstract fun SLDao(): SLDAO

    companion object {
        @Volatile
        private var Instance: ShoppingListAppDatabase? = null

        fun getDatabase(context: Context): ShoppingListAppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ShoppingListAppDatabase::class.java,
                    "shoppinglist_database.db")
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}