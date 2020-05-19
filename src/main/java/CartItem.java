public class CartItem {
    private int productID;
    private int quantity;

    private Product product;
    private ProductVC videoCard;    // TODO: just need gpu for naming purpose; find better way to do this

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

    public ProductVC getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(ProductVC videoCard) {
        this.videoCard = videoCard;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productID=" + productID +
                ", quantity=" + quantity +
                '}';
    }
}
