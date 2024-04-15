package com.parham.msu.criminal_intent_CH13

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.parham.msu.criminal_intent_CH13.databinding.ListItemCrimePoliceBinding
import android.text.format.DateFormat
import java.util.*

class CrimePoliceHolder(private val binding: ListItemCrimePoliceBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(crime: Crime) {
        binding.apply {
            crimeTitle.text = crime.title
            //crimeDate.text = SimpleDateFormat("EEE MMM dd HH:mm z yyyy", Locale.getDefault()).format(crime.date)
            val formattedDate = DateFormat.format("MMM dd, yyyy", crime.date)
            binding.crimeDate.text = formattedDate.toString()
            root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "Serious crime ${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            contactPoliceButton.setOnClickListener {
                // Handle button click event for contacting the police
                // For example, you can launch a new activity or show a dialog
            }
        }
        binding.crimeSolveddd.visibility = if(crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}