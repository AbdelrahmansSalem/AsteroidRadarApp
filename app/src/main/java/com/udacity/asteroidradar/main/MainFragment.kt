package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        setHasOptionsMenu(true)


        binding.asteroidRecycler.adapter=MainAdapter(MainAdapter.onClickListener {
            viewModel.navigateToDetails(it)
        })

        viewModel.navigate_to_details.observe(viewLifecycleOwner, Observer {
            it?.let {
                    this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                    viewModel.navigateToDetailsDone()

            }

        })
        viewModel.filterType.observe(viewLifecycleOwner, Observer {
            viewModel.setList()
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.getFilteredData(when(item.itemId){
            R.id.show_today_menu->dataFilter.TODAY_DATA
            else->dataFilter.ALL_DATA
        })
        return true
    }
}
