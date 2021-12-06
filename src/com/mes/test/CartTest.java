package com.mes.test;

import com.mes.pojo.Cart;
import com.mes.pojo.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void addItem() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"1234567",1,new BigDecimal(10),new BigDecimal(10)));
        cart.addItem(new CartItem(2,"1234567",1,new BigDecimal(20),new BigDecimal(20)));
        cart.addItem(new CartItem(1,"1852as",3,new BigDecimal(10),new BigDecimal(30)));
        System.out.println(cart);

    }

    @Test
    public void deleteItem() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"1234567",1,new BigDecimal(10),new BigDecimal(10)));
        cart.addItem(new CartItem(2,"1234567",1,new BigDecimal(20),new BigDecimal(20)));
        cart.addItem(new CartItem(1,"1852as",3,new BigDecimal(10),new BigDecimal(30)));
        cart.deleteItem(1);
        System.out.println(cart);
    }

    @Test
    public void clear() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"1234567",1,new BigDecimal(10),new BigDecimal(10)));
        cart.addItem(new CartItem(2,"1234567",1,new BigDecimal(20),new BigDecimal(20)));
        cart.addItem(new CartItem(1,"1852as",3,new BigDecimal(10),new BigDecimal(30)));
        cart.deleteItem(1);
        cart.clear();
        System.out.println(cart);
    }

    @Test
    public void updateCount() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"1234567",1,new BigDecimal(10),new BigDecimal(10)));
        cart.addItem(new CartItem(2,"1234567",1,new BigDecimal(20),new BigDecimal(20)));
        cart.addItem(new CartItem(1,"1852as",3,new BigDecimal(10),new BigDecimal(30)));
        cart.deleteItem(1);
        cart.clear();
        cart.addItem(new CartItem(1,"1852as",3,new BigDecimal(10),new BigDecimal(30)));
        cart.updateCount(1,7);
        System.out.println(cart);
    }
}