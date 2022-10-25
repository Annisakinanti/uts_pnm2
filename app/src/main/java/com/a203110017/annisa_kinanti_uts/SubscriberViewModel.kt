package com.a203110017.annisa_kinanti_uts

import android.util.Patterns
import androidx.lifecycle.*
import com.a203110017.annisa_kinanti_uts.db.Subscriber
import com.a203110017.annisa_kinanti_uts.db.SubscriberRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO 5: Kelas View Model
/*
Jadi, mari buat kelas Kotlin baru. Beri nama sebagai “SubscriberViewModel”.
Agar memenuhi syarat sebagai Android Jetpack Viewmodel, kelas ini harus merupakan subkelas dari kelas ViewModel.
Oleh karena itu, kita perlu memperluas kelas ViewModel . ViewModel berkomunikasi dengan lapisan data melalui repositori.
Oleh karena itu, kita perlu menambahkan instance kelas “SubscriberRepository” sebagai parameter konstruktor.
 */
class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    //  mendefinisikan dua variabel untuk "nama" dan "email" . Variabel ini akan digunakan untuk dua EditText di activity_main.xml.
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    //Kami menambahkan dua tombol ke tata letak xml. Kami akan menggunakan salah satunya untuk menyimpan dan memperbarui data. Dan, kami akan menggunakan tombol lain untuk menghapus semua dan menghapus data. Jadi, mari kita definisikan dua MutableLiveData untuk mereka.
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage


    //Pada awalnya, tombol harus memiliki "Simpan" dan "Hapus Semua" sebagai teksnya. Jadi, mari kita gunakan blok init untuk menginisialisasinya.
    init {
        saveOrUpdateButtonText.value = "Simpan"
        clearAllOrDeleteButtonText.value = "Hapus"
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }


    /*
    Pertama, kita akan mendapatkan nama dan email yang dimasukkan dari bidang input teks (EditTexts) . Kemudian, kita akan membuat instance Subscriber menggunakan mereka dan meneruskannya ke fungsi insertSubscriber. Setelah itu, kami akan menghapus nilai EditTexts.
     */
    fun saveOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Mohon Masukkan Nama!")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Mohon Masukkan Email!")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Masukkan email yang benar!")
        } else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                updateSubscriber(subscriberToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insertSubscriber(Subscriber(0, name, email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }

    private fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Pelanggan berhasil ditambahkan! $newRowId")
        } else {
            statusMessage.value = Event("Terjadi kesalahan!")
        }
    }


    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRows = repository.update(subscriber)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Simpan"
            clearAllOrDeleteButtonText.value = "Hapus"
            statusMessage.value = Event("$noOfRows Berhasil diperbarui!")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    /*
    Pertama-tama, kita perlu membuka kelas ViewModel dan membuat fungsi untuk mendapatkan daftar data yang disimpan. Di antarmuka DAO
     */
    fun getSavedSubscribers() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteSubscriber(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(subscriber)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Berhasil dihapus!")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Berhasil dihapus!")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
}