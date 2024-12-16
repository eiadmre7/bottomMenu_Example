package com.example.buttonmenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFrag extends Fragment {
    private TextInputEditText et_email, et_password;
    private Button btn_submit,btn_signup;
    private TextView tvSignup;
    private FirebaseAuth mAuth;

    public LoginFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        et_email=view.findViewById(R.id.et_Email);
        et_password=view.findViewById(R.id.et_Password);
        btn_submit=view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailPass();
            }
        });
        tvSignup=view.findViewById(R.id.tv_Signup);
        tvSignup.setOnClickListener(View->SignUp());
        return view;
    }
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void SignUp() {
        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
        MainActivity.homeFrame.setVisibility(View.INVISIBLE);
        MainActivity.dashboardFrame.setVisibility(View.INVISIBLE);
        MainActivity.signUpFrame.setVisibility(View.VISIBLE);
    }

    private boolean checkPassword(String password) {
        if (password.length() < 6){
            Toast.makeText(getActivity(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        int c_digit=0, c_upper=0, c_lower=0;
        for (char c:password.toCharArray()) {
            if (Character.isDigit(c)) c_digit++;
            else if (Character.isUpperCase(c)) c_upper++;
            else if (Character.isLowerCase(c)) c_lower++;
        }
        if (c_digit==0 || c_upper==0 || c_lower==0){
            Toast.makeText(getActivity(), "Password must contain at least one digit, one uppercase letter, and one lowercase letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }
    }

    private void checkEmailPass() {
        String email,password;
        email=et_email.getText().toString();
        password=et_password.getText().toString();
        if(!(email.equals("") || password.equals(""))){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "login successful", Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                    else
                        Toast.makeText(getActivity(), "login failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(getActivity(), "please fill in fields..", Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        et_email.setText(null);
        et_password.setText(null);
        MainActivity.isLoggedIn=true;
        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
        MainActivity.homeFrame.setVisibility(View.VISIBLE);
        MainActivity.dashboardFrame.setVisibility(View.INVISIBLE);
    }

}