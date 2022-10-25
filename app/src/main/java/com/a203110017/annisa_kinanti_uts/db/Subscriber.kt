package com.a203110017.annisa_kinanti_uts.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO 1: Buat kelas Entitas
/*
Jadi, untuk menjadikan kelas “ Subscriber ” ini sebagai kelas entitas ruang, kita perlu menandainya dengan anotasi @Entity .
Juga, kita perlu memberikan nama tabel dengan itu. Beri nama tabel database baru ini sebagai “ subscriber_data_table “.
@Entity ( tableName = "subscriber_data_table" ) Juga,
kita perlu menandai satu atribut dari kelas entitas ini sebagai kunci utama.
kita melakukannya dengan anotasi @PrimaryKey   .
Jika Anda ingin kolom tabel basis data Anda memiliki nama yang berbeda dari nama atribut,
Anda harus memberikannya menggunakan anotasi @ColumnInfo .
 */
@Entity(tableName = "subscriber_data_table")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id")
    var id: Int,

    @ColumnInfo(name = "subscriber_name")
    var name: String,

    @ColumnInfo(name = "subscriber_email")
    var email: String

)