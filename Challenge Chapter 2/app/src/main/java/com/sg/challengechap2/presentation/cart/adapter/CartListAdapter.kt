package com.sg.challengechap2.presentation.cart.adapter

import CartItemViewHolder
import CheckoutViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.databinding.CheckoutListItemBinding
import com.sg.challengechap2.databinding.ItemListCartBinding
import com.sg.challengechap2.model.Cart

class CartListAdapter(
    private val cartListener: CartListener? = null
) : RecyclerView.Adapter<ViewHolder>() {


    private val dataDiffer = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Cart>(){

        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id && oldItem.foodId == newItem.foodId
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (cartListener != null) CartItemViewHolder(
            ItemListCartBinding.inflate(
                LayoutInflater.from(parent.context),parent, false
            ), cartListener
        )else CheckoutViewHolder(
            CheckoutListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Cart>).bind((dataDiffer.currentList[position]))
    }

    override fun getItemCount(): Int {
        return dataDiffer.currentList.size
    }

    fun setData(cartData:List<Cart>){
        dataDiffer.submitList(cartData)
    }
}
