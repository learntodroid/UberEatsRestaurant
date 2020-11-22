package com.learntodroid.ubereatsrestaurant.restaurantlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.ubereatsrestaurant.R;
import com.learntodroid.ubereatsrestaurant.sharedmodel.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.RestaurantViewHolder> {
    private List<Restaurant> restaurants;
    private OnRestaurantClickListener listener;

    public RestaurantRecyclerAdapter(OnRestaurantClickListener listener) {
        this.listener = listener;
        this.restaurants = new ArrayList<>();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(restaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.item_restaurant_title);
        }

        public void bind(final Restaurant restaurant) {
            titleTextView.setText(restaurant.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRestaurantClick(restaurant);
                }
            });
        }
    }
}
