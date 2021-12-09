package com.example.todoapp.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.adapter.ItemOfTaskAdapter
import com.example.todoapp.adapter.SearchAdapter
import com.example.todoapp.data.Datasource
import com.example.todoapp.data.ToDoViewModel
import com.example.todoapp.databinding.FragmentSearchBinding
import com.example.todoapp.R


class SearchFragment : Fragment() {

    /*
 * Binding object instance corresponding to the fragment_list.xml layout
 * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
 * when the view hierarchy is attached to the fragment.
 */
    private var binding: FragmentSearchBinding? = null

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

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    /*
   * the third fun of Fragment -> onViewCreated()
   * call the functions to display on UI
   * Update the UI
   */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // var note = ItemOfTaskAdapter(Datasource().AllTask) // TodoList
        var notesearch = SearchAdapter(this.requireContext(), Datasource().Search()) // TodoList

        binding!!.apply {
            recyclerviewOfTask.adapter = notesearch
            recyclerviewOfTask.setHasFixedSize(true)

            lifecycleOwner = viewLifecycleOwner
            toDoViewModel = sharedViewModel
            searchFragment = this@SearchFragment
        }

    }

    /**
     * Navigate to the next screen Add Task.
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_searchFragment_to_addTaskFragment)
    }

    fun Search () {
        //  findNavController().navigate(R.id.action_searchFragment_self)
        sharedViewModel.ClearrSearch()
        var SearchWord = binding!!.textsearch.text.toString()
        sharedViewModel.SearchAdded(SearchWord)
        findNavController().navigate(R.id.action_searchFragment_self)

    }

    /**
     * DestroyView
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}