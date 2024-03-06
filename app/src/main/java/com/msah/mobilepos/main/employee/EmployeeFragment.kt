package com.msah.mobilepos.main.employee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.Order
import com.msah.mobilepos.databinding.FragmentEmployeeBinding
import com.msah.mobilepos.loadingprogress.LoadingProgressBar
import com.msah.mobilepos.main.employee.adapter.OrderAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeFragment : Fragment() {

    private lateinit var bnd: FragmentEmployeeBinding
    private lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var ordersRecyclerView: RecyclerView


    private val db = FirebaseFirestore.getInstance()
    private val ordersCollection = db.collection("orders")



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_employee, container, false)

        (activity as AppCompatActivity).setSupportActionBar(bnd.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "ORDERS"
        return bnd.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = LoadingProgressBar(requireContext())

        ordersRecyclerView = bnd.ordersRecyclerView
        ordersRecyclerView.layoutManager = LinearLayoutManager(context)

        val recyclerView = bnd.ordersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize orderAdapter with an empty list initially
        val orderAdapter = OrderAdapter(emptyList())
        recyclerView.adapter = orderAdapter

        // Retrieve data from Firestore and update the adapter
        getOrders(
            onSuccess = { orders ->
                requireActivity().runOnUiThread {
                    orderAdapter.updateOrders(orders)
                }
            },
            onFailure = { exception ->
                // Handle failure if needed
            }
        )
    }


    fun getOrders(onSuccess: (List<Order>) -> Unit, onFailure: (Exception) -> Unit) {
        ordersCollection
            .get()
            .addOnSuccessListener { result ->
                val ordersList = mutableListOf<Order>()
                for (document in result) {
                    val order = document.toObject(Order::class.java)
                    ordersList.add(order)
                }
                onSuccess(ordersList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}