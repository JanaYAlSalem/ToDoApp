package com.example.todoapp.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.Datasource
import com.example.todoapp.data.ToDoViewModel
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.model.ToDo
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {
    /*
 * Binding object instance corresponding to the fragment_list.xml layout
 * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
 * when the view hierarchy is attached to the fragment.
  */
    private var binding: FragmentAddTaskBinding? = null
    private var date = ""
    private var dateToCompare: Long = 0

    /*
     * create a reference object with ToDoViewModel type
     * the reference object from activityViewModels [NOT NOW]
     */
    private val sharedViewModel: ToDoViewModel by activityViewModels()

    private lateinit var AddTask:Datasource
    // private lateinit var ListAdd : List<ToDo>


    /*
 * the first fun of Fragment -> onCreate()
 * Inflate the layout XML file and return a binding object instance
 */
    // is by Default is create it

    /*
     * the second fun of Fragment -> onCreateView()
     * Inflate the layout XML file and return a binding object instance
     */
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding?.root
    }

    /*
 * the third fun of Fragment -> onViewCreated()
 * call the functions to display on UI
 * Update the UI
 */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            toDoViewModel = sharedViewModel
            addTaskFragment = this@AddTaskFragment
        }
    }

    fun datePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText("Select date")
                .build()
        datePicker.show(parentFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener{
            dateToCompare = it
            date = convertMillisecondsToReadableDate(it, "EEE, MMM dd ")
            sharedViewModel.setCompareDate(dateToCompare)
            sharedViewModel.setDate(date)
        }
    }

    private fun convertMillisecondsToReadableDate(dateMilliseconds: Long, datePattern: String): String{
        val format =
            SimpleDateFormat(datePattern, Locale.getDefault())
        return format.format(Date(dateMilliseconds))
    }


    fun compareDates(){
        if ( sharedViewModel.compareDate.value == null ){
            Toast.makeText(requireContext(), "Please Enter Date", Toast.LENGTH_SHORT).show()
        }
        else if ( sharedViewModel.compareDate.value!! < Calendar.getInstance().timeInMillis){
            Toast.makeText(requireContext(), "Date is past, Please Enter valid Date", Toast.LENGTH_SHORT).show()
        }
//        else
//            Toast.makeText(requireContext(), "Keep Going", Toast.LENGTH_SHORT).show()
    }

    /**
     * Navigate to the next screen Add Task.
     */
    fun goToNextScreen() {

        findNavController().navigate(R.id.action_addTaskFragment_to_listFragment)
    }

    // fun to add task
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask() {

        val Title = binding!!.titleOfTask.text.toString()
        val description = binding!!.descriptionOfTask.text.toString()
        val DateOfTask = binding!!.DateOfTask.text.toString()
        val StateOfTask =  binding!!.State.isChecked //falseif (binding!!.State ) { true } else {false}



        if (Title.isEmpty() == false) {
            if ( sharedViewModel.compareDate.value == null ){
                Toast.makeText(requireContext(), "Please Enter Date", Toast.LENGTH_SHORT).show()
            } else if ( sharedViewModel.compareDate.value!! < Calendar.getInstance().timeInMillis){
                Toast.makeText(requireContext(), "Date is past, Please Enter valid Date", Toast.LENGTH_SHORT).show()
            } else{
                sharedViewModel.addnewTask(
                    ToDo(
//                        id = sharedViewModel.get_Id(),
                        title = Title,
                        description = description,
                        Date = DateOfTask,
                        State = StateOfTask
                    )
                )

                sharedViewModel.addTitleToList(Title) // add to titlelist for using on search

                Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                goToNextScreen()}
        } else
            Toast.makeText(requireContext(), "Please Enter Title", Toast.LENGTH_SHORT).show()

    }


}