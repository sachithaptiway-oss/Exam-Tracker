package com.example.examtracker.DB.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exam(@PrimaryKey(autoGenerate = true) val examid: Int=0, val name: String, val date: String, var difficulty: Int =0, var preparation:Int=0)