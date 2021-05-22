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
    private FirebaseDatabase rootNode;
    private DatabaseReference userref;
//    Toolbar toolbar;
    EditText changeName,changeEmail,changeCountry,changeSex;
    LinearLayout changeava;
    Uri filepath;
    Bitmap bitmap;
    TextView uploadprofile;
    ImageView profileImg;
    HashMap<String,String> hashMap = new HashMap<>();
    Map<String, String> user;
    boolean onetime=false;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    final String userid=firebaseUser.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
//        toolbar = findViewById(R.id.toolbar_user);
        changeName = findViewById(R.id.changeName);
        changeEmail = findViewById(R.id.changeEmail);
        changeCountry = findViewById(R.id.changeCountry);
        changeSex  = findViewById(R.id.changeSex);
        changeava = findViewById(R.id.changAvata);
        profileImg = findViewById(R.id.profile_image);
        uploadprofile= findViewById(R.id.upload_profile);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rootNode = FirebaseDatabase.getInstance();
        userref = rootNode.getReference("User").child(userid);
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
                Glide.with(getApplicationContext())
                        .load(user.get("avatarurl"))
                        .into(profileImg);
//                Log.d("user", String.valueOf(user));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        uploadprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filepath!=null&&onetime==false) {
                    onetime=true;
                    uploadtofirebase();
                }
                else {
                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("users");
                    hashMap.put("name",changeName.getText().toString());
                    hashMap.put("email",changeEmail.getText().toString());
                    hashMap.put("sex",changeSex.getText().toString());
                    hashMap.put("country",changeCountry.getText().toString());
                    hashMap.put("avatarurl",user.get("avatarurl"));
                    dbr.child(userid).setValue(hashMap);
                    Toast.makeText(getApplicationContext(),"Update Sucess",Toast.LENGTH_LONG).show();
                }
            }
        });
        changeava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onetime=false;
                Dexter.withContext(UserProfile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Hãy ch?n hình ?nh"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Userprofile.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == -1) {
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);

                Glide.with(this)
                        .load(bitmap)
                        .into(profileImg);

            } catch (Exception ex) {

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void uploadtofirebase() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("C?t nh?t thông tin...");
        dialog.show();
        StorageReference Imagefolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        Random generator = new Random();
        final StorageReference uploader=Imagefolder.child("images/"+generator);
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"C?t nh?t thành công",Toast.LENGTH_LONG).show();
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
    private void StoreLink(String url) {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("User");
        hashMap.put("name",changeName.getText().toString());
        hashMap.put("email",changeEmail.getText().toString());
        hashMap.put("sex",changeSex.getText().toString());
        hashMap.put("country",changeCountry.getText().toString());
        hashMap.put("avatarurl",url);
        dbr.child(userid).setValue(hashMap);

    }


}