package org.dronix.android.unisannio.ingegneria;

import org.dronix.android.unisannio.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class IngegneriaRssItemAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private final OnClickListener mItemButtonClickListener;

    private List<IngegneriaRssItem> mNewsList;

    public IngegneriaRssItemAdapter(LayoutInflater inflater, List<IngegneriaRssItem> list, OnClickListener itemButtonClickListener) {
        mNewsList = list;
        mInflater = inflater;
        mItemButtonClickListener = itemButtonClickListener;
    }

    public int getCount() {
        return mNewsList.size();
    }

    public IngegneriaRssItem getItem(int position) {
        return mNewsList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_card, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        IngegneriaRssItem article = getItem(position);

        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPubDate().toString());

        if (mItemButtonClickListener != null) {
            holder.itemButton1.setOnClickListener(mItemButtonClickListener);
            holder.itemButton2.setOnClickListener(mItemButtonClickListener);
        }

        return convertView;
    }


    public void setNewsList(List<IngegneriaRssItem> newsList) {
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
