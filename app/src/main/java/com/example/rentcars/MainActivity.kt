package com.example.rentcars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = checkNotNull(_binding) { "Binding is null" }

    private val navController by lazy {
        val navFragment =
            supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
        navFragment.findNavController()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
//        if (viewModel.isUserAuthorized()) {
//            viewModel.loadUserProfile()
//            navGraph.setStartDestination(R.id.mainFlowFragment)
//        } else {
//            navGraph.setStartDestination(R.id.signFlowFragment)
//        }
        navGraph.setStartDestination(R.id.mainFlowFragment)
        navController.graph = navGraph
    }
}