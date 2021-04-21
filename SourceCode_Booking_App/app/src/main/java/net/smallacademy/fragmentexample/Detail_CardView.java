package net.smallacademy.fragmentexample;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Detail_CardView extends AppCompatActivity {
    TextView test;
    ImageView purl1;
    TextView nameC;
    ImageView btnn, back_home;
    TextView more_details;
    Animation from_left, from_right, from_bottom;
    RatingBar second_ratingbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__card_view);

        final String textdata = getIntent().getStringExtra("titleC");
        test = findViewById(R.id.titleC);
        test.setText(textdata);

        final String setImage = getIntent().getStringExtra("purl1");
        purl1 = findViewById(R.id.purl1);
        Glide.with(purl1).load(setImage).into(purl1);

        final String namedata = getIntent().getStringExtra("nameC");
        nameC = findViewById(R.id.nameC);
        nameC.setText(namedata);

        back_home = findViewById(R.id.back_home);
        more_details = findViewById(R.id.more_details);
        second_ratingbar = findViewById(R.id.ratingC);

        btnn = findViewById(R.id.btnn);
        btnn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_CardView.this, Detail_Cardview_2.class);
                intent.putExtra("titleC", textdata);
                intent.putExtra("purlC", setImage);
                intent.putExtra("nameC", namedata);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnn, "background_image_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Detail_CardView.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_CardView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Hide status bar and navigation bar at the bottom
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        //Load Animations
        from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);
        from_right = AnimationUtils.loadAnimation(this, R.anim.anim_from_right);
        from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);

        //Set Animations
        back_home.setAnimation(from_left);
        nameC.setAnimation(from_right);
        test.setAnimation(from_right);
        second_ratingbar.setAnimation(from_left);
        btnn.setAnimation(from_bottom);
        more_details.setAnimation(from_bottom);

    }
}