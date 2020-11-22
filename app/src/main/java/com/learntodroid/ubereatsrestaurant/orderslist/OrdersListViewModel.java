package com.learntodroid.ubereatsrestaurant.orderslist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learntodroid.ubereatsrestaurant.loginsignup.UberEatsRestaurantRepository;

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

    public MutableLiveData<List<Order>> getOrdersLiveData() {
        return ordersLiveData;
    }
}
