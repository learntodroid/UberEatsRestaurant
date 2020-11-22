package com.learntodroid.ubereatsrestaurant.addeditrestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.learntodroid.ubereatsrestaurant.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddEditRestaurantFragment extends Fragment {
    private AddEditRestaurantViewModel addEditRestaurantViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addEditRestaurantViewModel = new ViewModelProvider(this).get(AddEditRestaurantViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addeditrestaurant, container, false);

        view.findViewById(R.id.fragment_addeditrestaurant_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String img = "https://images.pexels.com/photos/3944308/pexels-photo-3944308.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
                List<String> foodCategories = new ArrayList<>();
                foodCategories.addAll(Arrays.asList(new String[]{"American", "Burger", "Fast Food", "Family Meals"}));
                Restaurant r = new Restaurant("McDonald's", 5.99, 15, 35, 4.2, img, "$", foodCategories, "123 Fake Street, Melbourne", -33.880490, 151.184363, addEditRestaurantViewModel.getUserLiveData().getValue().getUid());
                addEditRestaurantViewModel.createRestaurant(r);
            }
        });

        return view;
    }
}
