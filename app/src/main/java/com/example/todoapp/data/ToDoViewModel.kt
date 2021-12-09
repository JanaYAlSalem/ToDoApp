package com.example.todoapp.data


import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.model.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ToDoViewModel(application: Application) : AndroidViewModel(application) {


    // private lateinit var AddTask:Datasource

    private val _id = MutableLiveData<Int>()
    val Id_index: LiveData<Int> = _id

    private val _Title = MutableLiveData<String>()
    val Title: LiveData<String> = _Title

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _DateOfTask = MutableLiveData<String>()
    val DateOfTask: LiveData<String> = _DateOfTask

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state
    val compareDate = MutableLiveData<Long>()

    private val _List = MutableLiveData<List<ToDo>>()
    val List: LiveData<List<ToDo>> = _List


    fun addnewTask(TodoList: ToDo) = AllTask.add(TodoList)

    fun editeTask(id: Int, TodoList: ToDo) = AllTask.set(id, TodoList)

    // list to use on search
    fun addTitleToList(Title:String) = TitleList.add(Title)

    fun editeTitleToList(id: Int, Title:String) = TitleList.set(id, Title)

    fun SearchAdded (SearchWord : String) {
        for (itemFor in TitleList)
            if(SearchWord.equals(itemFor, ignoreCase = true)){
                SearchList.add(AllTask.get(TitleList.indexOf(itemFor))) //addItem(elemnt(index)) // TitleList.indexOf(itemFor) -> 0
                // AllTask.get(0) -> ToDo(titlr,dis,date,state)
                // SearchList.add()
                //  SearchList.add(ToDo(titlr,dis,date,state))
            }

    }
    fun ClearrSearch () = SearchList.removeAll(SearchList)

    fun removeItem() {


    }

    fun setDate(myDate: String) {
        _DateOfTask.value = myDate

    }

    fun updateTaskData(pos: Int) { // send index to get element

        var item = AllTask.get(pos)

        _id.value = pos
        _Title.value = item.title
        _description.value = item.description
        _DateOfTask.value = item.Date
        _state.value = item.State
    }

    fun IsPAST(date: String): Boolean {
        var today = Date()
        Log.d("TAG","befor $date")
        val format = SimpleDateFormat("dd/MM/yyyy",Locale.UK)
        var taskDate = format.parse("10/05/2022")
        Log.d("TAG","after ${taskDate}")
        return taskDate.before(today)
        Log.d("TAG","fun is  ${taskDate.before(today)}")
    }



    fun ResultSearch(SearchWord : String) {
//
//        var item = AllTask.indexOf() // index to get element
//
//     if(SearchWord.equals(item.title, ignoreCase = true)){
//         SearchList.add(item)
//     }

    }


    // recive the long vsriablr
    fun setCompareDate(num: Long){
        compareDate.value = num
    }

}