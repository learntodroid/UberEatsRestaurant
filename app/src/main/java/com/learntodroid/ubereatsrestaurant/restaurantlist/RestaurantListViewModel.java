package com.learntodroid.ubereatsrestaurant.restaurantlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learntodroid.ubereatsrestaurant.addeditrestaurant.Restaurant;
import com.learntodroid.ubereatsrestaurant.loginsignup.UberEatsRestaurantRepository;

import java.util.List;

public class RestaurantListViewModel extends ViewModel {
    private UberEatsRestaurantRepository uberEatsRestaurantRepository;
    private MutableLiveData<List<Restaurant>> restaurantsLiveData;

    public RestaurantListViewModel() {
        uberEatsRestaurantRepository = UberEatsRestaurantRepository.getInstance();
        restaurantsLiveData = uberEatsRestaurantRepository.getRestaurantsLiveData();
        uberEatsRestaurantRepository.queryRestaurants();
    }

    public void setSelectedRestaurant(Restaurant restaurant) {
        uberEatsRestaurantRepository.setSelectedRestaurant(restaurant);
    }

    public MutableLiveData<List<Restaurant>> getRestaurantsLiveData() {
        return restaurantsLiveData;
    }
}
