import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sg.challengechap2.R
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.databinding.CheckoutListItemBinding
import com.sg.challengechap2.databinding.ItemListCartBinding
import com.sg.challengechap2.model.Cart
import com.sg.challengechap2.presentation.cart.adapter.CartListener
import com.sg.challengechap2.utils.doneEditing


class CartItemViewHolder(
    private val binding: ItemListCartBinding,
    private val cartListener: CartListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart>{
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: Cart) {
        binding.apply {
            ivPlusCart.setOnClickListener{cartListener?.onPlusTotalItemCartClicked(item)}
            ivMinusCart.setOnClickListener{cartListener?.onMinusTotalItemCartClicked(item)}
            icDeleteCart.setOnClickListener{cartListener?.onRemoveCartClicked(item)}
           // itemView.setOnClickListener{cartListener?.onCartClicked(item)}
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etCardNote.setText(item.itemNotes)
        binding.etCardNote.doneEditing {
            binding.etCardNote.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etCardNote.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(item: Cart) {
        binding.apply {
            ivCartMenu.load(item.foodImgUrl){crossfade(true)}
            tvFoodCartName.text = item.foodName
            tvFoodCartPrice.text = ("Rp "+ item.itemQuantity * item.foodPrice)
            tvCartTotal.text = item.itemQuantity.toString()
        }

    }

}


class CheckoutViewHolder(
    private val binding: CheckoutListItemBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivCheckout.load(item.foodImgUrl) {
                crossfade(true)
            }
            tvTotalItem.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.itemQuantity.toString()
                )
            tvFoodNameCheckout.text = item.foodName
            tvFoodPriceCheckout.text = item.foodPrice.toString()
            tvFoodPriceCheckout.text = ("Rp "+ item.itemQuantity * item.foodPrice)
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etCardNote.text = item.itemNotes
    }
}

