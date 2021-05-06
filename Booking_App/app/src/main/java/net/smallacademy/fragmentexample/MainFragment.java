package net.smallacademy.fragmentexample;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainFragment extends Fragment {
//    private OnFragmentItemSelectedListener listener;
    private ViewPager2 viewPager2;
    private Handler sliderHander = new Handler();

    String url1 = "https://media-cdn.tripadvisor.com/media/photo-s/0d/c1/21/ed/restaurant-hall-with.jpg";
    String url2 = "https://media-cdn.tripadvisor.com/media/photo-s/18/5f/c7/4d/greenhouse-restaurant.jpg";
    String url3 = "https://cf.bstatic.com/images/hotel/max1024x768/211/211481090.jpg";
    RecyclerView recview;
    myadapter adapter;
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
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

        recview=view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
// cau hinh firebase UI de truyen nhan du lieu den database
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("restaurants"), model.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);

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
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof OnFragmentItemSelectedListener){
//            listener = (OnFragmentItemSelectedListener) context;
//        }else {
//            throw new ClassCastException(context.toString() + " must implement listener");
//        }
//    }
//
//    public interface  OnFragmentItemSelectedListener{
//
//    }

}
