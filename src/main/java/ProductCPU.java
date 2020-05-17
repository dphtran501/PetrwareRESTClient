public class ProductCPU extends Product {

    private String processorsType;
    private String socketType;
    private String coreName;
    private int numOfCores;
    private int numOfThreads;
    private double operatingFrequency;
    private double maxTurboFrequency;

    public ProductCPU(int id) {
        super(id);
    }

    public ProductCPU(Product product) {
        super(product.getId(), product.getModel(), product.getBrand(), product.getName(), product.getSeries(),
                product.getCategory(), product.getPrice(), product.getDescription(), product.getImgSrc());
    }

    public String getProcessorsType() {
        return processorsType;
    }

    public void setProcessorsType(String processorsType) {
        this.processorsType = processorsType;
    }

    public String getSocketType() {
        return socketType;
    }

    public void setSocketType(String socketType) {
        this.socketType = socketType;
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public int getNumOfCores() {
        return numOfCores;
    }

    public void setNumOfCores(int numOfCores) {
        this.numOfCores = numOfCores;
    }

    public int getNumOfThreads() {
        return numOfThreads;
    }

    public void setNumOfThreads(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }

    public double getOperatingFrequency() {
        return operatingFrequency;
    }

    public void setOperatingFrequency(double operatingFrequency) {
        this.operatingFrequency = operatingFrequency;
    }

    public double getMaxTurboFrequency() {
        return maxTurboFrequency;
    }

    public void setMaxTurboFrequency(double maxTurboFrequency) {
        this.maxTurboFrequency = maxTurboFrequency;
    }

    @Override
    public String toString() {
        return "ProductCPU{" +
                "processorsType='" + processorsType + '\'' +
                ", socketType='" + socketType + '\'' +
                ", coreName='" + coreName + '\'' +
                ", numOfCores=" + numOfCores +
                ", numOfThreads=" + numOfThreads +
                ", operatingFrequency=" + operatingFrequency +
                ", maxTurboFrequency=" + maxTurboFrequency +
                '}';
    }
}
