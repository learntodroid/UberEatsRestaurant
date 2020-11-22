package com.learntodroid.ubereatsrestaurant.loginsignup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class LoginSignUpViewModel extends ViewModel {
    private UberEatsRestaurantRepository uberEatsRestaurantRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public LoginSignUpViewModel() {
        uberEatsRestaurantRepository = UberEatsRestaurantRepository.getInstance();
        userLiveData = uberEatsRestaurantRepository.getUserLiveData();
    }

    public void login(String email, String password) {
        uberEatsRestaurantRepository.login(email, password);
    }

    public void signUp(String email, String password) {
        uberEatsRestaurantRepository.signUp(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
