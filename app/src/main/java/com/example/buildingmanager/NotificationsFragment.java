package com.example.buildingmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    private FirebaseFirestore db;
    private ListView listView;
    private View view;
    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        db = FirebaseFirestore.getInstance();
        context = this.getActivity();
        listView = view.findViewById(R.id.notifications_array_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card item = (Card) parent.getAdapter().getItem(position);
//                Toast.makeText(context, item.getId(), Toast.LENGTH_SHORT).show();
                NotificationFragment fragment = new NotificationFragment();
                Bundle arguments = new Bundle();
                arguments.putString("id", item.getId());
                fragment.setArguments(arguments);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();

            }
        });

        final HashMap<String, Map<String, Object>> queryResults = new HashMap<>();
        cardArrayAdapter = new CardArrayAdapter(this.getActivity(), R.layout.list_item_card);

        db.collection("notices").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("QUERYDATA", document.getId() + " => " + document.getData());
                        queryResults.put(document.getId(), document.getData());
                    }

                    for (String s : queryResults.keySet()) {
                        Log.d("CARDS", s);
                        Timestamp timestamp = (Timestamp) queryResults.get(s).get("date");
                        Date date = timestamp.toDate();
                        Card card = new Card(queryResults.get(s).get("title").toString(), date.toString(), s);
                        cardArrayAdapter.add(card);
                    }
                    listView.setAdapter(cardArrayAdapter);
                }
            }
        });
        return view;
    }
}
