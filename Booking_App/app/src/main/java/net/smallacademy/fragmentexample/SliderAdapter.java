package net.smallacademy.fragmentexample;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    // list for storing urls of images.
    private final List<SliderData> sliderData;
    private ViewPager2 viewPager2;

    SliderAdapter(List<SliderData> sliderData, ViewPager2 viewPager2) {
        this.sliderData = sliderData;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_layout,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, final int position) {

        final SliderData sliderItem = sliderData.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into((ImageView) viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return sliderData.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.myimage);
        }
    }
}