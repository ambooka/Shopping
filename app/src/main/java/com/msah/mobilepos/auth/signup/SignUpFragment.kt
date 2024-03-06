package com.msah.mobilepos.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.DataState
import com.msah.mobilepos.data.model.User
import com.msah.mobilepos.data.preference.UserPref
import com.msah.mobilepos.data.repository.auth.AuthRepositoryImpl
import com.msah.mobilepos.data.repository.user.UserRepositoryImpl
import com.msah.mobilepos.databinding.FragmentSignUpBinding
import com.msah.mobilepos.auth.signup.viewmodel.SignUpViewModel
import com.msah.mobilepos.auth.signup.viewmodel.SignUpViewModelFactory
import com.msah.mobilepos.loadingprogress.LoadingProgressBar
import com.msah.mobilepos.utils.AlertMessageViewer.showAlertDialogMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private lateinit var bnd: FragmentSignUpBinding
    private lateinit var loadingProgressBar: LoadingProgressBar
    private val viewModel: SignUpViewModel by viewModels{
        SignUpViewModelFactory(
            AuthRepositoryImpl(),
            UserRepositoryImpl()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        bnd.viewModel = viewModel
        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.userLiveData.observe(viewLifecycleOwner){
            handleSignUp(it)
        }

    }

    private fun handleSignUp(it: DataState<User>){

        when(it){

            is DataState.Loading -> {
                loadingProgressBar.show()
            }

            is DataState.Success -> {
                loadingProgressBar.cancel()
                saveUserData(it.data)
            }

            is DataState.Error -> {
                loadingProgressBar.cancel()
                showAlertDialogMessage(requireContext(), it.message)
            }

        }

    }

    private fun saveUserData(user: User){

        val userPref = UserPref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            userPref.setUsername(user.username!!)
            userPref.setEmail(user.email!!)
            userPref.setPhone(user.phone!!)
            userPref.setAddress((user.address!!))
            userPref.setOrders((user.orders!!))
            userPref.setDateJoined((user.dateJoined!!.toString()))
            userPref.setAccType("CUSTOMER")

            findNavController().navigate(R.id.action_authFragment_to_mainMenuFragment)

        }

    }

}