package com.a203110017.annisa_kinanti_uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.a203110017.annisa_kinanti_uts.databinding.ActivityMainBinding
import com.a203110017.annisa_kinanti_uts.db.Subscriber
import com.a203110017.annisa_kinanti_uts.db.SubscriberDatabase
import com.a203110017.annisa_kinanti_uts.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        initRecyclerView()
    }

    // fungsi untuk menginisialisasi RecyclerView
    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({ selectedItem: Subscriber -> listItemClicked(selectedItem) })
        binding.subscriberRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    // menetapkannya ke RecyclerView menggunakan pengikatan data. Selanjutnya, kita akan membuat fungsi lain untuk menampilkan daftar.
    private fun displaySubscribersList() {
        subscriberViewModel.getSavedSubscribers().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    /*
    menampilkan daftar Pelanggan di RecyclerView. Mari kita mulai dengan mendefinisikan variabel referensi untuk kelas adaptor.
    private lateinit var adapter : MyRecyclerViewAdapter Kemudian, kita akan membuat fungsi untuk pendengar klik.
    Untuk saat ini, kami akan menampilkan pesan toast. Itu akan menunjukkan nama pelanggan yang dipilih.
     */
    private fun listItemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}