package com.example.newsbreezer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.newsbreezer.R
import kotlinx.android.synthetic.main.toolbar.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class HomeActivity : AppCompatActivity(), KodeinAware {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    companion object {
        private const val TAG: String = "HomeActivity"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setSupportActionBar(toolbar) // To set toolBar as ActionBar

        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)


        // Update action bar to reflect navigation
        //appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)


    }


    /**
     * If using the default action bar this must be overridden.
     * This will handle back actions initiated by the the back arrow
     * at the start of the action bar.
     */
   /* override fun onSupportNavigateUp(): Boolean {
        // Handle the back button event and return true to override
        // the default behavior the same way as the OnBackPressedCallback.
        // TODO(reason: handle custom back behavior here if desired.)
        // If no custom behavior was handled perform the default action.
        return findNavController(R.id.myNavHostFragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/


}