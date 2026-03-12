package com.example.examtracker.DB.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examtracker.DB.Tables.ExamFiles
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun addExamFile(examFiles: ExamFiles)

    @Query("select * from examfiles where examId=:examid")
    fun getexamfiles(examid: Int): Flow<List<ExamFiles>>


}