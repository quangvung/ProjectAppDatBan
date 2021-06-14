package net.smallacademy.fragmentexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
    private List<OfferModel> offerData;

    public OfferAdapter(List<OfferModel> offerData) {
        this.offerData = offerData;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new OfferAdapter.OfferViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.offer_layout,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.OfferViewHolder holder, int position) {
        final OfferModel offerItem = offerData.get(position);
        holder.offer_title.setText(offerItem.getOffer_title());
        holder.offer_img.setOnClickListener(v -> {
            BottomSheetDialog menuDialog = new BottomSheetDialog(v.getContext(),R.style.BottomSheetDiaglogTheme);
            menuDialog.setContentView(R.layout.img_bottomsheet_layout);
            ImageView imageView;
            imageView = menuDialog.findViewById(R.id.img_bottomsheet);
            Glide.with(imageView).load(offerItem.getOffer_img()).into(imageView);
            menuDialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return offerData.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        TextView offer_title,offer_img;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            offer_img = itemView.findViewById(R.id.offer_img);
            offer_title = itemView.findViewById(R.id.offer_title);

        }
    }
}
