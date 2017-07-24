package com.namclu.android.deputyscheduler.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namclu.android.deputyscheduler.R;
import com.namclu.android.deputyscheduler.models.Shift;

import java.util.List;

/**
 * Created by namlu on 7/23/2017.
 *
 * Adapter to get shift data and display onto RecyclerView
 */

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ViewHolder>{

    private static final String TAG = ShiftAdapter.class.getSimpleName();

    private final List<Shift> mShifts;

    // ShiftAdapter constructor
    public ShiftAdapter(List<Shift> shifts) {
        mShifts = shifts;
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

        holder.mTextCompany.setText(String.format("%d", currentShift.getId()));
        holder.mTextDate.setText(String.format("%s", currentShift.getStartDate()));
        holder.mTextStartTime.setText(String.format("%s", currentShift.getStartTime()));
        if (!currentShift.getEndTime().isEmpty()) {
            holder.mTextEndTime.setText(String.format("%s", currentShift.getEndTime()));
        } else {
            holder.mTextEndTime.setText(R.string.shift_in_progress);
        }
    }

    @Override
    public int getItemCount() {
        return mShifts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextCompany;
        public final TextView mTextDate;
        public final TextView mTextStartTime;
        public final TextView mTextEndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextCompany = (TextView) itemView.findViewById(R.id.text_company_name);
            mTextDate = (TextView) itemView.findViewById(R.id.text_day);
            mTextStartTime = (TextView) itemView.findViewById(R.id.text_start_time_heading);
            mTextEndTime = (TextView) itemView.findViewById(R.id.text_end_time);
        }
    }
}
