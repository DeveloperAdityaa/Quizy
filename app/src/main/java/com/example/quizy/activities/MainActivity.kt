package com.example.quizy.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizy.R
import com.example.quizy.adapters.QuizAdapter
import com.example.quizy.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var quizAdapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()

    }

//    private fun populateDummyData() {
//        quizList.add(Quiz("12-10-2021", "12-10-2021"))
//        quizList.add(Quiz("13-10-2021", "13-10-2021"))
//        quizList.add(Quiz("14-10-2021", "14-10-2021"))
//        quizList.add(Quiz("15-10-2021", "15-10-2021"))
//        quizList.add(Quiz("16-10-2021", "16-10-2021"))
//        quizList.add(Quiz("17-10-2021", "17-10-2021"))
//    }

    private fun setUpViews() {
        setUpFirestore()
        setUpRecyclerView()
        setUpDrawer()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {

        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                val dateFormater = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormater.format(Date(it))
                intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(this, "Datepicker is canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            quizAdapter.notifyDataSetChanged()

        }
    }

    private fun setUpRecyclerView() {

        quizAdapter = QuizAdapter(this, quizList)
        quizRecycler.layoutManager = GridLayoutManager(this, 2)
        quizRecycler.adapter = quizAdapter

    }

    private fun setUpDrawer() {
        setSupportActionBar(topAppBar)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

