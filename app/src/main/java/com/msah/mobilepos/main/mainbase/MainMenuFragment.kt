package com.msah.mobilepos.main.mainbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.msah.mobilepos.R
import com.msah.mobilepos.data.repository.basket.BasketRepositoryImpl
import com.msah.mobilepos.databinding.FragmentMainMenuBinding
import com.msah.mobilepos.basket.BasketFragment
import com.msah.mobilepos.basket.viewmodel.BasketViewModel
import com.msah.mobilepos.basket.viewmodel.BasketViewModelFactory
import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.data.preference.UserPref
import com.msah.mobilepos.main.employee.EmployeeFragment
import com.msah.mobilepos.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMenuFragment : Fragment() {

    private lateinit var bnd: FragmentMainMenuBinding
    private lateinit var navHostFragment: NavHostFragment
    private val viewModel by viewModels<BasketViewModel> {
        BasketViewModelFactory(
            BasketRepositoryImpl()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userPref = UserPref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            if (userPref.getAccType() == "STAFF") {
                // Get the FragmentManager from the Activity or Fragment
                val fragmentManager = parentFragmentManager ?: return@launch

                val transaction = fragmentManager.beginTransaction()

                transaction.replace(R.id.fragmentContainerView, EmployeeFragment()) // Replace the current fragment
                transaction.commit()
            } else if(userPref.getAccType() == "CUSTOMER"){
                init()
            }
        }
    }

    private fun init(){

        bnd.mainMenuFragment = this

        viewModel.basketCountLiveData.observe(viewLifecycleOwner){
            bnd.count = it
        }

        navHostFragment = childFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment

        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph_bottom_bar)
        navHostFragment.navController.graph = graph
        NavigationUI.setupWithNavController(bnd.bottomNav, navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            bnd.isBottomNavVisible = destination.id != R.id.productDetailsFragment
        }
    }
    fun openBasket(){
        BasketFragment().show(parentFragmentManager, "Cart")

    }

}