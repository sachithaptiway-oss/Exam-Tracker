package com.example.examtracker.DB.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examtracker.DB.Tables.Exam
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertExam(exam: Exam)

    @Query("select * from exam  order by date ")
    fun getAllExams(): Flow<List<Exam>>

    @Query("delete from exam where examid=:examId")
    suspend fun deleteExam(examId: Int)

    @Query("update exam set difficulty=:Difficulty,preparation=:Preparedness")
    suspend fun updateExam(Difficulty:Int,Preparedness: Int)
}