package com.example.buildingmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NotificationFragment extends Fragment {
    private FirebaseFirestore db;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        db = FirebaseFirestore.getInstance();
        Bundle arguments = getArguments();
        String id = arguments.getString("id");

        final TextView title = view.findViewById(R.id.notification_title);
        final TextView dateText = view.findViewById(R.id.notification_date);
        final TextView text = view.findViewById(R.id.notification_text);

        db.collection("notices").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Timestamp timestamp = (Timestamp) document.get("date");
                        Date date = timestamp.toDate();
                        title.setText(document.get("title").toString());
                        dateText.setText(date.toString());
                        text.setText(document.get("text").toString());
                    }
                }
            }
        });
        return view;
    }
}

