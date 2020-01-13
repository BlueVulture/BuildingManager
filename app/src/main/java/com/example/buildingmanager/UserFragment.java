package com.example.buildingmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserFragment extends Fragment {
    private FirebaseFirestore db;
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        db = FirebaseFirestore.getInstance();
        Bundle arguments = getArguments();
        final String id = arguments.getString("id");
        context = this.getActivity();


        final TextView name = view.findViewById(R.id.user_name_field);
        final EditText email = view.findViewById(R.id.email_edit);
        final EditText phone = view.findViewById(R.id.phone_edit);
        final EditText building = view.findViewById(R.id.building_edit);

        final HashMap<String, Object> toSave = new HashMap<>();

        db.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name.setText(document.get("name").toString() + " " + document.get("lastname").toString());
                        email.setText(document.get("email").toString());
                        phone.setText(document.get("phone").toString());
                        building.setText(document.get("building").toString());
                    }
                }
            }
        });

        Button button = (Button) view.findViewById(R.id.save_edit_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toSave.put("name", name.getText().toString().trim());
                toSave.put("email", email.getText().toString().trim());
                toSave.put("phone", phone.getText().toString().trim());
                toSave.put("building", building.getText().toString().trim());
                db.collection("users").document(id).update(toSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
            }
        });
        return view;
    }
}
