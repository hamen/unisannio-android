package org.dronix.android.unisannio.adapters;

import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.models.News;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private final View.OnClickListener mItemButtonClickListener;

    private List<News> mNewsList;

    public NewsAdapter(LayoutInflater inflater, List<News> list, View.OnClickListener itemButtonClickListener) {
        mNewsList = list;
        mInflater = inflater;
        mItemButtonClickListener = itemButtonClickListener;
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

    public View getView(int position, View recycledView, ViewGroup parent) {
        ViewHolder holder;

        if (recycledView == null) {
            recycledView = mInflater.inflate(R.layout.list_item_card, null);

            holder = new ViewHolder(recycledView);
            recycledView.setTag(holder);

        } else {
            holder = (ViewHolder) recycledView.getTag();
        }

        News article = getItem(position);

        holder.title.setText(article.getBody());
        holder.date.setText(article.getDate());

        if (mItemButtonClickListener != null) {
            holder.itemButton1.setOnClickListener(mItemButtonClickListener);
            holder.itemButton2.setOnClickListener(mItemButtonClickListener);
        }

        return recycledView;
    }

    public void setNewsList(List<News> newsList) {
        mNewsList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    static class ViewHolder {

        @InjectView(R.id.date)
        TextView date;

        @InjectView(R.id.title)
        TextView title;

        @InjectView(R.id.list_item_card_button_1)
        LinearLayout itemButton1;

        @InjectView(R.id.list_item_card_button_2)
        LinearLayout itemButton2;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
