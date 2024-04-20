package com.parham.msu.criminal_intent_CH13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.parham.msu.criminal_intent_CH13.databinding.FragmentCrimeDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

//private const val TAG = "CrimeDetailFragment"
class CrimeDetailFragment : Fragment() {

    private lateinit var crimeListViewModel: CrimeListViewModel
    //lateinit var crime: Crime

    private val args: CrimeDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }

    //private lateinit var binding: FragmentCrimeDetailBinding
    private var _binding: FragmentCrimeDetailBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible"
        }

    /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crimeListViewModel =
            ViewModelProvider(requireActivity()).get(CrimeListViewModel::class.java)


        crime = Crime(
            id = UUID.randomUUID(),
            title = "",
            date = Date(),
            isSolved = false,
            //requiresPolice = false
        )
        //Log.d(TAG, "The crime ID is: ${args.crimeId}")
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCrimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    //wiring up views in a fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contactPoliceButton.setOnClickListener {
            // When the "Contact Police" button is clicked, mark the crime as requiring police intervention
            //crime.requiresPolice = true

            binding.apply {

                //listener for edit text
                crimeTitle.doOnTextChanged { text, _, _, _ ->
                    //crime = crime.copy(title = text.toString())
                }
                //listener for button

                crimeDate.apply {
                    // text = crime.date.toString()
                    isEnabled = false
                }

                //listener for textbox changes
                crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                    //crime = crime.copy(isSolved = isChecked)
                }

            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    crimeDetailViewModel.crime.collect { crime ->
                        crime?.let { updateUi(it) }
                    }
                }
            }

        }

    }

    private fun updateUi(crime: Crime) {
        binding.apply {
            if (crimeTitle.text.toString() != crime.title) {
                crimeTitle.setText(crime.title)
            }
            crimeDate.text = crime.date.toString()
            crimeSolved.isChecked = crime.isSolved
        }
    }



        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }