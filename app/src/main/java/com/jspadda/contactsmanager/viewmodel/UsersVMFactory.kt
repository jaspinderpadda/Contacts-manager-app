package com.jspadda.contactsmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jspadda.contactsmanager.repository.UserRepository

class UsersVMFactory(private val repository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserVM::class.java)) {
            return UserVM(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}