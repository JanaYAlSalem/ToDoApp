package com.example.todoapp.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.*
import com.example.todoapp.data.AllTask
import com.example.todoapp.fragment.ListFragmentDirections
import com.example.todoapp.model.ToDo
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemOfTaskAdapter (private val contex: Context,private val dataset: List<ToDo>): RecyclerView.Adapter<ItemOfTaskAdapter.ItemViewHolder>() {

    // sup class of ItemAdapter
    class ItemViewHolder(  view: View) : RecyclerView.ViewHolder(view) {

        val index_Adapter: TextView = view.findViewById(R.id.index_task)
        val title_Adapter: TextView = view.findViewById(R.id.titleOfTask)
        val Date_Adapter : TextView = view.findViewById(R.id.DateOfTask)
        val State_Adapter: CheckBox = view.findViewById(R.id.State)
        val ed_Adapter : ImageButton = view.findViewById(R.id.imageButton)
        val delete_Adapter : ImageButton = view.findViewById(R.id.delete_button)
        //val SortByTitle_Adapter : menu = view.findViewById(R.id.SortByTitle)




    } // end ItemViewHolder :in line on ItemAdapter class


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemOfTaskAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemOfTaskAdapter.ItemViewHolder, position: Int) {

        val item = dataset[position]
        holder.index_Adapter.text = position.inc().toString()
        holder.title_Adapter.text = item.title
        holder.Date_Adapter.text = item.Date.toString()
        holder.State_Adapter.isChecked = item.State


        // icon edit on list fragment
        holder.ed_Adapter.setOnClickListener {
            var action = ListFragmentDirections.actionListFragmentToUpdateTaskFragment(position)
            holder.itemView.findNavController().navigate(action)
        }

        // icon delete on list fragment
        holder.delete_Adapter.setOnClickListener {
            MaterialAlertDialogBuilder(contex, position)
                .setTitle("DELETE TASK")
                .setMessage("Are you Sure to delete")
//                .setNegativeButton("No", null)
                .setCancelable(null == false)
                .setNegativeButton(contex.getString(R.string.Yes)) { _, _ ->
                    AllTask.removeAt(position)
                    notifyDataSetChanged()
                }
                .show()
        }

        // mnue SortByTitle


        // checkBox
        holder.State_Adapter.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                holder.title_Adapter.setTextColor(contex.getColor(R.color.RED))
            } else
                holder.title_Adapter.setTextColor(contex.getColor(R.color.GRAY))

        }

    }
    override fun getItemCount(): Int {
        return dataset.size
    }

}