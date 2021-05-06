package net.smallacademy.fragmentexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FavFragment extends Fragment {

    private RecyclerView recview;
    Adapter_fav cardviewadapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,container,false);
        recview = view.findViewById(R.id.fav_rec);
        recview.setLayoutManager(new GridLayoutManager(getContext(),2));
        recview.setNestedScrollingEnabled(false);

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("likes"), model.class)
                        .build();

        cardviewadapter=new Adapter_fav(options);
        recview.setAdapter(cardviewadapter);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cardviewadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cardviewadapter.stopListening();
    }

}
