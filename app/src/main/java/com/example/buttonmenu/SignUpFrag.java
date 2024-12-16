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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


public class SignUpFrag extends Fragment {
    private TextInputEditText etemail, etpassword, etconfirmpassword, etname, etphone,etUsername;
    private String email,password,name,phone,username;
    private Button btnSignup,btnCancel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;



    public SignUpFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        btnSignup=view.findViewById(R.id.btn_signUp);
        btnCancel=view.findViewById(R.id.btn_cancel);
        etemail=view.findViewById(R.id.et_email);
        etpassword=view.findViewById(R.id.et_password);
        etconfirmpassword=view.findViewById(R.id.et_ConfirmPass);
        etname=view.findViewById(R.id.et_name);
        etphone=view.findViewById(R.id.et_phone);
        etUsername=view.findViewById(R.id.et_user_name);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.loginFrame.setVisibility(View.VISIBLE);
                MainActivity.homeFrame.setVisibility(View.INVISIBLE);
                MainActivity.dashboardFrame.setVisibility(View.INVISIBLE);
                MainActivity.signUpFrame.setVisibility(View.INVISIBLE);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
        return view;
    }

    private void SignUp() {
        initStrings();
        if(!(isValidEmail(email))){
            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
            etemail.setError("Invalid Email");
            etemail.requestFocus();
            return;
        }

        if(checkPassword()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getActivity(), "Sign up success.",
                                        Toast.LENGTH_SHORT).show();
                                addUserToFireStore();
                                updateUI();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getActivity(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void addUserToFireStore() {
        User user=new User(name,username,phone,email);
        Map<String, Object> userMap = user.toMap();
        db.collection("users").document(username).set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "User added to Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean checkPassword() {
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
    private void initStrings() {
        name=etname.getText().toString();
        username=etUsername.getText().toString();
        phone=etphone.getText().toString();
        email=etemail.getText().toString();
        password=etpassword.getText().toString();
    }
    private void updateUI() {
        MainActivity.loginFrame.setVisibility(View.VISIBLE);
        MainActivity.signUpFrame.setVisibility(View.INVISIBLE);
        MainActivity.homeFrame.setVisibility(View.INVISIBLE);
        MainActivity.dashboardFrame.setVisibility(View.INVISIBLE);
    }
}