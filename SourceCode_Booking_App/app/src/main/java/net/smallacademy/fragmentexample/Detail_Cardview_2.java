package net.smallacademy.fragmentexample;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Detail_Cardview_2 extends AppCompatActivity {
    TextView test;

    ImageView purlC;
    TextView nameC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__cardview_2);

        String textdata = getIntent().getStringExtra("titleC");
        test = findViewById(R.id.titleC);
        test.setText(textdata);

        String setImage = getIntent().getStringExtra("purlC");
        purlC = findViewById(R.id.purlC);
        Glide.with(purlC).load(setImage).into(purlC);

        String namedata = getIntent().getStringExtra("nameC");
        nameC = findViewById(R.id.nameC);
        nameC.setText(namedata);

    }
}
