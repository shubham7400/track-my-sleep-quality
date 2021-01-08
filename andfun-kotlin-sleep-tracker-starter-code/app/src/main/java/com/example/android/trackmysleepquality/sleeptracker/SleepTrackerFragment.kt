
package com.example.android.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)

        // requireNotNull is a kotlin function which throws an exception if passed argument is null
        val application = requireNotNull(this.activity).application

        //To get a reference to the DAO of the database, use
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)

        val sleepTrackerViewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        // it is neccessory so that binding can observe live update. Set the current activity as the lifecycle owner of the binding.
        binding.setLifecycleOwner(this)

        //Assign the sleepTrackerViewModel binding variable to the sleepTrackerViewModel.
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer {   night ->
            night?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                sleepTrackerViewModel.doneNavigating()
            }
        })


        return binding.root
    }
}
