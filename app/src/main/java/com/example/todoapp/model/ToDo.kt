package com.example.todoapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class ToDo(
//    val id: Int,
    var title: String,
    val description: String,
    val Date: String,
    val State: Boolean
)