import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int customerID;
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(int customerID) {
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addCartItem(CartItem cartItem) {
        CartItem existingItem = findCartItemById(cartItem.getProductID());
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + cartItem.getQuantity();
            this.cartItems.get(this.cartItems.indexOf(existingItem)).setQuantity(newQuantity);
        } else {
            this.cartItems.add(cartItem);
        }
    }

    public void removeCartItem(CartItem cartItem) {
        CartItem existingItem = findCartItemById(cartItem.getProductID());
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() - cartItem.getQuantity();
            if (newQuantity > 0) {
                this.cartItems.get(this.cartItems.indexOf(existingItem)).setQuantity(newQuantity);
            } else {
                this.cartItems.remove(cartItem);
            }
        }

    }

    private CartItem findCartItemById (int productID) {
        return this.cartItems.stream().filter(cartItem -> cartItem.getProductID() == productID).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "customerID=" + customerID +
                ", cartItems=" + cartItems +
                '}';
    }
}
