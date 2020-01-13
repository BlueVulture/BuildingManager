package com.example.buildingmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TaskArrayAdapter extends ArrayAdapter<Card> {
    private static final String TAG = "CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();

    public TaskArrayAdapter(@NonNull Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    static class CardViewHolder {
        TextView line1;
        TextView line2;
        ImageView imageView;
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.task_item_card, container, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.line1);
            viewHolder.line2 = (TextView) row.findViewById(R.id.line2);
            viewHolder.imageView = (ImageView) row.findViewById(R.id.complete_image_view);


            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        viewHolder.line1.setText(card.getLine1());
        viewHolder.line2.setText(card.getLine2());
        if(!card.getStatus().isEmpty()) {
            if(card.getStatus().equals("F")) {
                viewHolder.imageView.setImageResource(R.drawable.icon_check);
            } else {
                viewHolder.imageView.setImageResource(R.drawable.icon_close);
            }
        }

        return row;
    }
}
