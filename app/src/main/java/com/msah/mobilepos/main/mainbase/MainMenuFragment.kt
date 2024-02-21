package com.msah.mobilepos.main.mainbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.msah.mobilepos.R
import com.msah.mobilepos.data.repository.basket.BasketRepositoryImpl
import com.msah.mobilepos.databinding.FragmentMainMenuBinding
import com.msah.mobilepos.basket.BasketFragment
import com.msah.mobilepos.basket.viewmodel.BasketViewModel
import com.msah.mobilepos.basket.viewmodel.BasketViewModelFactory
import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.utils.Constants

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
        init()
    }

    private fun init(){

        bnd.mainMenuFragment = this

        viewModel.basketCountLiveData.observe(viewLifecycleOwner){
            bnd.count = it
        }

       navHostFragment = childFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        NavigationUI.setupWithNavController(bnd.bottomNav, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            bnd.isBottomNavVisible = destination.id != R.id.productDetailsFragment
        }


    }


    fun openBasket(){

        BasketFragment().show(parentFragmentManager, "Cart")

    }

}