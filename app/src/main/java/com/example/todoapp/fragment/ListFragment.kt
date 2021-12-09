package com.example.todoapp.fragment

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.adapter.ItemOfTaskAdapter
import com.example.todoapp.R
import com.example.todoapp.data.Datasource
import com.example.todoapp.data.ToDoViewModel
import com.example.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    /*
 * Binding object instance corresponding to the fragment_list.xml layout
 * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
 * when the view hierarchy is attached to the fragment.
 */
    private var binding: FragmentListBinding? = null

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

        binding = FragmentListBinding.inflate(inflater, container, false)
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
        var note = ItemOfTaskAdapter(this.requireContext(),Datasource().loadToDo()) // TodoList

        binding!!.apply {
            recyclerviewOfTask.adapter = note
            recyclerviewOfTask.setHasFixedSize(true)

            lifecycleOwner = viewLifecycleOwner
            toDoViewModel = sharedViewModel
            listFragment = this@ListFragment
        }

    }

    /*
    * who to view the list
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.SortByTitle -> {
                var note = ItemOfTaskAdapter(this.requireContext(),Datasource().loadToDo()) // TodoList

                binding!!.apply {
                    recyclerviewOfTask.adapter = note
                    recyclerviewOfTask.setHasFixedSize(true)

                    lifecycleOwner = viewLifecycleOwner
                    toDoViewModel = sharedViewModel
                    listFragment = this@ListFragment
                }
                return true
            }
            R.id.NewTask -> {
                return true
            }
            R.id.OldDate -> {
                //change avt
                return true
            }
            else -> return super.onOptionsItemSelected(item)


        }

    }


    /**
     * Navigate to the next screen Add Task.
     */
    fun goToNextScreen() {

        findNavController().navigate(R.id.action_listFragment_to_addTaskFragment)
    }




    fun Search(){

        sharedViewModel.ClearrSearch()
        var SearchWord = binding!!.textsearch.text.toString()
        sharedViewModel.SearchAdded(SearchWord)
        findNavController().navigate(R.id.action_listFragment_to_searchFragment)


    }

    /**
     * DestroyView
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}