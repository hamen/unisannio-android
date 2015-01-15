package org.dronix.android.unisannio.nagivation_drawer;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class NavigationDrawerAdapter extends ArrayAdapter<NavigationDrawerItem> {

    Context mContext;

    int mLayoutResourceId;

    NavigationDrawerItem mDrawerItems[] = null;

    @Setter
    private int mSelected;

    public NavigationDrawerAdapter(Context context, int layoutResourceId, NavigationDrawerItem[] data) {
        super(context, layoutResourceId, data);
        mLayoutResourceId = layoutResourceId;
        mContext = context;
        mDrawerItems = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = ((MainActivity) mContext).getLayoutInflater().inflate(mLayoutResourceId, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NavigationDrawerItem item = getItem(position);

        holder.text.setText(item.getName());
        holder.icon.setImageResource(item.getIcon());
        int color;

        if (position == mSelected) {
            color = mContext.getResources().getColor(R.color.white);
        } else {
            color = mContext.getResources().getColor(R.color.unisannio_blue);
        }
        holder.text.setTextColor(color);

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.navigation_drawer_item_icon)
        ImageView icon;

        @InjectView(R.id.navigation_drawer_item_text)
        TextView text;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}