package com.parham.msu.criminal_intent_CH13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.text.format.DateFormat
import androidx.recyclerview.widget.RecyclerView
import com.parham.msu.criminal_intent_CH13.databinding.ListItemCrimeBinding
import com.parham.msu.criminal_intent_CH13.databinding.ListItemCrimePoliceBinding

class CrimeHolder(
    val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime, onCrimeClicked: () -> Unit) {
        binding.crimeTitle.text = crime.title
        val formattedDate = DateFormat.format("MMM dd, yyyy", crime.date)
        binding.crimeDate.text = formattedDate.toString()

        binding.root.setOnClickListener {
            /*Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()*/
            onCrimeClicked()
        }
        binding.crimeSolvedd.visibility = if(crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}


class CrimeListAdapter(
    private val crimes: List<Crime>,
    private val onCrimeClicked: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //Test New Code
    companion object {
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_POLICE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
                CrimeHolder(binding)
            }
            VIEW_TYPE_POLICE -> {
                val binding = ListItemCrimePoliceBinding.inflate(inflater, parent, false)
                CrimePoliceHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        when (holder) {
            is CrimeHolder -> holder.bind(crime, onCrimeClicked)
            is CrimePoliceHolder -> holder.bind(crime)
        }
    }

    override fun getItemCount() = crimes.size

    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]
        //return if (crime.requiresPolice) VIEW_TYPE_POLICE else VIEW_TYPE_NORMAL
        return VIEW_TYPE_NORMAL
    }
}
    /*override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
        return CrimeHolder(binding)
    }
    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
       *//* holder.apply {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
        }*//*
        holder.bind(crime)
    }

    override fun getItemCount() = crimes.size*/
