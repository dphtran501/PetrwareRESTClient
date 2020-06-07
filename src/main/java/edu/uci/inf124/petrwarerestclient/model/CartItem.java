package edu.uci.inf124.petrwarerestclient.model;

public class CartItem {
    private int productID;
    private int quantity;

    private Product product;

    public CartItem() {}

    public CartItem(int productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "edu.uci.inf124.petrwarerestclient.model.CartItem{" +
                "productID=" + productID +
                ", quantity=" + quantity +
                '}';
    }
}
