package lk.chamiviews.tablepay.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import lk.chamiviews.tablepay.data.model.LocalCart

@Database(
    entities = [LocalCart::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CartDatabase : RoomDatabase() {
    abstract val cartDao: CartDao
}