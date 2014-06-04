package org.dronix.android.unisannio.adapters;

import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.models.News;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private List<News> mNewsList;

    public NewsAdapter(LayoutInflater inflater, List<News> list) {
        mNewsList = list;
        mInflater = inflater;
    }

    public int getCount() {
        return mNewsList.size();
    }

    public News getItem(int position) {
        return mNewsList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = mInflater.inflate(R.layout.list_row, null);
        }

        TextView body = (TextView) vi.findViewById(R.id.title);
        TextView date = (TextView) vi.findViewById(R.id.subtitle);

        News n = mNewsList.get(position);

        // Setting all values in listview
        body.setText(n.getBody());
        date.setText(n.getDate());
        return vi;
    }

    public void setNewsList(List<News> newsList) {
        mNewsList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }
}
