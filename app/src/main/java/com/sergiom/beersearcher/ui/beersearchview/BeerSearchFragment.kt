package com.sergiom.beersearcher.ui.beersearchview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.sergiom.beersearcher.R
import com.sergiom.beersearcher.databinding.FragmentBeerSearchBinding
import com.sergiom.beersearcher.ui.beerdetailview.BeerDetailFragment
import com.sergiom.beersearcher.ui.beersearchview.viewadapter.BeersAdapter
import com.sergiom.beersearcher.ui.viewedbeersview.ViewedBeersFragment
import com.sergiom.beersearcher.utils.autoCleared
import com.sergiom.data.models.BeerModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [BeerSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class BeerSearchFragment : Fragment(), BeersAdapter.ItemClickListener {

    private var binding: FragmentBeerSearchBinding by autoCleared()
    private val viewModel: BeerSearchViewModel by viewModels()
    private lateinit var adapter: BeersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBeerSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setSearchView()
        setupRecyclerView()
        binding.goViewedBeers.setOnClickListener {
            val fragment = ViewedBeersFragment.newInstance()
            goToFragment(fragment = fragment, name = "viewedBeers")
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.beers?.let { beers ->
                        adapter.setItems(beers)
                        if (binding.beersRecyclerView.isVisible.not()) {
                            binding.errText.visibility = View.GONE
                            binding.beersRecyclerView.visibility = View.VISIBLE
                        }
                        if (beers.beers.isEmpty()) {
                            setErrorMessage(emptyError = true)
                        }
                        binding.beersRecyclerView.scrollToPosition(0)
                    }
                    it.loading.let { isVisible ->
                        binding.beerImage.isVisible = isVisible
                    }
                    it.error?.let { error ->
                        setErrorMessage()
                        Toast.makeText(context, "ERROR: $error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setErrorMessage(emptyError: Boolean = false) {
        binding.beersRecyclerView.visibility = View.GONE
        if (emptyError)
            binding.errText.text = getString(R.string.empty_search)
        else
            binding.errText.text = getString(R.string.error_text)
        binding.errText.visibility = View.VISIBLE
    }

    private fun setSearchView() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.getBeerQuery(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.length?.let {
                        if (it > 0) {
                            viewModel.getBeerQuery(newText)
                        }
                    }
                    return true
                }

            })
            queryHint = getString(R.string.action_search)
            setOnClickListener {
                this.onActionViewExpanded()
            }
            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if(!hasFocus) {
                    if (this.query.toString().isEmpty()) {
                        this.onActionViewCollapsed()
                        viewModel.getBeers() //Default beers at the end
                    }
                    this.clearFocus()
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

    override fun onItemClicked(item: BeerModel) {
        viewModel.saveBeerToDataBase(item)
        val fragment = BeerDetailFragment.newInstance(item.id)
        goToFragment(fragment = fragment, name = "beerDetail")
    }

    private fun goToFragment(fragment: Fragment, name: String) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.addToBackStack(name)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    companion object {
        /**
         * @return A new instance of fragment BeerSearchFragment.
         */
        @JvmStatic
        fun newInstance() = BeerSearchFragment()
    }

}