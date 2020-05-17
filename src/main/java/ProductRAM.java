public class ProductRAM extends Product {

    private String capacity;
    private String speed;
    private int latency;
    private String timing;
    private String color;
    private String colorLED;

    public ProductRAM(int id) {
        super(id);
    }

    public ProductRAM(Product product) {
        super(product.getId(), product.getModel(), product.getBrand(), product.getName(), product.getSeries(),
                product.getCategory(), product.getPrice(), product.getDescription(), product.getImgSrc());
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorLED() {
        return colorLED;
    }

    public void setColorLED(String colorLED) {
        this.colorLED = colorLED;
    }

    @Override
    public String toString() {
        return "ProductRAM{" +
                "capacity='" + capacity + '\'' +
                ", speed='" + speed + '\'' +
                ", latency=" + latency +
                ", timing='" + timing + '\'' +
                ", color='" + color + '\'' +
                ", colorLED='" + colorLED + '\'' +
                '}';
    }
}
