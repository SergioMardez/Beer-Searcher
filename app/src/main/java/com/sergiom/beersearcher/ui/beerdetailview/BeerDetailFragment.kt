package com.sergiom.beersearcher.ui.beerdetailview

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sergiom.beersearcher.R
import com.sergiom.beersearcher.databinding.FragmentBeerDetailBinding
import com.sergiom.beersearcher.ui.beersearchview.BeerSearchViewModel
import com.sergiom.beersearcher.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "beerId"
private const val ARG_PARAM2 = "isEdit"

/**
 * A simple [Fragment] subclass.
 * Use the [BeerDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class BeerDetailFragment : Fragment() {

    private var binding: FragmentBeerDetailBinding by autoCleared()
    private val viewModel: BeerDetailViewModel by viewModels()
    private var beerId: Int = -1
    private var isEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            beerId = it.getInt(ARG_PARAM1)
            isEdit = it.getBoolean(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModel.getBeer(beerId)
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        setEditView()
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.beer.observe(viewLifecycleOwner) { beer ->
                        if (beer == null) {
                            parentFragmentManager.popBackStack()
                        }
                        binding.detailView.setDetail(beer = beer)
                    }
                }
            }
        }
    }

    private fun setEditView() {
        if (isEdit) {
            binding.deleteBeer.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    setDialog()
                }
            }
        }
    }

    private fun setDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.dialog_ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.deleteBeer(beerId)
                    })
                setNegativeButton(R.string.dialog_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            }
            builder.setMessage(R.string.dialog_delete_message)
            builder.create()
        }
        alertDialog?.show()
    }

    companion object {
        /**
         * @param beerId id of the beer to get from data base.
         * @return A new instance of fragment BeerDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(beerId: Int, isEdit: Boolean = false) =
            BeerDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, beerId)
                    putBoolean(ARG_PARAM2, isEdit)
                }
            }
    }
}