package com.jspadda.contactsmanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jspadda.contactsmanager.R
import com.jspadda.contactsmanager.room.UserDatabase
import com.jspadda.contactsmanager.repository.UserRepository
import com.jspadda.contactsmanager.viewmodel.UserVM
import com.jspadda.contactsmanager.viewmodel.UsersVMFactory
import com.jspadda.contactsmanager.adapters.MyRecyclerViewAdapter
import com.jspadda.contactsmanager.databinding.ActivityMainBinding
import com.jspadda.contactsmanager.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = UserDatabase.getInstance(application).userDAO
        val repository = UserRepository(dao)
        val factory = UsersVMFactory(repository)

        userViewModel = ViewModelProvider(this, factory).get(UserVM::class.java)

        binding.userViewModel = userViewModel

        binding.lifecycleOwner = this

        initRecyclerView()

        }
    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayUserList()
    }

    private fun DisplayUserList() {
        userViewModel.users.observe(this, Observer {
            binding.recyclerView.adapter = MyRecyclerViewAdapter(
                it, {selectedItem: User -> listItemClicked(selectedItem)}
            )
        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(this,
            "Selected name is ${selectedItem.name}",
            Toast.LENGTH_LONG).show()

        userViewModel.initUpdateAndDelete(selectedItem)

    }
}