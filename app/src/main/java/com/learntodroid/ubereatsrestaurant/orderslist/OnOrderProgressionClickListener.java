package com.learntodroid.ubereatsrestaurant.orderslist;

public interface OnOrderProgressionClickListener {
    void onOrderAccepted(Order order);
    void onOrderReadyForCollection(Order order);
    void onOrderCollected(Order order);
}
