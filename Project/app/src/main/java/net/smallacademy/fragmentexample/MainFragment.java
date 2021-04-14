package net.smallacademy.fragmentexample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private OnFragmentItemSelectedListener listener;
    String url1 = "https://thuongtruong24h.vn/wp-content/uploads/2020/08/tch-15839274229471306654924-crop-15839274339272101041463-1597828167997932436972.jpg";
    String url2 = "https://images.foody.vn/brand/s1170x300/foody-upload-api-foody-hco-7543-delivery-now-cover-1170x300[4]-6372-200318174308.jpg";
    String url3 = "https://list.cdnxbvn.com/wp-content/uploads/2019/09/cua-hang-phuc-long.jpg";

    private RecyclerView courseRV;
    // Arraylist for storing data
    private ArrayList<CourseModel> courseModelArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.slider);
        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        courseRV = view.findViewById(R.id.idRVCourse);
        // here we have created new array list and added data to it.
        courseModelArrayList = new ArrayList<>();
        courseModelArrayList.add(new CourseModel("Nha Hang Quang Vu", 4, R.drawable.a));
        courseModelArrayList.add(new CourseModel("Nha Hang Nhi Ha", 3, R.drawable.b));
        courseModelArrayList.add(new CourseModel("Nha Hang Nhi Huynh", 4, R.drawable.c));
        courseModelArrayList.add(new CourseModel("Quang Vu", 4, R.drawable.a));
        courseModelArrayList.add(new CourseModel("Nhi Ha", 4, R.drawable.b));
        courseModelArrayList.add(new CourseModel("Nhi Huynh", 4, R.drawable.c));
        courseModelArrayList.add(new CourseModel("No one No Body :(", 4, R.drawable.a));

        // we are initializing our adapter class and passing our arraylist to it.
        CourseAdapter courseAdapter = new CourseAdapter(getContext(), courseModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentItemSelectedListener){
            listener = (OnFragmentItemSelectedListener) context;

        }else {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public interface  OnFragmentItemSelectedListener{

    }
}
