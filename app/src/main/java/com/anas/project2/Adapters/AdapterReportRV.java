package com.anas.project2.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.anas.project2.Model.ModelReport;
import com.anas.project2.R;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class AdapterReportRV extends RealmRecyclerViewAdapter<ModelReport, AdapterReportRV.ViewHolderReports> {

    Context context;
//    private final Activity mActivity;
    RealmResults<ModelReport> mList;
    String room_id;


    Realm realm;

    OrderedRealmCollection<ModelReport> data;
    public AdapterReportRV(@Nullable OrderedRealmCollection<ModelReport> data, String room_id) {
        super(data, true);
        this.data=data;
        this.room_id=room_id;
    }


    @NonNull
    @Override
    public ViewHolderReports onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_row, parent, false);
        return new ViewHolderReports(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReports holder, int position) {
        ModelReport temp = getItem(position);

        Realm.init(context);
        realm = Realm.getDefaultInstance();

        holder.month.setText(temp.getMonthOnly());
        holder.date.setText(temp.getDateOnly());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Add Intent to report attendance", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public class ViewHolderReports extends RecyclerView.ViewHolder {


        public TextView month;
        public TextView date;

        public Activity mActivity;
        RealmResults<ModelReport> mList;

        public ViewHolderReports(@NonNull final View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.txtMonth_row);
            date = itemView.findViewById(R.id.txtDate_row);

        }

    }



}
