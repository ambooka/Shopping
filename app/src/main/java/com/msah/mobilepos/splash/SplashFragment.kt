package com.msah.mobilepos.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.msah.mobilepos.R
import com.msah.mobilepos.data.preference.UserPref
import com.msah.mobilepos.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var bnd: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        val userPref = UserPref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            delay(1000)

            if(FirebaseAuth.getInstance().currentUser != null && userPref.getEmail().isNotEmpty()){

                findNavController().navigate(R.id.action_splashFragment_to_mainMenuFragment)

            }else{

                if(userPref.isFirstUsage()){
                    findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                }else{
                    findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                }

            }

        }

    }

}