package com.learntodroid.ubereatsrestaurant.addeditrestaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.learntodroid.ubereatsrestaurant.loginsignup.UberEatsRestaurantRepository;
import com.learntodroid.ubereatsrestaurant.sharedmodel.Restaurant;

public class AddEditRestaurantViewModel extends ViewModel {
    private UberEatsRestaurantRepository uberEatsRestaurantRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public AddEditRestaurantViewModel() {
        uberEatsRestaurantRepository = UberEatsRestaurantRepository.getInstance();
        userLiveData = uberEatsRestaurantRepository.getUserLiveData();
    }

    public void createRestaurant(Restaurant restaurant) {
        uberEatsRestaurantRepository.createRestaurant(restaurant);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
