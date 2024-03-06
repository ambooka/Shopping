package com.msah.mobilepos.main.employee.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.Order

class OrderAdapter(private var orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userIdTextView: TextView = itemView.findViewById(R.id.userIdTextView)
        private val processedByTextView: TextView = itemView.findViewById(R.id.processedByTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)

        fun bind(order: Order) {
            userIdTextView.text = "User ID: ${order.userId}"
            processedByTextView.text = "Processed By: ${order.processedBy}"
            statusTextView.text = "Status: ${order.status}"
        }
    }
}
