public class ProductVC extends Product {

    private String interfaceVC; // interface is keyword
    private String chipset;
    private String gpu;
    private int memorySize;
    private String memoryType;
    private String maxResolution;
    private String cooler;
    private int maxGPULength;
    private String cardDimensions;

    public ProductVC(int id) {
        super(id);
    }

    public ProductVC(Product product) {
        super(product.getId(), product.getModel(), product.getBrand(), product.getName(), product.getSeries(),
                product.getCategory(), product.getPrice(), product.getDescription(), product.getImgSrc());
    }

    public String getInterfaceVC() {
        return interfaceVC;
    }

    public void setInterfaceVC(String interfaceVC) {
        this.interfaceVC = interfaceVC;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getMaxResolution() {
        return maxResolution;
    }

    public void setMaxResolution(String maxResolution) {
        this.maxResolution = maxResolution;
    }

    public String getCooler() {
        return cooler;
    }

    public void setCooler(String cooler) {
        this.cooler = cooler;
    }

    public int getMaxGPULength() {
        return maxGPULength;
    }

    public void setMaxGPULength(int maxGPULength) {
        this.maxGPULength = maxGPULength;
    }

    public String getCardDimensions() {
        return cardDimensions;
    }

    public void setCardDimensions(String cardDimensions) {
        this.cardDimensions = cardDimensions;
    }

    @Override
    public String toString() {
        return "ProductVC{" +
                "interfaceVC='" + interfaceVC + '\'' +
                ", chipset='" + chipset + '\'' +
                ", gpu='" + gpu + '\'' +
                ", memorySize=" + memorySize +
                ", memoryType='" + memoryType + '\'' +
                ", maxResolution='" + maxResolution + '\'' +
                ", cooler='" + cooler + '\'' +
                ", maxGPULength=" + maxGPULength +
                ", cardDimensions='" + cardDimensions + '\'' +
                '}';
    }
}
