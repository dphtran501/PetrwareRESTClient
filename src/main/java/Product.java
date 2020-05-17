public class Product {
    private int id;
    private String model;
    private String brand;
    private String name;
    private String series;
    private String category;
    private double price;
    private String description;
    private String imgSrc;

    public Product(int id) {
        this.id = id;
    }

    public Product(int id, String model, String brand, String name, String series,
                   String category, double price, String description, String imgSrc) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.name = name;
        this.series = series;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", series='" + series + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
