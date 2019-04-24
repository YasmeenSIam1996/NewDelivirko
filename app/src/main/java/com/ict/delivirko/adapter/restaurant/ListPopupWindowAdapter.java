package com.ict.delivirko.adapter.restaurant;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ict.delivirko.Module.restaurant.places;
import com.ict.delivirko.R;

import java.util.List;


public class ListPopupWindowAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<places> mDataSource;
    private LayoutInflater layoutInflater;
    private OnClickListener onClickListener;

    public ListPopupWindowAdapter(Activity activity, List<places> dataSource, @NonNull OnClickListener onClickListener) {
        this.mActivity = activity;
        this.mDataSource = dataSource;
        layoutInflater = mActivity.getLayoutInflater();
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public String getItem(int position) {
        return mDataSource.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final places places = mDataSource.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_list_popup_window, null);
            holder.tvTitle = convertView.findViewById(R.id.text_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClickButton(position,places);
            }
        });

        // bind data
        holder.tvTitle.setText(getItem(position));
        return convertView;
    }

    public class ViewHolder {
        private TextView tvTitle;
    }

    // interface to return callback to activity
    public interface OnClickListener {
        void onClickButton(int position, places places);
    }
}