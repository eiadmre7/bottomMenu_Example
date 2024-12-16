package com.example.buttonmenu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private LoginFrag loginFrag;
    public static boolean isLoggedIn=false;
    private DashBoardFrag dashBoardFrag;
    private HomeFrag homeFrag;
    private SignUpFrag signupFragment;
    public static FrameLayout loginFrame,signUpFrame, homeFrame, dashboardFrame;
    private BottomNavigationView bottomnavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFrame=findViewById(R.id.LoginFrame);
        signUpFrame=findViewById(R.id.SignupFrame);
        homeFrame=findViewById(R.id.HomeFrame);
        dashboardFrame=findViewById(R.id.dashboardFrame);
        bottomnavigation=findViewById(R.id.bottomnavigation);
        start();
    }

    private void start() {
        homeFrag = new HomeFrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrame, homeFrag).commit();
        dashBoardFrag = new DashBoardFrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboardFrame, dashBoardFrag).commit();
        loginFrag = new LoginFrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.LoginFrame, loginFrag).commit();
        signupFragment = new SignUpFrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.SignupFrame, signupFragment).commit();

        // Hide other fragments initially
        dashboardFrame.setVisibility(View.INVISIBLE);
        homeFrame.setVisibility(View.INVISIBLE);
        signUpFrame.setVisibility(View.INVISIBLE);

        // Set up bottom navigation view listener
        bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.menu_home && isLoggedIn) {
                    // Show Home fragment
                    dashboardFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    homeFrame.setVisibility(View.VISIBLE);
                }
                if (item.getItemId()==R.id.menu_dashboard && isLoggedIn)
                {
                    // Show Dashboard fragment
                    homeFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    dashboardFrame.setVisibility(View.VISIBLE);
                }
                if (item.getItemId()==R.id.menu_login && !isLoggedIn) {
                    // Show Login fragment
                    homeFrame.setVisibility(View.INVISIBLE);
                    dashboardFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }
}