import java.util.ArrayList;
import java.util.List;

public class ProductListResponse {

    private String message;

    private List<ProductCPU> productCPUList = new ArrayList<>();
    private List<ProductRAM> productRAMList = new ArrayList<>();
    private List<ProductVC> productVCList = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductCPU> getProductCPUList() {
        return productCPUList;
    }

    public void setProductCPUList(List<ProductCPU> productCPUList) {
        this.productCPUList = productCPUList;
    }

    public List<ProductRAM> getProductRAMList() {
        return productRAMList;
    }

    public void setProductRAMList(List<ProductRAM> productRAMList) {
        this.productRAMList = productRAMList;
    }

    public List<ProductVC> getProductVCList() {
        return productVCList;
    }

    public void setProductVCList(List<ProductVC> productVCList) {
        this.productVCList = productVCList;
    }

    public void addProductCPU(ProductCPU productCPU) {
        this.productCPUList.add(productCPU);
    }

    public void addProductRAM(ProductRAM productRAM) {
        this.productRAMList.add(productRAM);
    }

    public void addProductVC(ProductVC productVC) {
        this.productVCList.add(productVC);
    }
}
