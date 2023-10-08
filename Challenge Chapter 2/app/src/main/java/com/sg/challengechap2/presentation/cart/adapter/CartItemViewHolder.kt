import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sg.challengechap2.R
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.databinding.CheckoutListItemBinding
import com.sg.challengechap2.databinding.ItemListCartBinding
import com.sg.challengechap2.model.CartFood
import com.sg.challengechap2.presentation.cart.adapter.CartListAdapter
import com.sg.challengechap2.presentation.cart.adapter.CartListener
import com.sg.challengechap2.presentation.utils.doneEditing


class CartItemViewHolder(
    private val binding: ItemListCartBinding,
    private val cartListener: CartListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartFood>{
    override fun bind(item: CartFood) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: CartFood) {
        binding.apply {
            ivPlusCart.setOnClickListener{cartListener?.onPlusTotalItemCartClicked(item.cart)}
            ivMinusCart.setOnClickListener{cartListener?.onMinusTotalItemCartClicked(item.cart)}
            icDeleteCart.setOnClickListener{cartListener?.onRemoveCartClicked(item.cart)}
            itemView.setOnClickListener{cartListener?.onCartClicked(item)}
        }
    }

    private fun setCartNotes(item: CartFood) {
        binding.etCardNote.setText(item.cart.itemNotes)
        binding.etCardNote.doneEditing {
            binding.etCardNote.clearFocus()
            val newItem = item.cart.copy().apply {
                itemNotes = binding.etCardNote.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(item: CartFood) {
        binding.apply {
            ivCartMenu.load(item.food.foodImg){crossfade(true)}
            tvFoodCartName.text = item.food.foodName
            tvFoodCartPrice.text = item.food.foodPrice.toString()
            tvCartTotal.text = item.cart.itemQuantity.toString()
        }

    }

}


class CheckoutViewHolder(
    private val binding: CheckoutListItemBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartFood> {
    override fun bind(item: CartFood) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: CartFood) {
        with(binding) {
            binding.ivCheckout.load(item.food.foodImg) {
                crossfade(true)
            }
            tvTotalItem.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.cart.itemQuantity.toString()
                )
            tvFoodNameCheckout.text = item.food.foodName
            tvFoodPriceCheckout.text = item.food.foodPrice.toString()
            tvFoodPriceCheckout.text = String.format("Rp.", (item.food.foodPrice * item.cart.itemQuantity))
        }
    }

    private fun setCartNotes(item: CartFood) {
        binding.etCardNote.text = item.cart.itemNotes
    }
}

