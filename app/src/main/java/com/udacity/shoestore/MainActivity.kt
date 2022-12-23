package com.udacity.shoestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.shoeList.ShoeListFragmentDirections
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment) TODO: Error.
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController TODO: Worked.
        navController =
            binding.navHostFragment.getFragment<NavHostFragment>().navController
        navController.setGraph(R.navigation.navigation, intent.extras)   // TODO: Worked.

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

    }

}

