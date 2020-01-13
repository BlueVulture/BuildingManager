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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResidentsFragment extends Fragment {
    private FirebaseFirestore db;
    private ListView listView;
    private View view;
    private static final String TAG = "CardListActivity";
    private UserArrayAdapter userArrayAdapter;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_residents, container, false);
        db = FirebaseFirestore.getInstance();
        context = this.getActivity();
        listView = view.findViewById(R.id.residents_array_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card item = (Card) parent.getAdapter().getItem(position);
                Toast.makeText(context, item.getId(), Toast.LENGTH_SHORT).show();
                UserFragment fragment = new UserFragment();
                Bundle arguments = new Bundle();
                arguments.putString("id", item.getId());
                fragment.setArguments(arguments);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        });

        final HashMap<String, Map<String, Object>> queryResults = new HashMap<>();
        userArrayAdapter = new UserArrayAdapter(this.getActivity(), R.layout.user_item_card);

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("QUERYDATA", document.getId() + " => " + document.getData());
                        queryResults.put(document.getId(), document.getData());
                    }

                    for (String s : queryResults.keySet()) {
                        Log.d("CARDS", s);
                        Card card = new Card(queryResults.get(s).get("name").toString() +" "+ queryResults.get(s).get("lastname").toString(), "", s);
                        userArrayAdapter.add(card);
                    }
                    listView.setAdapter(userArrayAdapter);
                }
            }
        });
        return view;
    }
}
