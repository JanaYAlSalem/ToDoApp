package com.example.todoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.ToDoViewModel
import com.example.todoapp.databinding.FragmentItemListBinding
import com.example.todoapp.R


class ItemListFragment : Fragment() {

    /*
 * Binding object instance corresponding to the fragment_list.xml layout
 * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
 * when the view hierarchy is attached to the fragment.
 */
    private var binding: FragmentItemListBinding? = null

    /*
     * create a reference object with ToDoViewModel type
     * the reference object from activityViewModels
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
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding!!.root
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
            itemListFragment = this@ItemListFragment
        }
//            var currentPos=0
//            arguments?.let {
//                currentPos=it?.getInt("id")
//            }
//            sharedViewModel.deleteTask(currentPos)
//            //  if(binding?.indexTask?.text.toString() == sharedViewModel.FindTaskIndex(todolist)

    }

    /**
     * Navigate to the next screen Add Task.
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_listFragment_to_addTaskFragment)
    }

    /**
     * DestroyView
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}