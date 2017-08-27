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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by namlu on 7/23/2017.
 *
 * Adapter to get shift data and display onto RecyclerView
 */

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ViewHolder>{

    private static final String TAG = ShiftAdapter.class.getSimpleName();

    // Class variables
    private final List<Shift> mShifts;
    private final OnItemClickListener mItemClickListener;

    // Interfaces
    public interface OnItemClickListener {
        void OnItemClicked(Shift shift);
    }

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
        // Format date as Sun \newline 1 Jan
        holder.mTextDate.setText(String.format(Locale.ENGLISH, "%s",
                new SimpleDateFormat("EEE \nd MMM")
                        .format(convertStringToDate(currentShift.getStartTime()))));
        // Format start time as 12:34 PM
        holder.mTextStartTime.setText(String.format(Locale.ENGLISH, "%s",
                new SimpleDateFormat("hh:mm a")
                        .format(convertStringToDate(currentShift.getStartTime()))));
        // Format end time as 12:34 PM
        if (!currentShift.getEndTime().isEmpty()) {
            holder.mTextEndTime.setText(String.format(Locale.ENGLISH, "%s",
                    new SimpleDateFormat("hh:mm a")
                            .format(convertStringToDate(currentShift.getEndTime()))));
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
            mTextDate = (TextView) itemView.findViewById(R.id.text_date);
            mTextStartTime = (TextView) itemView.findViewById(R.id.text_start_time_heading);
            mTextEndTime = (TextView) itemView.findViewById(R.id.text_end_time);
        }
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

    // Convert date/time String to Date object
    private Date convertStringToDate(String dateTimeString) {
        Date dateTimeStringToDate = null;

        try {
            dateTimeStringToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
                    .parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                dateTimeStringToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                        .parse(dateTimeString);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return dateTimeStringToDate;
    }
}
