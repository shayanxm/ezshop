package com.example.shayanmoradi.ezshop.Model.orderingModels;

import java.util.List;

public class OrderJsonBody {

private Biling biling;
    private List<Order> line_items;
private  List<Coupon> coupon_lines;
    private int customer_id;

    public OrderJsonBody(Biling biling, List<Order> line_items, List<Coupon> coupon_lines, int customer_id) {
        this.biling = biling;
        this.line_items = line_items;
        this.coupon_lines = coupon_lines;
        this.customer_id = customer_id;
    }
}
