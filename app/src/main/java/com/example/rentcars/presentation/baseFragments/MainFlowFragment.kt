package com.teamforce.thanksapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.rentcars.R
import com.example.rentcars.databinding.FragmentMainFlowBinding
import com.example.rentcars.presentation.baseFragments.BaseFlowFragment


class MainFlowFragment : BaseFlowFragment(
    R.layout.fragment_main_flow, R.id.nav_host_fragment_main
) {
    private var _binding: FragmentMainFlowBinding? = null
    private val binding get() = checkNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainFlowBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun setupNavigation(navController: NavController) {
        binding.bottomNavigation.setupWithNavController(navController)

//        binding.bottomNavigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.feed_graph -> {
//                    navController.navigate(
//                        R.id.feed_graph,
//                        null,
//                    )
//                    return@OnItemSelectedListener true
//                }
//                R.id.balance_graph -> {
//                    navController.navigate(
//                        R.id.balance_graph,
//                        null,
//                    )
//                    return@OnItemSelectedListener true
//                }
////                R.id.transaction_graph -> {
////                    navController.navigate(R.id.transaction_graph, null, OptionsTransaction().optionForTransaction2)
////                    return@OnItemSelectedListener true
////                }
//                R.id.benefitFragment -> {
//                    navController.navigate(
//                        R.id.benefitFragment,
//                        null,
//                    )
//                    return@OnItemSelectedListener true
//                }
//                R.id.more -> {
//                    binding.drawerLayout.openDrawer()
//                    return@OnItemSelectedListener false
//                }
//            }
//            true
//        })

//        binding.bottomNavigation.setOnItemReselectedListener(NavigationBarView.OnItemReselectedListener { item ->
//            when (item.itemId) {
//                R.id.feed_graph -> {
//                    navController.navigate(
//                        R.id.feed_graph,
//                        null,
//                    )
//                    return@OnItemReselectedListener
//                }
//                R.id.balance_graph -> {
//                    navController.navigate(
//                        R.id.balance_graph,
//                        null,
//                    )
//                    return@OnItemReselectedListener
//                }
//                R.id.transaction_graph -> {
//
//                    return@OnItemReselectedListener
//                }
//                R.id.history_graph -> {
//
//                    return@OnItemReselectedListener
//                }
//                R.id.challengesFragment -> {
//
//                    return@OnItemReselectedListener
//                }
//                R.id.benefitFragment -> {
//                    navController.navigate(
//                        R.id.benefitFragment,
//                        null,
//                    )
//                    return@OnItemReselectedListener
//                }
//                R.id.more -> {
//                    binding.drawerLayout.openDrawer()
//                    return@OnItemReselectedListener
//                }
//            }
//
//        })


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addAutoFragment ||
                destination.id == R.id.detailCarFragment
            ) {
                hideBottomNavigation()
            } else {
                showBottomNavigation()
            }
        }
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigation.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        binding.bottomNavigation.visibility = View.VISIBLE
        binding.bottomNavigation.visibility = View.VISIBLE

    }


}

