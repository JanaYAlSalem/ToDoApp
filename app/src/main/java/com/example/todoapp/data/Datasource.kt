package com.example.todoapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todoapp.model.ToDo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Datasource {

    fun loadToDo(): List<ToDo> {
        return AllTask
    } // end fun

    fun Search(): List<ToDo>{
        return SearchList
    }

}


var AllTask = mutableListOf<ToDo>()
var TitleList  = mutableListOf<String>()
var SearchList  = mutableListOf<ToDo>()