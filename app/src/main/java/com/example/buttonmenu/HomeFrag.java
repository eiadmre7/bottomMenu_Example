package com.example.buttonmenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFrag extends Fragment {
    private TextInputEditText etName;
    private TextView tvResult;
    private FloatingActionButton btnLogout;

    public HomeFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        etName=view.findViewById(R.id.et_password);
        tvResult=view.findViewById(R.id.tv_result);
        btnLogout=view.findViewById(R.id.logOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log_out();
            }
        });
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText().toString();
                tvResult.setText(name);
                etName.setText(null);
            }
        });
        return view;
    }

    private void Log_out() {
        FirebaseAuth.getInstance().signOut();
        MainActivity.isLoggedIn=false;
        MainActivity.loginFrame.setVisibility(View.VISIBLE);
        MainActivity.homeFrame.setVisibility(View.INVISIBLE);
        MainActivity.dashboardFrame.setVisibility(View.INVISIBLE);
    }

}