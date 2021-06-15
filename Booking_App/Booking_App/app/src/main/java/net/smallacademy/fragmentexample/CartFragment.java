package net.smallacademy.fragmentexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recview;
    BookUserDataAdapter bookUserDataAdapter;
    BookAdminDataAdapter bookAdminDataAdapter;
    ArrayList<Integer> count = new ArrayList<>();
    String name_res;
    int i = 0;
    private ArrayList<BookAdminDataModel> arr_data= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recview = view.findViewById(R.id.book_rec);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        if (LoginActivity.userData.get("role").equals("Admin")){
            FirebaseDatabase.getInstance().getReference("booking").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds : snapshot.getChildren()) {
                        count.add((int) snapshot.child(ds.getKey()).getChildrenCount());
                        FirebaseDatabase.getInstance().getReference("restaurants").child(ds.getKey()).child("name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                name_res = String.valueOf(snapshot.getValue());
                                arr_data.add(new BookAdminDataModel(name_res,String.valueOf(count.get(i++)),ds.getKey()));
                                bookAdminDataAdapter = new BookAdminDataAdapter(arr_data);
                                recview.setAdapter(bookAdminDataAdapter);
                                Log.d("count", String.valueOf(count));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if (LoginActivity.userData.get("role").equals("User")){
            FirebaseRecyclerOptions<BookUserDataModel> options =
                    new FirebaseRecyclerOptions.Builder<BookUserDataModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("booking"), BookUserDataModel.class)
                            .build();

            bookUserDataAdapter = new BookUserDataAdapter(options);
            recview.setAdapter(bookUserDataAdapter);
        }



        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        if (LoginActivity.userData.get("role").equals("User")) bookUserDataAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (LoginActivity.userData.get("role").equals("User")) bookUserDataAdapter.stopListening();
    }
}