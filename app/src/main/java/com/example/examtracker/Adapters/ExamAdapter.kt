package com.example.examtracker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examtracker.DB.Tables.Exam
import com.example.examtracker.R
import com.example.examtracker.Utility.PanicResult

class ExamAdapter(val context: Context,var examlist:List<PanicResult>): RecyclerView.Adapter<ExamAdapter.viewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewholder {
       val view= LayoutInflater.from(context).inflate(R.layout.examlist_layout,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(
        holder: viewholder,
        position: Int
    ) {
        val exam= examlist.get(position)

        holder.exanameview.text=examlist.get(position).exam.name
        holder.examdateview.text = examlist.get(position).exam.date
    }

    override fun getItemCount(): Int {
        return examlist.size
    }

    fun updatelist(updatedlist:List<PanicResult>){
        examlist=updatedlist
        notifyDataSetChanged()
    }

    class viewholder(itemview: View): RecyclerView.ViewHolder(itemview){

        val exanameview= itemview.findViewById<TextView>(R.id.tvExamName)
        val examdateview=itemview.findViewById<TextView>(R.id.tvExamDate)
        val daysleftview=itemview.findViewById<TextView>(R.id.tvDaysLeft)

    }
}