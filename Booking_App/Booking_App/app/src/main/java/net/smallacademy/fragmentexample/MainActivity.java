package net.smallacademy.fragmentexample;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import net.smallacademy.fragmentexample.Drawer.DrawerAdapter;
import net.smallacademy.fragmentexample.Drawer.DrawerItem;
import net.smallacademy.fragmentexample.Drawer.SimpleItem;
import net.smallacademy.fragmentexample.Drawer.SpaceItem;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Delayed;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    Dialog logoutDialog;
    FloatingActionButton fab_add_restaurant;
    BottomNavigationView bottomNavigation;

    private static final int POS_HOME = 0;
    private static final int POS_SEARCH = 1;
    private static final int POS_CART = 2;
    private static final int POS_PERSONAL = 3;
    private static final int POS_LOGOUT = 5;


    private String[] screenTitles;
    private Drawable[] screenIcons;
    SlidingRootNav slidingRootNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        fab_add_restaurant = findViewById(R.id.fab_add_restaurant);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");



        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.search:
                    loadFragment(new SearchFragment());
                    return true;
                case R.id.fav:
                    loadFragment(new FavFragment());
                    return true;
                case R.id.personal:

                    return true;
            }
            return false;
        });



        if (LoginActivity.userData.get("role").equals("Admin")) fab_add_restaurant.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddRestaurantActivity.class)));
        else fab_add_restaurant.setOnClickListener(v -> Toast.makeText(getApplicationContext(),
                "User cant add restaurant",
                Toast.LENGTH_LONG)
                .show());
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withContentClickableWhenMenuOpened(false)
                .inject();
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_SEARCH),
                createItemFor(POS_CART),
                createItemFor(POS_PERSONAL),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment secondFragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,secondFragment);
        fragmentTransaction.commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.onselect))
                .withSelectedTextTint(color(R.color.onselect));
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            logoutDialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            TextView yes,cancel;
            logoutDialog.setContentView(R.layout.logout_layout);
            yes=logoutDialog.findViewById(R.id.yes_logout);
            cancel=logoutDialog.findViewById(R.id.cancel_logout);
            yes.setOnClickListener(v -> {
                FirebaseAuth.getInstance().signOut();
                Intent intent
                        = new Intent(MainActivity.this,
                        LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            });
            cancel.setOnClickListener(v -> logoutDialog.dismiss());
            logoutDialog.show();
        }
        slidingRootNav.closeMenu();
        if(position == POS_SEARCH){
            loadFragment(new SearchFragment());
        }
        if (position == POS_PERSONAL) {
            startActivity(new Intent(MainActivity.this,UserProfile.class));
        }
        if (position== POS_CART) loadFragment(new CartFragment());
        if (position == POS_HOME) {
            loadFragment(new HomeFragment());
        }
    }
    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }
    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }
    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


}
