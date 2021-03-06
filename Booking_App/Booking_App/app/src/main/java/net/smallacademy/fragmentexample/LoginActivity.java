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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private Button Btn;

    public static Map<String,String> userData;
    private FirebaseAuth mAuth;
    private TextView regis;
    private ImageView regis1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.login);

        regis = findViewById(R.id.regis);
        regis1 = findViewById(R.id.regis1);
        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(LoginActivity.this,
                        RegistrationActivity.class);
                startActivity(intent);
            }
        });
        regis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(LoginActivity.this,
                        RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUserAccount()
    {
        // show the visibility of progress bar to show loading
        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password // check th trong
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

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Login successful!!",
                                        Toast.LENGTH_LONG)
                                        .show();
                                // hide the progress bar
                                // if sign-in is successful
                                // intent to home activity
                                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference("User").child(userID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        userData = (Map<String,String>) snapshot.getValue();
                                        Intent intent
                                                = new Intent(LoginActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            else {
                                // sign-in failed
                                Toast.makeText(getApplicationContext(),
                                        "Login failed!!",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
    }
}