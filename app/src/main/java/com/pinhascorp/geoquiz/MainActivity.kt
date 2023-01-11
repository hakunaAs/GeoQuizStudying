package com.pinhascorp.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true))

    private var currentIndex = 0
    private var rightAnswers = 0
    private var sumAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.nextButton)
        previousButton = findViewById(R.id.previousButton)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {view: View ->
            if (!questionBank[currentIndex].userGiveAnswer){
                checkAnswer(true)
                currentIndex = (currentIndex + 1) % questionBank.size
                updateQuestion()
            }
        }

        falseButton.setOnClickListener { view:View->
            if (!questionBank[currentIndex].userGiveAnswer) {
                checkAnswer(false)
                currentIndex = (currentIndex + 1) % questionBank.size
                updateQuestion()
            }
        }

        nextButton.setOnClickListener { view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        previousButton.setOnClickListener {
            currentIndex = (currentIndex - 1).mod(questionBank.size)
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        getResultOfAnswers()
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
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
        questionBank[currentIndex].userGiveAnswer = true
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun getResultOfAnswers(){
        if (sumAnswers == questionBank.size)
            Toast.makeText(this, "Your result is: ${rightAnswers * 100/questionBank.size}%",
                Toast.LENGTH_SHORT).show()
    }
}