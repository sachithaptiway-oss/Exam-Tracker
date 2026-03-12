package com.example.examtracker.DB

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examtracker.DB.Dao.DocumentDao
import com.example.examtracker.DB.Dao.ExamDao
import com.example.examtracker.DB.Tables.Exam
import com.example.examtracker.DB.Tables.ExamFiles

@Database(entities = [Exam::class, ExamFiles::class], version = 1)
abstract class DataBaseClass : RoomDatabase(){

   abstract fun examdao(): ExamDao
    abstract fun documentdao(): DocumentDao

    companion object{
        @Volatile
        var instance: DataBaseClass?=null
        fun getinstance(context: Context): DataBaseClass{
            return instance?:synchronized(this){
                instance?: Room.databaseBuilder(context.applicationContext, DataBaseClass::class.java,"DataBase1")
                    .allowMainThreadQueries()
                    .build().also { instance=it }
            }
        }
    }
}