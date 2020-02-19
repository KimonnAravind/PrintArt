package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.internal.ConnectionErrorMessages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{
    private EditText first;
    private EditText second;
    private EditText third;
    private EditText fourth;
    private EditText fifth;
    private EditText sixth;
    private LinearLayout pinLayout;
    private Button Verify,Verification;
    private EditText PhoneNumber;
    private String OTP="Invalid", Combo;
    private FirebaseAuth mAuth;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("PrintArt Kimonn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        first = (EditText) findViewById(R.id.one);
        second = (EditText) findViewById(R.id.two);
        sixth = (EditText) findViewById(R.id.six);
        third = (EditText) findViewById(R.id.three);
        fourth = (EditText) findViewById(R.id.four);
        fifth = (EditText) findViewById(R.id.five);
        Verify=(Button)findViewById(R.id.verify);
        pinLayout=(LinearLayout) findViewById(R.id.linLayout);
        Verification=(Button)findViewById(R.id.verification);
        PhoneNumber=(EditText)findViewById(R.id.phonenumber);

        mAuth = FirebaseAuth.getInstance();
        {


            first.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        second.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            second.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        third.requestFocus();
                    }
                    if(s.length()<1)
                    {
                        first.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            third.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        fourth.requestFocus();
                    }
                    if(s.length()<1)
                    {
                        second.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            fourth.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        fifth.requestFocus();
                    }
                    if(s.length()<1)
                    {
                        third.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



            fifth.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        sixth.requestFocus();
                    }
                    if(s.length()<1)
                    {
                        fourth.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            sixth.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1)
                    {
                        Verify.performClick();

                    }
                    if(s.length()<1)
                    {
                        fifth.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }

        Verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getOTP();
                pinLayout.setVisibility(View.VISIBLE);
                Verification.setVisibility(View.VISIBLE);
                Verify.setVisibility(View.INVISIBLE);
            }

        });

        Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!first.getText().toString().isEmpty()&&!second.getText().toString().isEmpty()&&!third.getText().toString().isEmpty()&&
                        !fourth.getText().toString().isEmpty()&&!fifth.getText().toString().isEmpty()&&!sixth.getText().toString().isEmpty())
                {
                    Combo = first.getText().toString() + second.getText().toString() + third.getText().toString() +
                            fourth.getText().toString() + fifth.getText().toString() + sixth.getText().toString();
                    Toast.makeText(MainActivity.this, "" + Combo, Toast.LENGTH_SHORT).show();
                    putOTP();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void putOTP()
    { PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, Combo);
            signInWithPhoneAuthCredential(credential); }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            PhoneNumber.setEnabled(false);



                        }
                        else {
                            // Sign in failed, display a message and update the UI
                            Log.w("YESPlease", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void getOTP()
    {   String input=PhoneNumber.getText().toString();

        if (input.isEmpty()||input.length()<10||input.startsWith("+")||input.startsWith("91"))
        {
            PhoneNumber.setError("Enter your 10 digit mobile number");
            return;
        }
        else
        {
            input="+91"+input;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    input,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks

        } }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
        {

        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            OTP = s;
        }


    };




}
