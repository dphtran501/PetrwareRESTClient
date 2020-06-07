package edu.uci.inf124.petrwarerestclient.response;

import edu.uci.inf124.petrwarerestclient.model.Cart;

public class CartResponse {
    private String message;

    private Cart cart;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
