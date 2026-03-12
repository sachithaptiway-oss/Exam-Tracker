package com.example.examtracker.DB.Tables

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.examtracker.DB.Tables.Exam

@Entity(foreignKeys = [ForeignKey(
    parentColumns = arrayOf("examid"),
    childColumns = arrayOf("examId"),
    entity = Exam::class,
    onDelete = ForeignKey.Companion.CASCADE
)], indices = [Index("examId")])
data class ExamFiles(@PrimaryKey(autoGenerate = true) val fileid: Int, val examId: Int, val filePath: String, val filetype: String, val filename: String)