package com.example.companyfb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnAdd;
    private TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        txtResult = findViewById(R.id.txtResult);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child("employees");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee emp = new Employee("Mariam Saleem", "Accounting");

                ref.push().setValue(emp);

            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> emps = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        // Access the data from the child
                        String key = childSnapshot.getKey();
                        Employee emp = childSnapshot.getValue(Employee.class);
                        emps.add(emp.getName());

                    }
                    String str = "";
                    for (String s : emps) {
                        str += s + "\n";
                    }
                    txtResult.setText(str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error999", error.toString());
            }
        });


    }
}