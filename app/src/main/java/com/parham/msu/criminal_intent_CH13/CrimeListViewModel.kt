package com.parham.msu.criminal_intent_CH13

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val TAG = "CrimeListViewModel"

class CrimeListViewModel : ViewModel(){
    private val crimeRepository = CrimeRepository.get()

    val crimes = mutableListOf<Crime>()

    init {
        Log.d(TAG, "init starting")
        viewModelScope.launch {
            Log.d(TAG, "coroutine launched")
            /*delay(5000)
            for (i in 0 until 100) {
                val crime = Crime(

                    id = UUID.randomUUID(),
                    title = "Crime #$i",
                    date = Date(),
                    isSolved = i % 2 == 0,
                    requiresPolice = i % 2 != 0
                )
                crimes += crime
            }*/

        crimes += loadCrimes()
        Log.d(TAG, "Loading crimes finished")
    }
}
    suspend fun loadCrimes(): List<Crime> {
        /*val result = mutableListOf<Crime>()
        delay(5000)
        for (i in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #$i",
                date = Date(),
                isSolved = i % 2 == 0,
                requiresPolice = i % 2 != 0
            )

            result += crime
        }*/
        return crimeRepository.getCrimes()
    }
}
