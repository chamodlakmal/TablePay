package lk.chamiviews.tablepay.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lk.chamiviews.tablepay.data.model.LocalCart

@Dao
interface CartDao {
    @Query("SELECT * FROM local_cart")
    fun getAll(): PagingSource<Int, LocalCart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(carts: List<LocalCart>)

    @Query("UPDATE local_cart SET isPaid = 1 WHERE id = :id")
    suspend fun markAsPaid(id: Int)

    @Query("DELETE FROM local_cart")
    suspend fun clearAll()
}