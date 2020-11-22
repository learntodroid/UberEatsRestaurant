package com.learntodroid.ubereatsrestaurant.loginsignup;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.learntodroid.ubereatsrestaurant.sharedmodel.Restaurant;
import com.learntodroid.ubereatsrestaurant.sharedmodel.Order;

import java.util.ArrayList;
import java.util.List;

public class UberEatsRestaurantRepository {
    private static final UberEatsRestaurantRepository instance = new UberEatsRestaurantRepository();

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;

    private FirebaseFirestore db;
    private MutableLiveData<String> restaurantIdMutableLiveData;

    private MutableLiveData<List<Restaurant>> restaurantsLiveData;
    private MutableLiveData<Restaurant> selectedRestaurantLiveData;

    private MutableLiveData<List<Order>> ordersLiveData;
    private MutableLiveData<List<String>> orderIdsLiveData;

    public UberEatsRestaurantRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
        }

        this.db = FirebaseFirestore.getInstance();
        this.restaurantIdMutableLiveData = new MutableLiveData<>();
        this.restaurantsLiveData = new MutableLiveData<>();
        this.selectedRestaurantLiveData = new MutableLiveData<>();
        this.ordersLiveData = new MutableLiveData<>();
        this.orderIdsLiveData = new MutableLiveData<>();
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        }
                    }
                });
    }

    public void signUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        }
                    }
                });
    }

    public void createRestaurant(Restaurant restaurant) {
        db.collection("restaurants")
                .add(restaurant)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(UberEatsRestaurantRepository.class.getSimpleName(), "DocumentSnapshot added with ID: " + documentReference.getId());
                        restaurantIdMutableLiveData.postValue(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(UberEatsRestaurantRepository.class.getSimpleName(), "Error adding document", e);
                    }
                });
    }

    public void queryRestaurants() {
        final List<Restaurant> restaurants = new ArrayList<>();

        String userId = firebaseAuth.getUid();

        db.collection("restaurants")
                .whereEqualTo("createdBy", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(UberEatsRestaurantRepository.class.getSimpleName(), document.getId() + " => " + document.getData());
                                Restaurant restaurant = document.toObject(Restaurant.class);
                                restaurants.add(restaurant);
                            }
                            restaurantsLiveData.postValue(restaurants);
                        } else {
                            Log.d(UberEatsRestaurantRepository.class.getSimpleName(), "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void queryOrders(String ordersStatus) {
        final List<Order> orders = new ArrayList<>();
        final List<String> orderIds = new ArrayList<>();

        db.collection("orders")
//                .whereEqualTo("restaurant.title", "McDonald's")
                .whereEqualTo("status", ordersStatus)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(UberEatsRestaurantRepository.class.getSimpleName(), document.getId() + " => " + document.getData());
                                Order order = document.toObject(Order.class);
                                orders.add(order);
                                orderIds.add(document.getId());
                            }
                            ordersLiveData.postValue(orders);
                            orderIdsLiveData.postValue(orderIds);
                        } else {
                            Log.d(UberEatsRestaurantRepository.class.getSimpleName(), "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void updateOrderStatus(Order order, String newStatus) {
        int orderIndex = ordersLiveData.getValue().indexOf(order);
        String orderId = orderIdsLiveData.getValue().get(orderIndex);
        String currentStatus = order.getStatus();

        order.setStatus(newStatus);

        DocumentReference orderRef = db.collection("orders").document(orderId);
        orderRef
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(UberEatsRestaurantRepository.class.getSimpleName(), "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(UberEatsRestaurantRepository.class.getSimpleName(), "Error updating document", e);
                    }
                });

        queryOrders(currentStatus);
    }

    public void setSelectedRestaurant(Restaurant restaurant) {
        selectedRestaurantLiveData.postValue(restaurant);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<List<Restaurant>> getRestaurantsLiveData() {
        return restaurantsLiveData;
    }

    public MutableLiveData<List<Order>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public static UberEatsRestaurantRepository getInstance() {
        return instance;
    }
}
