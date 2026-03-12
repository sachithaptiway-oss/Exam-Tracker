package com.example.examtracker.DB

import android.app.Application
import android.content.Context
import com.example.examtracker.DB.Tables.Exam
import com.example.examtracker.DB.Tables.ExamFiles
import kotlinx.coroutines.flow.Flow

class MainRepository(context: Context) {
    val db: DataBaseClass by   lazy {
        DataBaseClass.getinstance(context)
    }

    fun getAllExams(): Flow<List<Exam>>{
        return db.examdao().getAllExams()
    }
    fun getExamFiles(examid: Int): Flow<List<ExamFiles>>{
        return db.documentdao().getexamfiles(examid)
    }

}