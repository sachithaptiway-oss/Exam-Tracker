package com.example.examtracker.Utility

import com.example.examtracker.DB.Tables.Exam

object PanicScoreCalculator {

    fun calculate(exam: Exam): PanicResult {

        try {
            val daysLeft = ((exam.date.toInt() - System.currentTimeMillis())
                    / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(1)

            // difficulty + preparedness come directly from DB
            val urgency = (10 / daysLeft) * exam.difficulty
            val gap = (exam.difficulty - exam.preparation).coerceAtLeast(0)
            val score = ((urgency + gap * 2) * 10).coerceIn(0, 100)

            return PanicResult(
                score = score,
                daysLeft = daysLeft,
                label = when (score) {
                    in 0..30   -> "🟢 Chill, you're fine"
                    in 31..60  -> "🟡 Start studying soon"
                    else       -> "🔴 PANIC MODE 🚨"
                }, exam = exam
            )
        }catch (e: Exception){
            return PanicResult(score = 0, daysLeft = 0, label = "empty",exam)
        }

        // daysLeft is calculated fresh every time from stored timestamp


    }
}

data class PanicResult(
    val score: Int,
    val daysLeft: Int,
    val label: String,val exam: Exam
)