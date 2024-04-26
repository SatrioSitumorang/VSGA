package com.example.projectsatrio;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsatrio.DBHelper;
import com.example.projectsatrio.R;

public class TopupCursorAdapter extends RecyclerView.Adapter<TopupCursorAdapter.TopupViewHolder> {

    private Cursor mCursor;
    private OnTopupClickListener mListener;

    public interface OnTopupClickListener {
        void onTopupClick(int position);
    }

    public TopupCursorAdapter(Cursor cursor, OnTopupClickListener listener) {
        mCursor = cursor;
        mListener = listener;
    }

    @NonNull
    @Override
    public TopupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_topup, parent, false);
        return new TopupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopupViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String mataUang = mCursor.getString(mCursor.getColumnIndexOrThrow(DBHelper.COLUMN_MATAUANG));
        String keterangan = mCursor.getString(mCursor.getColumnIndexOrThrow(DBHelper.COLUMN_KETERANGAN));

        holder.mataUangTextView.setText(mataUang);
        holder.keteranganTextView.setText(keterangan);
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class TopupViewHolder extends RecyclerView.ViewHolder {

        TextView mataUangTextView;
        TextView keteranganTextView;

        public TopupViewHolder(@NonNull View itemView) {
            super(itemView);
            mataUangTextView = itemView.findViewById(R.id.matauangTextView);
            keteranganTextView = itemView.findViewById(R.id.keteranganTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onTopupClick(position);
                    }
                }
            });
        }
    }
}




