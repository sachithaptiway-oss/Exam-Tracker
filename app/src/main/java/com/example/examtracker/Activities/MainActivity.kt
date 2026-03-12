package com.example.examtracker.Activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examtracker.Adapters.ExamAdapter
import com.example.examtracker.MvvM.App
import com.example.examtracker.MvvM.MainViewModel
import com.example.examtracker.R
import com.example.examtracker.Utility.PanicResult
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    var examrecyclerview: RecyclerView?=null
    var examAdapter: ExamAdapter?=null
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // setting recyclerview
        examrecyclerview=findViewById(R.id.rvExams)
        examAdapter= ExamAdapter(this@MainActivity,emptyList())
        examrecyclerview?.layoutManager= LinearLayoutManager(this)
        examrecyclerview?.adapter=examAdapter

        findViewById<ExtendedFloatingActionButton>(R.id.fabAddExam).setOnClickListener {
            startActivity(Intent(this@MainActivity, AddExamActivity::class.java))
        }

        //initialize viewmodel before accessing it
        val app= App()
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this@MainActivity.application)
        ).get(MainViewModel::class.java)      //  mainViewModel= MainViewModel(this@MainActivity.application)

        mainViewModel.getExamsList()


        observe()


    }

    private fun observe() {
        mainViewModel.examlist.observe(this@MainActivity){
            data->examAdapter?.updatelist(data)?:emptyList<PanicResult>()
        }
    }
}