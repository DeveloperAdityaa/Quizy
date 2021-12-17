package com.example.quizy.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizy.R
import com.example.quizy.adapters.OptionAdapter
import com.example.quizy.models.Question
import com.example.quizy.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    lateinit var optionAdapter: OptionAdapter
    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = (1..5).random()
    var questionNumber = 1
    lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)


        setUpFirestore()
        setUpListener()
    }

    private fun setUpListener() {
//        btnPrevious.setOnClickListener {
//            index--
//            bindViews()
//        }
        btnNext.setOnClickListener {
            timer.cancel()
            index = (1..questions!!.size).random()
            questionNumber++
            bindViews()
        }

        btnSubmit.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            finish()
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        val date: String? = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {

        btnNext.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        if (questionNumber == 1) { // for first question
            btnNext.visibility = View.VISIBLE
        } else if (questionNumber == questions!!.size) { //for last question
            btnSubmit.visibility = View.VISIBLE
        } else {// for middle question
            btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]

        question?.let {
            txtQuestion.text = it.description
            optionAdapter = OptionAdapter(this, it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }

        startTimer()

    }

    private fun startTimer() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtTimer.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                Toast.makeText(this@QuestionActivity, "Time is up!", Toast.LENGTH_SHORT).show()
                index = (1..questions!!.size).random()
                questionNumber++
                bindViews()
            }
        }
        timer.start()
    }
}