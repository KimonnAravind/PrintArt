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

import com.assigned.printart.Paper.PaperStore;
import com.google.android.gms.common.internal.ConnectionErrorMessages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{
    private EditText first;
    private EditText second;
    private EditText third;
    private EditText fourth;
    private EditText fifth;
    private EditText sixth;
    private LinearLayout pinLayout;
    private TextInputLayout two,three,four;
    private Button Verify,Verification;
    private EditText PhoneNumber,name,password,passwords;
    private String OTP="Invalid", Combo;
    private FirebaseAuth mAuth;
    private Button createAcc;
    DatabaseReference UserPortal;
    HashMap<String,Object> EndUsers = new HashMap<>();
    String code;
    int temp=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*getSupportActionBar().setTitle("PrintArt Kimonn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        first = (EditText) findViewById(R.id.one);
        second = (EditText) findViewById(R.id.two);
        sixth = (EditText) findViewById(R.id.six);
        third = (EditText) findViewById(R.id.three);
        fourth = (EditText) findViewById(R.id.four);
        fifth = (EditText) findViewById(R.id.five);
        Verify=(Button)findViewById(R.id.verify);

        Paper.init(this);

        pinLayout=(LinearLayout) findViewById(R.id.linLayout);
        Verification=(Button)findViewById(R.id.verification);
        PhoneNumber=(EditText)findViewById(R.id.phonenumber);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        passwords=(EditText)findViewById(R.id.repassword);
        two=(TextInputLayout)findViewById(R.id.layps);
        three=(TextInputLayout)findViewById(R.id.laypsc);
        four=(TextInputLayout)findViewById(R.id.laypscs);
        createAcc=(Button)findViewById(R.id.create);
        UserPortal=FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();




        {


            first.addTextChangedListener(new TextWatcher()
            {
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
                        if(!first.getText().toString().isEmpty()&&!second.getText().toString().isEmpty()&&!third.getText().toString().isEmpty()&&
                                !fourth.getText().toString().isEmpty()&&!fifth.getText().toString().isEmpty())
                        {
                            /*Verification.performClick();*/
                        }

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


            }

        });

        createAcc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(!PhoneNumber.getText().toString().isEmpty()&&! name.getText().toString().isEmpty()
                        &&!password.getText().toString().isEmpty()&&!passwords.getText().toString().isEmpty())
                {
                    Log.e("one",password.getText().toString());
                    Log.e("two",password.getText().toString());
                    if((password.getText().toString()).equals(passwords.getText().toString()))
                    {
                        if(8<=(password.getText().toString()).length())
                        {Toast.makeText(MainActivity.this, "Ready too Push", Toast.LENGTH_SHORT).show();
                            EndUsers.put("Name",name.getText().toString());
                            EndUsers.put("PhoneNumber",PhoneNumber.getText().toString());
                            EndUsers.put("Password",password.getText().toString());
                            UserPortal.child("EndUsers").child(PhoneNumber.getText().toString()).updateChildren(EndUsers)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Paper.book().write(PaperStore.UserLoginID,PhoneNumber.getText().toString());
                                                Paper.book().write(PaperStore.UserLoginCode,password.getText().toString());
                                                Toast.makeText(MainActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                                startActivity(intent);


                                            }
                                        }
                                    });
                        }
                        else
                        {
                            password.setError("Password should be minimum 8 character!");
                        }
                    }
                    else
                    {
                        password.setError("Password mismatch!");
                        passwords.setError("Password mismatch!");
                    }
                }
                else
                {
                    PhoneNumber.setError("All the fields are mandatory to fill!");
                    name.setError("All the fields are mandatory to fill!");
                    password.setError("All the fields are mandatory to fill!");
                    passwords.setError("All the fields are mandatory to fill!");
                }
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
                            Toast.makeText(MainActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                            PhoneNumber.setEnabled(false);
                            UserPortal.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {

                                    if((dataSnapshot.child("EndUsers").child(PhoneNumber.getText().toString()).exists()))
                                    {
                                        if(temp==1)
                                        {
                                            Paper.book().write(PaperStore.UserLoginID,PhoneNumber.getText().toString());
                                            String pw=dataSnapshot.child("EndUsers")
                                                    .child(PhoneNumber.getText().toString())
                                                    .child("Password").getValue().toString();
                                            Paper.book().write(PaperStore.UserLoginCode,pw);
                                            Toast.makeText(MainActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "New User", Toast.LENGTH_SHORT).show();
                                        two.setVisibility(View.VISIBLE);
                                        three.setVisibility(View.VISIBLE);
                                        four.setVisibility(View.VISIBLE);
                                        Verification.setVisibility(View.INVISIBLE);
                                        createAcc.setVisibility(View.VISIBLE);
                                        temp=2;
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                        else {

                            Toast.makeText(MainActivity.this, "OTP Expired", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
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

            Toast.makeText(MainActivity.this, "You have exceeded the maximum number of try! Please try after 24 hours, or Contact support team!", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            OTP = s;
            Verify.setVisibility(View.INVISIBLE);
            pinLayout.setVisibility(View.VISIBLE);
            Verification.setVisibility(View.VISIBLE);
        }


    };




}
