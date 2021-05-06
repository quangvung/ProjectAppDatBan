package net.smallacademy.fragmentexample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private FirebaseAuth mAuth;
    private TextView gotologin;
    private ImageView gotologin1;
    private Button regBtn;
    private TextView regName;
    private TextView regEmail;
    private TextView regPhone;
    private TextView regPass;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        gotologin = findViewById(R.id.gotologin);
        gotologin1 = findViewById(R.id.gotologin1);

        regBtn = findViewById(R.id.btnregister);
        regName = findViewById(R.id.editTextName);
        regEmail= findViewById(R.id.email);
        regPhone= findViewById(R.id.editTextMobile);
        regPass = findViewById(R.id.passwd);

        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RegistrationActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });
        gotologin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RegistrationActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });
        //firebase
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                registerNewUser();
            }
        });
    }

    private void registerNewUser()
    {
        // show the visibility of progress bar to show loading
        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            // if the user created intent to login activity
                            rootNode= FirebaseDatabase.getInstance();
                            reference= rootNode.getReference("User");
                            // get all the value
                            String name = regName.getText().toString();
                            String email = regEmail.getText().toString();
                            String mobile_number = regPhone.getText().toString();
                            String password = regPass.getText().toString();

                            UserHelperClass helperClass = new UserHelperClass(name, email, mobile_number, password);
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(helperClass);
                            Intent intent
                                    = new Intent(RegistrationActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                        }
                    }
                });
    }
}