package com.pinhascorp.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val KEY_INDEX = "index"
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var resetButton: Button

    private var rightAnswers = 0
    private var sumAnswers = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.nextButton)
        previousButton = findViewById(R.id.previousButton)
        resetButton = findViewById(R.id.resetButton)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {_: View ->
            if (!quizViewModel.checkUserAnswer){
                checkAnswer(true)
                updateQuestion()
            }
        }

        falseButton.setOnClickListener { _:View->
            if (!quizViewModel.checkUserAnswer) {
                checkAnswer(false)
                updateQuestion()
            }
        }

        nextButton.setOnClickListener { _: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        resetButton.setOnClickListener {
            resetState()
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        getResultOfAnswers()
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId: Int
        if (userAnswer == correctAnswer){
            messageResId = R.string.correct_toast
            rightAnswers++
            sumAnswers++
        }
        else {
            messageResId = R.string.incorrect_toast
            sumAnswers++
        }
        quizViewModel.setUserAnswer(true)
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun getResultOfAnswers(){
        if (sumAnswers == quizViewModel.getListSize)
            Toast.makeText(this,
                "Your result is: ${rightAnswers * 100/quizViewModel.getListSize}%",
                Toast.LENGTH_SHORT).show()
    }

    private fun resetState(){
        quizViewModel.resetAnswers()
        rightAnswers = 0
        sumAnswers = 0
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"onStateInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
}