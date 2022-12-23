package com.udacity.shoestore.shoeList

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.models.Shoe


class ShoeListFragment : Fragment() {
    private var _binding: FragmentShoeListBinding? = null
    private val binding get() = _binding!!
    private val shoeViewModel: ShoeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoeListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addShoeFab.setOnClickListener {
            findNavController().navigate(ShoeListFragmentDirections.actionShoeListDestinationToShoeDetailFragment())
        }

        if (!shoeViewModel.myShoeList.value.isNullOrEmpty()) {
            shoeViewModel.myShoeList.value!!.forEach { shoeItem ->
                addViews(shoeItem)
            }

        }

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


    private fun addViews(shoe: Shoe) {
        val linearLayout = binding.container

        val item = View.inflate(this.context, R.layout.shoe_item, null)

        val name = item.findViewById<TextView>(R.id.shoe_name)
        name.text = shoe.name

        val size = item.findViewById<TextView>(R.id.shoe_size)
        size.text = shoe.name

        val brand = item.findViewById<TextView>(R.id.shoe_brand)
        brand.text = shoe.brand

        val desc = item.findViewById<TextView>(R.id.shoe_description)
        desc.text = shoe.description

        linearLayout.addView(item)
    }

}