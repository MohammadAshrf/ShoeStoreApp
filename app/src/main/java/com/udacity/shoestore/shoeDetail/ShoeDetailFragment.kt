package com.udacity.shoestore.shoeDetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.shoeList.ShoeListFragmentDirections
import com.udacity.shoestore.shoeList.ShoeViewModel
import timber.log.Timber


class ShoeDetailFragment : Fragment() {

    private var _binding: FragmentShoeDetailBinding? = null
    private val binding get() = _binding!!
    private val shoeViewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShoeDetailBinding.inflate(inflater, container, false)

        binding.shoeViewModel = shoeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoeViewModel.savedSuccessfully.observe(viewLifecycleOwner, Observer {
            if (it) {
                Timber.tag("Mohammad").d(shoeViewModel.myShoeList.value.toString())
                findNavController().navigateUp()
                shoeViewModel.updateSuccessState(false)
            }
        })

        shoeViewModel.savedFailed.observe(viewLifecycleOwner, Observer {
            if (it) {
                savedFailedMessage(R.string.saved_failed_message)
            }
        })

        shoeViewModel.cancel.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
                shoeViewModel.cancelProcess(false)
            }
        })
        val menuHost: MenuHost = requireActivity()            //TODO: New Menu Code

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.log_out -> {
                        findNavController().navigate(ShoeListFragmentDirections.actionShoeListDestinationToLoginDestination())
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun savedFailedMessage(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_SHORT).show()
    }

}