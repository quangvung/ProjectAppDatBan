package net.smallacademy.fragmentexample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class AddOfferAdapter extends RecyclerView.Adapter<AddOfferAdapter.OfferViewHolder> {
    private List<AddOfferModel> offerData;
    public static int ps;
    public AddOfferAdapter(List<AddOfferModel> recipeData) {
        this.offerData = recipeData;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OfferViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.add_offer_layout,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        final AddOfferModel offerItem = offerData.get(position);
        holder.offer_title.setText(offerItem.getAdd_recipe_text());
        holder.offerCount.setText(String.valueOf(position+1));
        Glide.with(holder.add_offer_img.getContext())
                .load(offerItem.getAdd_recipe_img())
                .into(holder.add_offer_img);
        holder.rmOfferbtn.setOnClickListener(v -> {
            offerData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, offerData.size());
        });
        holder.add_offer_img.setOnClickListener(v -> {
            ps = position;
            Context context = v.getContext();
            Dexter.withContext(context)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            ((Activity)context).startActivityForResult(Intent.createChooser(intent, "Hãy chọn hình ảnh"),1);
                        }
                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        }
                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();

        });

    }

    @Override
    public int getItemCount() {
        return offerData.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        EditText offer_title;
        ImageView add_offer_img;
        ImageView rmOfferbtn;
        TextView offerCount;

        OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            rmOfferbtn = itemView.findViewById(R.id.removeOffer);
            add_offer_img = itemView.findViewById(R.id.add_offer_img);
            offerCount = itemView.findViewById(R.id.add_offer_count);
            offer_title = itemView.findViewById(R.id.add_title_offer);
            offer_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    offerData.get(getAdapterPosition()).setAdd_recipe_text(offer_title.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }


}
