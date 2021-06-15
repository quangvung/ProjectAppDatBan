package net.smallacademy.fragmentexample;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Adapter_fav extends FirebaseRecyclerAdapter<Restaurant_model,Adapter_fav.myviewholder>
{
    Dialog deleteDialog,successDialog;
    public Adapter_fav(@NonNull FirebaseRecyclerOptions<Restaurant_model> options){super(options);}

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final Restaurant_model model) {
        final String postkey=getRef(position).getKey();
        holder.namefav.setText(model.getName());
        holder.address.setText(model.getAddress());
        Glide.with(holder.img_fav.getContext()).load(model.getPurl()).into(holder.img_fav);
        if (LoginActivity.userData.get("role").equals("User")) holder.more_.setVisibility(View.GONE);
        else holder.more_.setVisibility(View.VISIBLE);
        holder.more_.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.pop_up_card_admin_menu);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.edit_Res:
                        break;
                    case R.id.delete_Res:
                        deleteDialog = new Dialog(v.getContext(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                        successDialog = new Dialog(v.getContext(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                        TextView yes_delete,cancel_delete;
                        deleteDialog.setContentView(R.layout.delete_layout);
                        yes_delete = deleteDialog.findViewById(R.id.yes_delete);
                        cancel_delete = deleteDialog.findViewById(R.id.cancel_delete);
                        yes_delete.setOnClickListener(v1 -> {
                            FirebaseDatabase.getInstance().getReference("restaurants").child(postkey).removeValue();
                            deleteDialog.dismiss();
                            successDialog.setContentView(R.layout.delete_success_layout);
                            successDialog.show();
                            TextView yes_success = successDialog.findViewById(R.id.yes_success);
                            yes_success.setOnClickListener(v2 -> successDialog.dismiss());
                        });
                        cancel_delete.setOnClickListener(v1 -> deleteDialog.dismiss());
                        deleteDialog.show();

                        break;
                }
                return false;
            });
        });
        holder.card.setOnClickListener(view -> {
            Context context= view.getContext();
            Intent intent = new Intent(context, DetailRestaurantActivity.class);
            intent.putExtra("postkey",postkey);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        if (model.getAverageRating()!=null) holder.rating_card.setRating(model.getAverageRating());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.largecard_layout, parent, false);
        return new myviewholder(view);
    }

    @Override
    public int getItemCount(){
        return super.getItemCount();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView img_fav,more_;
        CardView card;
        TextView namefav,address;
        RatingBar rating_card;
        public myviewholder(@NonNull View view) {
            super(view);
            more_ = view.findViewById(R.id.more_option);
            img_fav = view.findViewById(R.id.purl);
            card= view.findViewById(R.id.layout_);
            namefav = view.findViewById(R.id.name_);
            address = view.findViewById(R.id.address_);
            rating_card = itemView.findViewById(R.id.rating_);
        }
    }
}
