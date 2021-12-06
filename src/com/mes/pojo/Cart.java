package com.mes.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 */
public class Cart {
//    public Integer totalCount;
//    public BigDecimal totalPrice;
//    public List<CartItem> items = new ArrayList<CartItem>();
    // 将items 做成Map形式，这样就可以直接在Map中查找指定id对应的商品项，不用进行遍历查找
    public Map<Integer,CartItem> items = new LinkedHashMap<Integer,CartItem>();
    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        // 1. 先检查购物车中是否添加过产品，如果添加了就数量加一，如果没有就添加一项
        CartItem Item = items.get(cartItem.getId());
        if (Item == null){
            //如果没有添加
            items.put(cartItem.getId(),cartItem);
        }else {
            // 如果已经添加了
            //将产品数量加一
            Item.setCount(Item.getCount()+ cartItem.count);
            // 然后计算产品总金额
            Item.setTotalPrice(Item.getPrice().multiply(new BigDecimal(Item.getCount())));
        }
    }
    /**
     * 删除商品项
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }


    /**
     * 清空购物车
     *
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     */
    public void updateCount(Integer id,Integer count){
        // 先拿到商品
        CartItem cartItem = items.get(id);
        if(cartItem != null){
            // 如果商品存在就更新商品数量，更改商品总金额
            cartItem.setCount(count);
            // 然后计算产品总金额
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));

        }
    }


    public Cart() {
    }

    public Cart(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }


    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }

        return totalPrice;
    }



    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
