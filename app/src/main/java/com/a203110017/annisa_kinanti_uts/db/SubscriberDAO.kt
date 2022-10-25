package com.a203110017.annisa_kinanti_uts.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
// TODO 2: Interface DAO
/*
DAO adalah singkatan dari "objek akses data". Untuk bekerja dengan perpustakaan ruangan, kita harus selalu membuat antarmuka DAO.
Setelah itu, kita harus mendefinisikan fungsi (metode) ruangan mengikuti standar yang diberikan oleh perpustakaan.
 */
@Dao
interface SubscriberDAO {

    /*
    kita perlu membubuhi keterangan ini dengan @Insert . Karena kita akan menggunakan coroutine,
    kita perlu menjadikannya sebagai fungsi suspending dengan menggunakan modifier “ suspend ”.
     */
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber) : Long


    /*
    Untuk membuat mereka memperbarui dan menghapus fungsi kamar, kami telah membubuhi keterangan dengan @Update dan @Delete . Karena kita menggunakan coroutine, kita juga harus memodifikasinya dengan modifier “suspend”.
    Keduanya harus memiliki instance "Subcriber" sebagai parameter.
     */
    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) : Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber) : Int


    /*
    kita akan mendefinisikan sebuah fungsi untuk mendapatkan daftar semua data pelanggan yang disimpan dari database.
    Di sini, kita perlu menulis kueri SQL untuk mendapatkan semua data dari tabel database dengan @Query
     */
    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers():Flow<List<Subscriber>>
}