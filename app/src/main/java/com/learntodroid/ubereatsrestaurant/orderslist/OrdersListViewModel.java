package com.learntodroid.ubereatsrestaurant.orderslist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learntodroid.ubereatsrestaurant.loginsignup.UberEatsRestaurantRepository;
import com.learntodroid.ubereatsrestaurant.sharedmodel.Order;

import java.util.List;

public class OrdersListViewModel extends ViewModel {
    private UberEatsRestaurantRepository uberEatsRestaurantRepository;
    private MutableLiveData<List<Order>> ordersLiveData;

    public OrdersListViewModel() {
        uberEatsRestaurantRepository = UberEatsRestaurantRepository.getInstance();
        ordersLiveData = uberEatsRestaurantRepository.getOrdersLiveData();
    }

    public void queryOrders(String ordersStatus) {
        uberEatsRestaurantRepository.queryOrders(ordersStatus);
    }

    public void updateOrderStatus(Order order, String newStatus) {
        uberEatsRestaurantRepository.updateOrderStatus(order, newStatus);
    }

    public MutableLiveData<List<Order>> getOrdersLiveData() {
        return ordersLiveData;
    }
}
