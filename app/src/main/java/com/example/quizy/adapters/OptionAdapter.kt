package com.example.quizy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizy.R
import com.example.quizy.models.Question
import com.google.api.Distribution
import java.security.cert.PKIXRevocationChecker

class OptionAdapter(val context : Context, val question: Question) :
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {


    private var options = listOf<String>(question.option1, question.option2, question.option3, question.option4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.txtOption.text = options[position]
        holder.optionContainer.setOnClickListener{
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }

        if(question.userAnswer == options[position]){
            holder.optionContainer.setBackgroundResource(R.drawable.option_item_selected_background)
        }
        else{
            holder.optionContainer.setBackgroundResource(R.drawable.option_item_background)
        }

    }

    override fun getItemCount(): Int {
        return options.size
    }

    inner class OptionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtOption : TextView = itemView.findViewById(R.id.txtOption)
        val optionContainer : LinearLayout = itemView.findViewById(R.id.option_container)
    }
}