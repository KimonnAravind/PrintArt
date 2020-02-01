package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private DatabaseReference TestRef;
    private Button Test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestRef= FirebaseDatabase.getInstance().getReference().child("Test");
        Test = (Button)findViewById(R.id.button);

        getSupportActionBar().setTitle("PrintArt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Working", Toast.LENGTH_SHORT).show();
                HashMap<String,Object> Tester = new HashMap<>();
                Tester.put("Test","Kimonn");
                TestRef.updateChildren(Tester).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}
