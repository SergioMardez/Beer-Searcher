package com.sergiom.beersearcher.ui.viewedbeersview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.sergiom.beersearcher.R
import com.sergiom.beersearcher.databinding.FragmentViewedBeersBinding
import com.sergiom.beersearcher.ui.beerdetailview.BeerDetailFragment
import com.sergiom.beersearcher.ui.beersearchview.viewadapter.BeersAdapter
import com.sergiom.beersearcher.utils.autoCleared
import com.sergiom.data.models.BeerModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ViewedBeersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ViewedBeersFragment : Fragment(), BeersAdapter.ItemClickListener {

    private var binding: FragmentViewedBeersBinding by autoCleared()
    private val viewModel: ViewedBeersViewModel by viewModels()
    private lateinit var adapter: BeersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewedBeersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setupRecyclerView()
        setButtons()
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.beers.observe(viewLifecycleOwner) { beers ->
                        adapter.setItems(viewModel.mapBeers(beers))
                        if (beers.isEmpty()) {
                            binding.emptyListText.visibility = View.VISIBLE
                            binding.beersRecyclerView.visibility = View.GONE
                            binding.deleteBeers.visibility = View.GONE
                        } else {
                            binding.emptyListText.visibility = View.GONE
                            binding.beersRecyclerView.visibility = View.VISIBLE
                            binding.deleteBeers.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = BeersAdapter(this)
        binding.apply {
            beersRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            beersRecyclerView.adapter = adapter
        }
    }

    private fun setButtons() {
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.deleteBeers.setOnClickListener {
            viewModel.deleteAllBeers()
        }
    }

    override fun onItemClicked(item: BeerModel) {
        val fragment = BeerDetailFragment.newInstance(beerId = item.id, isEdit = true)
        val transaction = parentFragmentManager.beginTransaction()
        transaction.addToBackStack("beerDetail")
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    companion object {
        /**
         * @return A new instance of fragment ViewedBeersFragment.
         */
        @JvmStatic
        fun newInstance() = ViewedBeersFragment()
    }

}