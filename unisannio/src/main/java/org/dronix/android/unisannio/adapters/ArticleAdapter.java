package org.dronix.android.unisannio.adapters;

import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.models.News;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ArticleAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private List<Article> mNewsList;

    public ArticleAdapter(LayoutInflater inflater, List<Article> list) {
        mNewsList = list;
        mInflater = inflater;
    }

    public int getCount() {
        return mNewsList.size();
    }

    public Article getItem(int position) {
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

        Article n = mNewsList.get(position);

        body.setText(n.getTitle());
        date.setText(n.getPubDate());
        return vi;
    }

    public void setNewsList(List<Article> newsList) {
        mNewsList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }
}
