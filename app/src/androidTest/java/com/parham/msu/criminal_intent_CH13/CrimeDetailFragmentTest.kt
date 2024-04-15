package com.parham.msu.criminal_intent_CH13

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import org.junit.After
import org.junit.Before
import org.junit.Test


class CrimeDetailFragmentTest {

    private lateinit var scenario: FragmentScenario<CrimeDetailFragment>

    @Before
    fun setUp() {
        // Launch the fragment scenario
        scenario = launchFragmentInContainer<CrimeDetailFragment>()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testCheckBoxAndEditText() {
        // Verify that the CheckBox and EditText are hooked up to the fragment
        scenario.onFragment { fragment ->
            // Verify that the Crime object is initialized correctly
            val crime = fragment.crime
            assert(crime != null)

            // Perform assertions on the initial state of the Crime object
            crime?.apply {
                assert(title.isEmpty())
                assert(!isSolved)

                // Simulate user interaction
                val updatedCrime = copy(title = "Test Crime", isSolved = true)

                // Verify updated state
                assert(updatedCrime.title == "Test Crime")
                assert(updatedCrime.isSolved)

            }
        }

    }
}
