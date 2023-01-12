package com.pinhascorp.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {

    var currentIndex = 0

    private val questionBank = listOf(Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true))

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val checkUserAnswer: Boolean
        get() = questionBank[currentIndex].userGiveAnswer

    val getListSize: Int
        get() = questionBank.size

    fun moveToNext(){
        currentIndex = (currentIndex + 1).mod(questionBank.size)
    }

    fun moveToPrevious(){
        currentIndex = (currentIndex - 1).mod(questionBank.size)
    }

    fun setUserAnswer(userGiveAnswer: Boolean){
        questionBank[currentIndex].userGiveAnswer = userGiveAnswer
    }

    fun resetAnswers(){
        currentIndex = 0
        for (i in questionBank.indices)
            questionBank[i].userGiveAnswer = false
    }
}