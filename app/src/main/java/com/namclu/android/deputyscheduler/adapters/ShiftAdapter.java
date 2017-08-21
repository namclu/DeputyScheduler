package com.namclu.android.deputyscheduler.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namclu.android.deputyscheduler.R;
import com.namclu.android.deputyscheduler.models.Shift;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * Created by namlu on 7/23/2017.
 *
 * Adapter to get shift data and display onto RecyclerView
 */

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ViewHolder>{

    private static final String TAG = ShiftAdapter.class.getSimpleName();

    // Global variables
    private final List<Shift> mShifts;
    private final OnItemClickListener mItemClickListener;

    public ShiftAdapter(List<Shift> shifts, OnItemClickListener onItemClickListener) {
        mShifts = shifts;
        mItemClickListener = onItemClickListener;
    }

    @Override
    public ShiftAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shift_list_content, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ShiftAdapter.ViewHolder holder, int position) {
        final Shift currentShift = mShifts.get(position);

        holder.mShiftId.setText(String.format(Locale.ENGLISH, "%d", currentShift.getId()));
        holder.mTextDate.setText(String.format(Locale.ENGLISH, "%s", currentShift.getStartDate()));
        holder.mTextStartTime.setText(String.format(Locale.ENGLISH, "%s", currentShift.getStartTime()));
        if (!currentShift.getEndTime().isEmpty()) {
            holder.mTextEndTime.setText(String.format(Locale.ENGLISH, "%s", currentShift.getEndTime()));
        } else {
            holder.mTextEndTime.setText(R.string.shift_in_progress);
        }

        new DownloadImageTask(holder.mImageView).execute(currentShift.getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClicked(currentShift);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShifts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mShiftId;
        public final ImageView mImageView;
        public final TextView mTextDate;
        public final TextView mTextStartTime;
        public final TextView mTextEndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mShiftId = (TextView) itemView.findViewById(R.id.text_shift_id);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            mTextDate = (TextView) itemView.findViewById(R.id.text_day);
            mTextStartTime = (TextView) itemView.findViewById(R.id.text_start_time_heading);
            mTextEndTime = (TextView) itemView.findViewById(R.id.text_end_time);
        }
    }

    public interface OnItemClickListener {
        void OnItemClicked(Shift shift);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        final ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
