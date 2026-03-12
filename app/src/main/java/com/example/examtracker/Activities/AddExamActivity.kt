package com.example.examtracker.Activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.examtracker.DB.DataBaseClass
import com.example.examtracker.DB.Tables.Exam
import com.example.examtracker.MvvM.MainViewModel
import com.example.examtracker.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddExamActivity : AppCompatActivity() {

var mainViewModel: MainViewModel?=null

var examnameview: EditText?=null
    var difficultylabelview: TextView?=null
    var preparednesslabelview: TextView?=null
    var ratingdifficultyview: RatingBar?=null
    var ratingpreparednessview: RatingBar?=null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_exam)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mainViewModel = ViewModelProvider(this
            , ViewModelProvider.AndroidViewModelFactory.getInstance(this@AddExamActivity.application)).get(
            MainViewModel::class.java)


         difficultylabelview=findViewById<TextView>(R.id.tvDifficultyLabel)
         preparednesslabelview=findViewById<TextView>(R.id.tvPreparednessLabel)
         examnameview=findViewById<EditText>(R.id.etExamName)
         ratingdifficultyview =findViewById<RatingBar>(R.id.ratingDifficulty)
         ratingpreparednessview = findViewById<RatingBar>(R.id.ratingPreparedness)

        //rating
        ratingdifficultyview?.setOnRatingBarChangeListener { _, rating, _ ->
            difficultylabelview?.text = when (rating) {
                1f -> "Very Easy"
                2f -> "Easy"
                3f -> "Moderate"
                4f -> "Hard"
                5f -> "Very Hard"
                else -> ""
            }
        }
        ratingpreparednessview?.setOnRatingBarChangeListener{_,rating, fromUser ->
            preparednesslabelview?.text= when(rating){
                1f -> "Just began"
                2f -> "Moderate"
                3f -> "Sufficient"
                4f -> "Good"
                5f -> "Ace"
                else -> ""            }
        }

        observe()

        findViewById<Button>(R.id.btnSaveExam).setOnClickListener {
            val difficulty=ratingdifficultyview?.rating
            val preparedness = ratingpreparednessview?.rating
            mainViewModel?.validateAddExam(examnameview?.text.toString(), LocalDate.now().toString(),difficulty?.toInt()?:0,preparedness?.toInt()?:0)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observe() {
        mainViewModel?.isValid?.observe(this){
            isvalid->{
                if (isvalid){
                   addExam()
                }
            }
        }
        mainViewModel?.nameError?.observe(this){error->examnameview?.error=error}
        mainViewModel?.difficultyError?.observe(this){error-> Snackbar.make(findViewById(android.R.id.content),error,
            Snackbar.LENGTH_SHORT).show() }
        mainViewModel?.preparationError?.observe(this){
            error->
            Snackbar.make(findViewById(android.R.id.content),error, Snackbar.LENGTH_SHORT).show()
        }
        mainViewModel?.difficultyError?.observe(this){
            error->
            Snackbar.make(findViewById(android.R.id.content),error, Snackbar.LENGTH_SHORT).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addExam(){
        lifecycleScope.launch {
            try {
                DataBaseClass.getinstance(this@AddExamActivity.application).examdao().insertExam(Exam(name = examnameview?.text.toString(), date = LocalDate.now().toString(), preparation = ratingpreparednessview?.rating?.toInt()?:0, difficulty = ratingdifficultyview?.rating?.toInt()?:0))
                finish()

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}