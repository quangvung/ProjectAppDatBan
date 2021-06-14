package net.smallacademy.fragmentexample;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserProfile extends AppCompatActivity {
    private DatabaseReference userref;
    Toolbar toolbar;
    EditText changeName,changeEmail,changeCountry,changeSex;
    TextView uploadprofile;
    HashMap<String,String> hashMap = new HashMap<>();
    Map<String, String> user;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    final String userid=firebaseUser.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        toolbar = findViewById(R.id.toolbar_user);
        changeName = findViewById(R.id.changeName);
        changeEmail = findViewById(R.id.changeEmail);
        changeCountry = findViewById(R.id.changeAddress);
        changeSex  = findViewById(R.id.changeSex);
        uploadprofile= findViewById(R.id.upload_profile);
        userref = FirebaseDatabase.getInstance().getReference("User").child(userid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                user = (Map<String, String>) dataSnapshot.getValue();
                changeName.setText(user.get("name"));
                changeEmail.setText(user.get("email"));
                changeCountry.setText(user.get("country"));
                changeSex.setText(user.get("sex"));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        uploadprofile.setOnClickListener(v -> {
                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("users");
                hashMap.put("name",changeName.getText().toString());
                hashMap.put("email",changeEmail.getText().toString());
                hashMap.put("sex",changeSex.getText().toString());
                hashMap.put("country",changeCountry.getText().toString());
                dbr.child(userid).setValue(hashMap);
                Toast.makeText(getApplicationContext(),"Update Sucess",Toast.LENGTH_LONG).show();
        });
    }



}