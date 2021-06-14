package net.smallacademy.fragmentexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AddRestaurantActivity extends AppCompatActivity {
    RecyclerView Offer_rec;
    AddOfferAdapter addOfferAdapter;
    Toolbar toolbar;
    TextView upload_btn;
    TextView add_more_offer;
    Uri filepath, bg_restaurant,menu_img;
    int i,count=0;
    String bg_url,menu_url,postkey;
    ArrayList<Uri> imageList= new ArrayList<>();
    Bitmap bitmap,camera_icon;
    ImageView add_offer_img,add_restaurant_bg,add_menu_img;
    HashMap<String,String> hashMap = new HashMap<>();
    HashMap<String,String> Offer_hashMap = new HashMap<>();
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    final String userID=firebaseUser.getUid();
    EditText name_res,title_res,location_res,from_time,to_time,phone_num_res, avg_cost_res;
    boolean flagimg_bg =false,flagimg_menu=false;
    boolean flagupload=false;
    private ArrayList<AddOfferModel> arrOffer= new ArrayList<>();
    DatabaseReference restaurant_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrestaurant);
        toolbar = findViewById(R.id.toolbar);
        add_offer_img = findViewById(R.id.add_offer_img);
        add_menu_img = findViewById(R.id.add_menu_img);
        upload_btn = findViewById(R.id.upload_btn);
        add_more_offer = findViewById(R.id.add_more_offer);
        add_restaurant_bg = findViewById(R.id.addrestaurantbg);
        name_res = findViewById(R.id.name_res);
        avg_cost_res = findViewById(R.id.avg_cost_res_text);
        title_res = findViewById(R.id.title_res);
        location_res = findViewById(R.id.location_res);
        from_time = findViewById(R.id.from_time);
        to_time = findViewById(R.id.to_time);
        Offer_rec = findViewById(R.id.add_offer_rec);
        phone_num_res = findViewById(R.id.phone_num_res);
        restaurant_id = FirebaseDatabase.getInstance().getReference("User").child(userID).child("restaurant_id");
        camera_icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.camera_);
        arrOffer.add(new AddOfferModel("",camera_icon));
        buildOfferRecyclerView();

        add_more_offer.setOnClickListener((View.OnClickListener) v -> {
            arrOffer.add(new AddOfferModel("",camera_icon));
            buildOfferRecyclerView();
        });
        upload_btn.setOnClickListener(v -> {
            if (filepath==null) Toast.makeText(getApplicationContext(),"Pick your restaurant background",Toast.LENGTH_LONG).show();
            else{
                imageList.add(bg_restaurant);
                imageList.add(menu_img);
                uploadtofirebase();
            }

        });


        add_restaurant_bg.setOnClickListener(v -> {
            flagimg_bg =true;
            Dexter.withContext(AddRestaurantActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, ""),1);
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
        add_menu_img.setOnClickListener(v -> {
            flagimg_menu =true;
            Dexter.withContext(AddRestaurantActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, ""),1);
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

        //show back arrow
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    private void uploadtofirebase() {
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Uploading");
        dialog.show();
        StorageReference Imagefolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for (i=0;i<imageList.size();i++){
            Uri imgCount=imageList.get(i);
            final StorageReference uploader=Imagefolder.child("image"+imgCount.getLastPathSegment());
            uploader.putFile(imgCount)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"File Uploaded",Toast.LENGTH_LONG).show();
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    StoreLink(url);
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            dialog.setMessage("Uploader :"+(int)percent+"%");
                        }
                    });
        }

    }
    private void StoreLink(String url) {
        ProgressDialog dialog=new ProgressDialog(this);

        Offer_hashMap.put("img_offer_"+count,url);
        if(count==i-1) {
            count = 0;
            flagupload=true;
        }
        else count++;
        if(flagupload==true) {
            Intent intent = new Intent(AddRestaurantActivity.this,MainActivity.class);
            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("restaurants");
            menu_url = Offer_hashMap.get("img_offer_" + (i - 1));
            Offer_hashMap.remove("img_offer_" + (i - 1));
            bg_url = Offer_hashMap.get("img_offer_" + (i - 2));
            Offer_hashMap.remove("img_offer_" + (i - 2));
            hashMap.put("menu_img", menu_url);
            hashMap.put("purl", bg_url);
            for (int x=0;x<arrOffer.size();x++){
                Offer_hashMap.put("title_offer_"+x,arrOffer.get(x).getAdd_recipe_text());
            }
            hashMap.put("name",name_res.getText().toString());
            hashMap.put("Uid",firebaseUser.getUid());
            hashMap.put("time_open",from_time.getText().toString()+" - "+to_time.getText().toString());
            hashMap.put("title",title_res.getText().toString());
            hashMap.put("phone_res",phone_num_res.getText().toString());
            hashMap.put("address",location_res.getText().toString());
            hashMap.put("avg_cost", avg_cost_res.getText().toString());
            String id= UUID.randomUUID().toString();
            restaurant_id.child(id).setValue(hashMap);
            dbr.child(id).setValue(hashMap);
            restaurant_id.child(id).setValue(true);
            dbr.child(id).child("Offer").setValue(Offer_hashMap);
            flagupload=false;
            dialog.dismiss();
            dialog.setMessage("Uploaded Successfully");
            startActivity(intent);
        }
    }
    private void buildOfferRecyclerView() {

        Offer_rec.setLayoutManager(new LinearLayoutManager(this));
        addOfferAdapter = new AddOfferAdapter(arrOffer);
        Offer_rec.setAdapter(addOfferAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == -1) {
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);

                if (flagimg_bg){
                    Glide.with(this)
                            .load(bitmap)
                            .into(add_restaurant_bg);
                    flagimg_bg =false;
                    bg_restaurant = filepath;
                }
                else if (flagimg_menu){
                    Glide.with(this)
                            .load(bitmap)
                            .into(add_menu_img);
                    flagimg_menu =false;
                    menu_img = filepath;
                }
                else {
                    int position = AddOfferAdapter.ps;
                    arrOffer.get(AddOfferAdapter.ps).setAdd_recipe_img(bitmap);
                    imageList.add(position,filepath);
                }

                buildOfferRecyclerView();
            } catch (Exception ex) {

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}