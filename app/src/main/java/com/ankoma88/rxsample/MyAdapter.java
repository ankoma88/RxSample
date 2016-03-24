package com.ankoma88.rxsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends ArrayAdapter<ListItem> {
    private static final String TAG = "CardArrayAdapter";
    private List<ListItem> list = new ArrayList<>();

    static class CardViewHolder {
        TextView line1;
        TextView line2;
    }

    public MyAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(ListItem object) {
        list.add(object);
        super.add(object);
    }

  @Override
  public void addAll(Collection<? extends ListItem> data) {
    list.addAll(data);
    super.addAll(data);
  }

  @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public ListItem getItem(int index) {
        return this.list.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.line1);
            viewHolder.line2 = (TextView) row.findViewById(R.id.line2);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        ListItem listItem = getItem(position);
        viewHolder.line1.setText(listItem.getLine1());
        viewHolder.line2.setText(listItem.getLine2());
        return row;
    }

}
