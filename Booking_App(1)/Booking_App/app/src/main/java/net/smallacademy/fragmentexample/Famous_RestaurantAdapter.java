package net.smallacademy.fragmentexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Famous_RestaurantAdapter extends FirebaseRecyclerAdapter<Famous_RestaurantModel, Famous_RestaurantAdapter.myviewholder> {

    public Famous_RestaurantAdapter(@NonNull FirebaseRecyclerOptions<Famous_RestaurantModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Famous_RestaurantModel model) {
        final String postkey=getRef(position).getKey();
        Glide.with(holder.imageRestaurent.getContext()).load(model.getPurl()).into(holder.imageRestaurent);
        holder.imageRestaurent.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent
                    = new Intent(context,
                    DetailRestaurantActivity.class);
            intent.putExtra("postkey",postkey);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.famous_card_layout, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        ImageView imageRestaurent;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imageRestaurent = itemView.findViewById(R.id.purl);
        }
    }
}
