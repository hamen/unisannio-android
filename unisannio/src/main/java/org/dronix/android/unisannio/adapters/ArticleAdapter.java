package org.dronix.android.unisannio.adapters;

import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.fragments.ScienzeAvvisiFragment;
import org.dronix.android.unisannio.models.Article;

import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ArticleAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private final OnClickListener mItemButtonClickListener;

    private List<Article> mNewsList;

    public ArticleAdapter(LayoutInflater inflater, List<Article> list, OnClickListener itemButtonClickListener) {
        mNewsList = list;
        mInflater = inflater;
        mItemButtonClickListener = itemButtonClickListener;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_card, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Article article = getItem(position);

        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPubDate());

        if (mItemButtonClickListener != null) {
            holder.itemButton1.setOnClickListener(mItemButtonClickListener);
            holder.itemButton2.setOnClickListener(mItemButtonClickListener);
        }

        return convertView;
    }


    public void setNewsList(List<Article> newsList) {
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
        Button itemButton1;

        @InjectView(R.id.list_item_card_button_2)
        Button itemButton2;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
