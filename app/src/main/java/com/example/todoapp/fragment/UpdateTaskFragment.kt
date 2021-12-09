package com.example.todoapp.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.AllTask
import com.example.todoapp.data.ToDoViewModel
import com.example.todoapp.databinding.FragmentUpdateTaskBinding
import com.example.todoapp.model.ToDo
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import com.example.todoapp.R



class UpdateTaskFragment : Fragment() {

    private var binding: FragmentUpdateTaskBinding? = null
    private var date = ""
    private var dateToCompare: Long = 0


    /*
     * create a reference object with ToDoViewModel type
     * the reference object from activityViewModels [NOT NOW]
     */
    private val sharedViewModel: ToDoViewModel by activityViewModels()


    /*
 * the first fun of Fragment -> onCreate()
 * Inflate the layout XML file and return a binding object instance
 */
    // is by Default is create it

    /*
     * the second fun of Fragment -> onCreateView()
     * Inflate the layout XML file and return a binding object instance
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
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
            updateTaskFragment = this@UpdateTaskFragment


        }
        var currentPos = 0
        arguments?.let {
            currentPos = it?.getInt("id")
        }
        sharedViewModel.updateTaskData(currentPos)
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
            Toast.makeText(requireContext(), "toooo Laaaaate", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(requireContext(), "Keep Going", Toast.LENGTH_SHORT).show()

    }




    fun editTask() {

        val Title = binding!!.titleOfTask.text.toString()
        val description = binding!!.descriptionOfTask.text.toString()
        val DateOfTask = binding!!.DateOfTask.text.toString()
        val StateOfTask =
            binding!!.State.isDuplicateParentStateEnabled//falseif (binding.State) { true } else {false}

        if (Title.isEmpty() == false) {
            if ( sharedViewModel.compareDate.value == null ){
                Toast.makeText(requireContext(), "Please Enter Date", Toast.LENGTH_SHORT).show()
            } else if ( sharedViewModel.compareDate.value!! < Calendar.getInstance().timeInMillis){
                Toast.makeText(requireContext(), "Date is past, Please Enter valid Date", Toast.LENGTH_SHORT).show()
            } else{
                var currentPos = 0
                arguments?.let {
                    currentPos = it?.getInt("id")
                }
                sharedViewModel.editeTask(
                    currentPos,
                    ToDo(
                        title = Title,
                        description = description,
                        Date = DateOfTask,
                        State = StateOfTask
                    )
                ) //end if parameter
                //
                sharedViewModel.editeTitleToList(currentPos,Title) // update to titlelist for using on search
                Toast.makeText(requireContext(), "Task is updated ", Toast.LENGTH_SHORT).show()
                goToNextScreen()}
        } else
            Toast.makeText(requireContext(), "Please Enter Title", Toast.LENGTH_SHORT).show()

    }


    fun goToNextScreen() {

        findNavController().navigate(R.id.action_updateTaskFragment_to_listFragment)
    }


}