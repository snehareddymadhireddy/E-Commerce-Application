import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.Cart
import com.example.finalproject1.ProductData
import com.example.finalproject1.databinding.CartListBinding
import com.example.finalproject1.databinding.SuperCartListBinding

class CartAdapter(
    private val cartItems: List<Cart>,
    private val productDataList: List<ProductData>,
    private val onQuantityChanged: (Cart) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
private lateinit var binding: CartListBinding
    inner class ViewHolder(private val binding: CartListBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(cartItem: Cart, productData: ProductData) {
            with(binding) {
                imgProduct.setImageResource(productData.image)
                txtProductName.text = productData.name
                txtProductDescription.text = productData.description
                txtProductPrice.text = "$${productData.price}"
                etQuantity.setText(cartItem.quantity.toString())
                txtTotalPrice.text = "$${cartItem.totalPrice}"

                btnMinus.setOnClickListener {
                    val quantity = cartItem.quantity - 1
                    if (quantity > 0) {
                        val updatedItem = cartItem.copy(quantity = quantity, totalPrice = quantity * cartItem.price)
                        onQuantityChanged(updatedItem)
                    }
                }

                btnPlus.setOnClickListener {
                    val quantity = cartItem.quantity + 1
                    val updatedItem = cartItem.copy(quantity = quantity, totalPrice = quantity * cartItem.price)
                    onQuantityChanged(updatedItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        val productData = productDataList.find { it.id == cartItem.productId }
        productData?.let { holder.bind(cartItem, it) }
    }

    override fun getItemCount() = cartItems.size
}
