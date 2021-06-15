package net.smallacademy.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BookAdminDataActivity extends AppCompatActivity {
    RecyclerView recview;
    BookAdminInDataAdapter bookAdminInDataAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_admin_data);
        toolbar = findViewById(R.id.toolbar_);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        recview = findViewById(R.id.data_Rec);
        recview.setLayoutManager(new LinearLayoutManager(this));
        String postkey = getIntent().getStringExtra("postkey");
        FirebaseRecyclerOptions<BookAdminInDataModel> options =
                new FirebaseRecyclerOptions.Builder<BookAdminInDataModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("booking").child(postkey), BookAdminInDataModel.class)
                        .build();

        bookAdminInDataAdapter = new BookAdminInDataAdapter(options);
        recview.setAdapter(bookAdminInDataAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        bookAdminInDataAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bookAdminInDataAdapter.stopListening();
    }
}