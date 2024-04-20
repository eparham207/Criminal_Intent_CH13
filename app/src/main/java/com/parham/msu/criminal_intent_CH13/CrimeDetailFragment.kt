package com.parham.msu.criminal_intent_CH13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.parham.msu.criminal_intent_CH13.databinding.FragmentCrimeDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class CrimeDetailFragment : Fragment() {

    private val args: CrimeDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }

    private var _binding: FragmentCrimeDetailBinding? = null


    private val binding get() = _binding!!


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

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.crimeTitle.text.isBlank()) {
                    binding.crimeTitle.error = "Please provide a title for the crime"
                } else {
                    findNavController().navigateUp()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

            binding.apply {

                //listener for edit text
                crimeTitle.doOnTextChanged { text, _, _, _ ->
                    crimeDetailViewModel.updateCrime { oldCrime ->
                        oldCrime.copy(title = text.toString())
                    }
                }
                crimeDate.apply {
                    isEnabled = false
                }

                //listener for textbox changes
                crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                    crimeDetailViewModel.updateCrime { oldCrime ->
                        oldCrime.copy(isSolved = isChecked) }
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