public class ProductResponse {
    String message;

    ProductCPU productCPU;
    ProductRAM productRAM;
    ProductVC productVC;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProductCPU getProductCPU() {
        return productCPU;
    }

    public void setProductCPU(ProductCPU productCPU) {
        this.productCPU = productCPU;
    }

    public ProductRAM getProductRAM() {
        return productRAM;
    }

    public void setProductRAM(ProductRAM productRAM) {
        this.productRAM = productRAM;
    }

    public ProductVC getProductVC() {
        return productVC;
    }

    public void setProductVC(ProductVC productVC) {
        this.productVC = productVC;
    }
}
