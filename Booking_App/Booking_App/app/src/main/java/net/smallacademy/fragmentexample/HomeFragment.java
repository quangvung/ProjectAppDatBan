package net.smallacademy.fragmentexample;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
//    private OnFragmentItemSelectedListener listener;
    private ViewPager2 viewPager2;
    private Handler sliderHander = new Handler();

    String url1 = "https://media-cdn.tripadvisor.com/media/photo-s/0d/c1/21/ed/restaurant-hall-with.jpg";
    String url2 = "https://media-cdn.tripadvisor.com/media/photo-s/18/5f/c7/4d/greenhouse-restaurant.jpg";
    String url3 = "https://cf.bstatic.com/images/hotel/max1024x768/211/211481090.jpg";
    RecyclerView famous_recview,recview;
    Famous_RestaurantAdapter f_adapter;
    RestaurantAdapter r_adapter;
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        // we are creating array list for storing our image urls.
        // initializing the slider view.
        viewPager2 = view.findViewById(R.id.slider);
        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        //passing this array list inside our adapter class

        viewPager2.setAdapter(new SliderAdapter(sliderDataArrayList, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHander.removeCallbacks(slideRunnable);
                sliderHander.postDelayed(slideRunnable, 3000);
            }
        });

        famous_recview =view.findViewById(R.id.famous_recview);
        recview = view.findViewById(R.id.recview);
        famous_recview.setLayoutManager(new GridLayoutManager(getContext(),2));
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Famous_RestaurantModel> options =
                new FirebaseRecyclerOptions.Builder<Famous_RestaurantModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("famous_restaurants"), Famous_RestaurantModel.class)
                        .build();
        FirebaseRecyclerOptions<Restaurant_model> options_ =
                new FirebaseRecyclerOptions.Builder<Restaurant_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("restaurants"), Restaurant_model.class)
                        .build();
        f_adapter = new Famous_RestaurantAdapter(options);
        r_adapter = new RestaurantAdapter(options_);
        famous_recview.setAdapter(f_adapter);
        recview.setAdapter(r_adapter);

        return view;
    }
//
    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem() == sliderDataArrayList.size()-1) viewPager2.setCurrentItem(0);
            else viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        f_adapter.startListening();
        r_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        f_adapter.stopListening();
        r_adapter.stopListening();
    }


}
