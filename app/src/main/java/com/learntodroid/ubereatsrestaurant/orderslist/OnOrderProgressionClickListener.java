package com.learntodroid.ubereatsrestaurant.orderslist;

import com.learntodroid.ubereatsrestaurant.sharedmodel.Order;

public interface OnOrderProgressionClickListener {
    void onOrderAccepted(Order order);
    void onOrderReadyForCollection(Order order);
    void onOrderCollected(Order order);
}
