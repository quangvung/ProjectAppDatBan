package net.smallacademy.fragmentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailRestaurantActivity extends AppCompatActivity {
    private DatabaseReference userref,dbr,likereference,book_ref;
    RecyclerView offer_rec;
    OfferAdapter offerAdapter;
    ArrayList<OfferModel> arrOffer= new ArrayList<>();
    EditText seats;
    ImageView img_i,img_o,img_s,img_c,img_f;
    RelativeLayout layout_img_i,layout_img_o,layout_img_s,layout_img_c,layout_img_f;
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Toolbar toolbar;
    LinearLayout booking_layout;
    List<String> allRatings;
    TextView menu_Show;
    String postkey;
    String name_user;
    ImageView bg_res,like_btn;
    TextView name_res,title_res,local_res,open_time_res,rate_num,phone_res,avg_cost;
    LinearLayout rating_button;
    Map<String,String> restaurent_data,rating,offer_data,book_data;
    public static String where_b="Indoor",person_b="Single";
    public static int pos_w = 1,oldpos_w,pos_h=1,oldpos_h ;
    boolean testclick = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);
        booking_layout = findViewById(R.id.layout_booking);
        toolbar = findViewById(R.id.toolbar);
        menu_Show  = findViewById(R.id.menu_Show);
        name_res = findViewById(R.id.name_res);
        title_res = findViewById(R.id.title_res);
        local_res = findViewById(R.id.location_res);
        phone_res = findViewById(R.id.phone_num_res);
        avg_cost = findViewById(R.id.avg_cost_res);
        rate_num = findViewById(R.id.rate_num);
        like_btn = findViewById(R.id.like_btn);
        open_time_res = findViewById(R.id.open_time_res);
        rating_button = findViewById(R.id.rate_btn);
        bg_res = findViewById(R.id.bg_res);
        offer_rec = findViewById(R.id.offer_rec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //get postkey from adapter
        postkey = getIntent().getStringExtra("postkey");

        toolbar.setNavigationOnClickListener(v -> finish());

        menu_Show.setOnClickListener(v -> {
            BottomSheetDialog menuDialog = new BottomSheetDialog(DetailRestaurantActivity.this,R.style.BottomSheetDiaglogTheme);
            menuDialog.setContentView(R.layout.img_bottomsheet_layout);
            ImageView imageView;
            imageView = menuDialog.findViewById(R.id.img_bottomsheet);
            Glide.with(imageView).load(restaurent_data.get("menu_img")).into(imageView);
            menuDialog.show();
        });
        booking_layout.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DetailRestaurantActivity.this,R.style.BottomSheetDiaglogTheme);
            bottomSheetDialog.setContentView(R.layout.layout_booking);
            TextView submit_btn;
            submit_btn = bottomSheetDialog.findViewById(R.id.submit_btn);
            seats = bottomSheetDialog.findViewById(R.id.seat_number);
            layout_img_i = bottomSheetDialog.findViewById(R.id.layout_img_i);
            layout_img_o = bottomSheetDialog.findViewById(R.id.layout_img_o);
            layout_img_s = bottomSheetDialog.findViewById(R.id.layout_img_s);
            layout_img_c = bottomSheetDialog.findViewById(R.id.layout_img_c);
            layout_img_f = bottomSheetDialog.findViewById(R.id.layout_img_f);
            img_i = bottomSheetDialog.findViewById(R.id.img_i);
            img_o = bottomSheetDialog.findViewById(R.id.img_o);
            img_s = bottomSheetDialog.findViewById(R.id.img_s);
            img_c = bottomSheetDialog.findViewById(R.id.img_c);
            img_f = bottomSheetDialog.findViewById(R.id.img_f);
            where_b="Indoor";
            person_b="Single";
            pos_w = 1;
            pos_h=1;
            FirebaseDatabase.getInstance().getReference("User").child(userID).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name_user = String.valueOf(snapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            submit_btn.setOnClickListener(v1 -> {
                book_data = new HashMap<>();
                if (book_data!=null) book_data.clear();
                book_data.put("Name_res",restaurent_data.get("name"));
                book_data.put("Where",where_b);
                book_data.put("Person",person_b);
                book_data.put("Seats",seats.getText().toString());
                FirebaseDatabase.getInstance().getReference("User").child(userID).child("booking").child(postkey).setValue(book_data);
                book_data.remove("Name_res");
                book_data.put("User_name",name_user);
                book_ref = FirebaseDatabase.getInstance().getReference("booking").child(postkey).child(userID);
                book_ref.setValue(book_data);
                bottomSheetDialog.dismiss();
            });

            layout_img_i.setOnClickListener(v1 -> {
                where_b = "Indoor";
                oldpos_w=pos_w;
                pos_w = 1;
                layout_img_i.setBackgroundResource(R.drawable.bg_booking_1);
                img_i.setColorFilter(Color.parseColor("#ffffff"));
                layout_img_i.setPadding(24,24,24,24);
                deleteold_wHightlight();
            });
            layout_img_o.setOnClickListener(v1 -> {
                where_b = "Outdoor";
                oldpos_w=pos_w;
                pos_w = 2;
                layout_img_o.setBackgroundResource(R.drawable.bg_booking_1);
                img_o.setColorFilter(Color.parseColor("#ffffff"));
                layout_img_o.setPadding(24,24,24,24);
                deleteold_wHightlight();
            });
            layout_img_s.setOnClickListener(v1 -> {
                person_b = "Single";
                oldpos_h=pos_h;
                pos_h = 1;
                layout_img_s.setBackgroundResource(R.drawable.bg_booking_1);
                img_s.setColorFilter(Color.parseColor("#ffffff"));
                layout_img_s.setPadding(24,24,24,24);
                deleteold_hHightlight();
            });
            layout_img_c.setOnClickListener(v1 -> {
                person_b = "Couple";
                oldpos_h=pos_h;
                pos_h = 2;
                layout_img_c.setBackgroundResource(R.drawable.bg_booking_1);
                img_c.setColorFilter(Color.parseColor("#ffffff"));
                layout_img_c.setPadding(24,24,24,24);
                deleteold_hHightlight();
            });
            layout_img_f.setOnClickListener(v1 -> {
                person_b = "Family";
                oldpos_h=pos_h;
                pos_h = 3;
                layout_img_f.setBackgroundResource(R.drawable.bg_booking_1);
                img_f.setColorFilter(Color.parseColor("#ffffff"));
                layout_img_f.setPadding(24,24,24,24);

                deleteold_hHightlight();
            });


            bottomSheetDialog.show();
        });


        //get data from firebase
        dbr = FirebaseDatabase.getInstance().getReference().child("restaurants").child(postkey);
        likereference= FirebaseDatabase.getInstance().getReference("likes");
        userref = FirebaseDatabase.getInstance().getReference("User").child(userID).child("likes");
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurent_data = (Map<String, String>) snapshot.getValue();
                offer_data = (Map<String, String>) snapshot.child("Offer").getValue();
                Log.d("offer", String.valueOf(offer_data));
                assert restaurent_data != null;
                name_res.setText(restaurent_data.get("name"));
                title_res.setText(restaurent_data.get("title"));
                local_res.setText(restaurent_data.get("address"));
                phone_res.setText("Call: " + restaurent_data.get("phone_res"));
                avg_cost.setText("Avg cost - "+restaurent_data.get("avg_cost"));
                Glide.with(bg_res).load(restaurent_data.get("purl")).into(bg_res);

                if(snapshot.child("rating").getValue()!= null) {
                    rating = (Map<String, String>) snapshot.child("rating").getValue();
                    allRatings = new ArrayList<>(rating.values());
                    int ratingCount = allRatings.size();
                    float ratingSum = 0f;
                    for (int i =0;i<ratingCount;i++)
                    {
                        ratingSum += Float.parseFloat(String.valueOf(allRatings.get(i)));
                    }
                    float averageRating = ratingSum / ratingCount;
                    dbr.child("averageRating").setValue(averageRating);
                    rate_num.setText(String.valueOf(Math.floor(averageRating*10)/10));
                }
                int i=0;
                if(offer_data!=null)
                while(offer_data.get("title_offer_"+i)!=null&&offer_data.get("title_offer_"+i)!=""){
                    arrOffer.add(new OfferModel(offer_data.get("title_offer_"+i),offer_data.get("img_offer_"+i)));
                    i++;
                }
                offerAdapter = new OfferAdapter(arrOffer);
                offer_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                offer_rec.setAdapter(offerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rating_button.setOnClickListener(v -> {
            RatingBar ratingBar;
            ImageView img_rating;
            TextView cancel_rate,accept_rate;
            BottomSheetDialog rateDialog = new BottomSheetDialog(DetailRestaurantActivity.this,R.style.BottomSheetDiaglogTheme);
            rateDialog.setContentView(R.layout.rating_layout);
            accept_rate = rateDialog.findViewById(R.id.accept_rate);
            cancel_rate = rateDialog.findViewById(R.id.cancel_rate);
            img_rating = rateDialog.findViewById(R.id.img_rating);
            ratingBar = rateDialog.findViewById(R.id.ratingStar);

            accept_rate.setOnClickListener(v1 -> {
                dbr.child("rating").child(userID).setValue(ratingBar.getRating());
                rateDialog.dismiss();

            });
            cancel_rate.setOnClickListener(v1 -> {
                rateDialog.dismiss();
            });
            Glide.with(getApplicationContext()).load(restaurent_data.get("purl")).into(img_rating);

            rateDialog.show();
        });
        likereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postkey).hasChild(userID))
                {
                    like_btn.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                else
                {
                    like_btn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        like_btn.setOnClickListener(v -> {
            testclick = true;
            likereference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(testclick)
                    {
                        if(snapshot.child(postkey).hasChild(userID))
                        {
                            likereference.child(postkey).child(userID).removeValue();
                            userref.child(postkey).removeValue();
                            testclick=false;
                        }
                        else
                        {
                            likereference.child(postkey).child(userID).setValue(true);
                            userref.child(postkey).setValue(restaurent_data);
                            testclick=false;
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

    }

    private void deleteold_hHightlight() {
        if (oldpos_h != pos_h) {
            switch (oldpos_h) {
                case 1:
                    layout_img_s.setBackgroundResource(R.drawable.bg_booking);
                    img_s.setColorFilter(Color.parseColor("#A7A7A7"));
                    layout_img_s.setPadding(24,24,24,24);

                    break;
                case 2:
                    layout_img_c.setBackgroundResource(R.drawable.bg_booking);
                    img_c.setColorFilter(Color.parseColor("#A7A7A7"));
                    layout_img_c.setPadding(24,24,24,24);

                    break;
                case 3:
                    layout_img_f.setBackgroundResource(R.drawable.bg_booking);
                    img_f.setColorFilter(Color.parseColor("#A7A7A7"));
                    layout_img_f.setPadding(24,24,24,24);

                    break;
            }
        }
    }

    private void deleteold_wHightlight() {
        if (oldpos_w != pos_w) {
            switch (oldpos_w) {
                case 1:
                    layout_img_i.setBackgroundResource(R.drawable.bg_booking);
                    img_i.setColorFilter(Color.parseColor("#A7A7A7"));
                    layout_img_i.setPadding(24,24,24,24);

                    break;
                case 2:
                    layout_img_o.setBackgroundResource(R.drawable.bg_booking);
                    img_o.setColorFilter(Color.parseColor("#A7A7A7"));
                    layout_img_o.setPadding(24,24,24,24);

                    break;
            }
        }
    }
}