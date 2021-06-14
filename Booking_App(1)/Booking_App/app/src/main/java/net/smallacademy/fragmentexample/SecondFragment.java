package net.smallacademy.fragmentexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SecondFragment extends Fragment {

    RestaurantAdapter restaurantAdapter;
    RecyclerView recview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        recview = view.findViewById(R.id.recview_search);
        setHasOptionsMenu(true);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
    }
    private void processsearch(String s) {
        FirebaseRecyclerOptions<Restaurant_model> options =
                new FirebaseRecyclerOptions.Builder<Restaurant_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("restaurants").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), Restaurant_model.class)
                        .build();
        restaurantAdapter = new RestaurantAdapter(options);
        restaurantAdapter.startListening();
        recview.setAdapter(restaurantAdapter);
    }
}
