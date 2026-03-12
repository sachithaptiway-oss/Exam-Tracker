package com.example.examtracker.Adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examtracker.DB.Tables.ExamFiles

class ExamFilesAdapter(val context: Context,var examfileslist:List<ExamFiles>): RecyclerView.Adapter<ExamFilesAdapter.viewholer> (){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewholer {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: viewholer,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    fun updateexamfileslist(updatedlist: List<ExamFiles>){
        examfileslist=updatedlist
    }

    class viewholer(itemview: View): RecyclerView.ViewHolder(itemview){

    }
}