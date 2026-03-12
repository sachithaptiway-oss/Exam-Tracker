package com.example.examtracker.MvvM

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.examtracker.DB.Tables.Exam
import com.example.examtracker.DB.Tables.ExamFiles
import com.example.examtracker.DB.MainRepository
import com.example.examtracker.Utility.PanicResult
import com.example.examtracker.Utility.PanicScoreCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository1 = MainRepository(application.applicationContext)

    //Exam list
    private var _examlist = MutableStateFlow<List<PanicResult>>(emptyList())
    val examlist: LiveData<List<PanicResult>> = _examlist.asLiveData()

    //validate varaibles
    private  var _nameerror= MutableLiveData<String>()
    private var _dateError = MutableLiveData<String>()
    private var _difficulty = MutableLiveData<String>()
    private var _preparation = MutableLiveData<String>()
    private var _isValid = MutableLiveData<Boolean>()

    val nameError: LiveData<String> = _nameerror
    val dateError: LiveData<String> = _dateError
    val difficultyError: LiveData<String> = _difficulty
    val  preparationError: LiveData<String> = _preparation
    val isValid: LiveData<Boolean> = _isValid

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var _documents = MutableStateFlow<List<ExamFiles>>(emptyList())
    val documents: LiveData<List<ExamFiles>> = _documents.asLiveData()

    fun getExamsList() {
        viewModelScope.launch {
            repository1.getAllExams()
                .catch { e -> _error.value = e.message ?: "Unknown error" }
                .collect { exams ->
                    _examlist.value = exams.map { exam ->
                        PanicScoreCalculator.calculate(exam)
                    }
                }
        }
    }

    fun getExamFiles(examId: Int) {
        viewModelScope.launch {
            repository1.getExamFiles(examId)
                .catch { e -> _error.value = e.message ?: "Unknown error" }
                .collect { files ->
                    _documents.value = files
                }
        }
    }

    fun validateAddExam(name: String,date: String,difficulty: Int,preparation: Int){
        var valid = true
        if (name.isEmpty()){
            _nameerror.value = "Please enter exam "
            valid=false
        }
        if (date.isEmpty()){
            _dateError.value = "Please select a date"
            valid=false
        }
        if (difficulty.toString().isEmpty()){
            _difficulty.value="Please select difficulty level"
            valid=false
        }
        if (preparation.toString().isEmpty()){
            _preparation.value = "Please select preparation level"
            valid=false
        }
        _isValid.value=valid

    }
}