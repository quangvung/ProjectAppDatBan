package net.smallacademy.fragmentexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;
import java.util.Map;

public class Adapter_fav extends FirebaseRecyclerAdapter<model,Adapter_fav.myviewholder>
{
    Map<String, String> like_data  = new HashMap<>();

    public Adapter_fav(@NonNull FirebaseRecyclerOptions<model> options){super(options);}

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final model model) {
        holder.namefav.setText(model.getName());
        Glide.with(holder.img_fav.getContext()).load(model.getPurl()).into(holder.img_fav);

        holder.img_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context= view.getContext();
                Intent intent = new Intent(context, Detail_CardView.class);
                intent.putExtra("nameC", model.getName());
                intent.putExtra("purl1", model.getPurl1());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_layout, parent, false);
        return new myviewholder(view);
    }

    @Override
    public int getItemCount(){
        return super.getItemCount();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView img_fav;
        CardView img_card;
        TextView namefav;
        public myviewholder(@NonNull View view) {
            super(view);
            img_fav = view.findViewById(R.id.img_fav);
            img_card= view.findViewById(R.id.cardview_fav);
            namefav = view.findViewById(R.id.namw_fav);

        }
    }
}
