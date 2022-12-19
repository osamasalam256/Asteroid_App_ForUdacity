package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.MainAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

@Suppress("DEPRECATION")
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity =requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainFactory(activity.application))[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)


        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        binding.asteroidRecycler.adapter = MainAdapter(MainAdapter.OnClickListener{
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner) {
            it?.let {
                val action = MainFragmentDirections.actionShowDetail(it)
                findNavController().navigate(action)
                viewModel.displayPropertyDetailsComplete()
            }
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_week ->viewModel.weekAsteroids()
            R.id.show_all->viewModel.getAllAstero()
            R.id.show_today->viewModel.dayAsteroids()
        }


        return true
    }
}
