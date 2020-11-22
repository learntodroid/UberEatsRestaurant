package com.learntodroid.ubereatsrestaurant.restaurantlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.ubereatsrestaurant.R;
import com.learntodroid.ubereatsrestaurant.sharedmodel.Restaurant;

import java.util.List;

public class RestaurantListFragment extends Fragment implements OnRestaurantClickListener {
    private RestaurantListViewModel restaurantListViewModel;
    private RestaurantRecyclerAdapter restaurantRecyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restaurantListViewModel = new ViewModelProvider(this).get(RestaurantListViewModel.class);
        restaurantRecyclerAdapter = new RestaurantRecyclerAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurantlist, container, false);

        restaurantListViewModel.getRestaurantsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                if (restaurants != null) {
                    Log.i(RestaurantListFragment.class.getSimpleName(), "Restaurants: " + restaurants.size());
                    restaurantRecyclerAdapter.setRestaurants(restaurants);
                }
            }
        });

        RecyclerView restaurantsRecyclerView = view.findViewById(R.id.fragment_restaurantlist_restaurants);
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        restaurantsRecyclerView.setAdapter(restaurantRecyclerAdapter);

        return view;
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant) {
        restaurantListViewModel.setSelectedRestaurant(restaurant);
        Navigation.findNavController(getView()).navigate(R.id.action_restaurantListFragment_to_addEditRestaurantFragment);
    }
}
