package com.msah.mobilepos.auth.employeeLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.msah.mobilepos.R
import com.msah.mobilepos.data.preference.UserPref
import com.msah.mobilepos.loadingprogress.LoadingProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_login, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployeeLoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployeeLoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val btnEmployeeSignIn = view.findViewById<Button>(R.id.btnEmployeeSignIn)
        val etEmployeeEmail = view.findViewById<EditText>(R.id.etEmployeeEmail)
        val etEmployeePassword = view.findViewById<EditText>(R.id.etEmployeePassword)
        btnEmployeeSignIn.setOnClickListener {
            val email = etEmployeeEmail.text.toString()
            val password = etEmployeePassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(view, "Please enter email and password", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var loadingProgressBar = LoadingProgressBar(requireContext())
            loadingProgressBar.show()



            // Firebase authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val userPref = UserPref(requireContext())
                            userPref.setUsername(email)
                            userPref.setEmail(email)
                            userPref.setAccType("STAFF")
                        }
                        findNavController().navigate(R.id.action_employeeLoginFragment_to_employeeFragment)

                        loadingProgressBar.cancel()

                    } else {
                        // If sign in fails, display a message to the user.
                        loadingProgressBar.cancel()
                        Snackbar.make(view, "Authentication failed.", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }

    }
}