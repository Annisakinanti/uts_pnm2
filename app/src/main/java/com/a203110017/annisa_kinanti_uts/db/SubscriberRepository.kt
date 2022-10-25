package com.a203110017.annisa_kinanti_uts.db

// TODO 4 Kelas Repositori
/*
Repositori adalah kelas penting dalam arsitektur Android MVVM. Ada dua jenis sumber data dalam proyek Android.
Lokal dan Terpencil. Aplikasi ini hanya akan memiliki sumber data lokal. Di MVVM, model tampilan tidak tahu apa-apa tentang sumber data. ViewModel hanya meminta data dari repositori atau mengirim data ke repositori. Ini adalah tugas repositori untuk menangani database lokal atau API jarak jauh seperti yang diminta oleh model tampilan.
Dalam proyek Android yang lebih besar, bisa ada lebih dari satu kelas repositori.
 */
class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber): Long {
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber): Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}