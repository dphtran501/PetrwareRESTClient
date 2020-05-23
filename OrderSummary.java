public class OrderSummary {
    private String cID;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String country;
    private String address;
    private String cardNumber;
    private String shippingMethod;
    
    public OrderSummary(String cID){
        this.cID = cID;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public String getCountry(){
        return country;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    
    public String getCardNumber(){
        return cardNumber;
    }
    
    public void setShippingMethod(String shippingMethod){
        this.shippingMethod = shippingMethod;
    }
    
    public String getShippingMethod(){
        return shippingMethod;
    }
}
