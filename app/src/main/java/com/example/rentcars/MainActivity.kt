package com.example.rentcars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.databinding.ActivityMainBinding
import com.example.rentcars.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = checkNotNull(_binding) { "Binding is null" }
    private val viewModel : MainViewModel by viewModels()
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
        if (viewModel.isAuthorized()) {
            navGraph.setStartDestination(R.id.mainFlowFragment)
        } else {
            navGraph.setStartDestination(R.id.signFlowFragment)
        }

        navController.graph = navGraph
    }
}