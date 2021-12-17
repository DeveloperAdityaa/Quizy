package com.example.quizy.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizy.R
import com.example.quizy.activities.QuestionActivity
import com.example.quizy.adapters.QuizAdapter.*
import com.example.quizy.models.Quiz
import com.example.quizy.utils.ColorPicker
import com.example.quizy.utils.IconPicker

class QuizAdapter(val context: Context, val quizes: List<Quiz>) :
    RecyclerView.Adapter<QuizViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_item, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTitle.text = quizes[position].title
        holder.cardQuiz.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.imageIcon.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE", quizes[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizes.size
    }

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.quizTitle)
        var imageIcon: ImageView = itemView.findViewById(R.id.quizIcon)
        var cardQuiz: CardView = itemView.findViewById(R.id.quizCard)

    }
}