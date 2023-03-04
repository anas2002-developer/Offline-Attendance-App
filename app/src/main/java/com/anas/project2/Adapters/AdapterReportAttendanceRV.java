package com.anas.project2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.project2.Model.ModelReportAttendance;
import com.anas.project2.R;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class AdapterReportAttendanceRV extends RealmRecyclerViewAdapter<ModelReportAttendance, AdapterReportAttendanceRV.ViewHolderRA> {

    Context context;
//    private final Activity mActivity;
    RealmResults<ModelReportAttendance> mList;
    Realm realm;

    String room_id;

    OrderedRealmCollection<ModelReportAttendance> data;
    public AdapterReportAttendanceRV(@Nullable OrderedRealmCollection<ModelReportAttendance> data, String room_id) {
        super(data, true);
        this.data=data;
        this.room_id=room_id;
    }

    @NonNull
    @Override
    public ViewHolderRA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_attendance_row, parent, false);
        return new ViewHolderRA(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRA holder, int position) {
        ModelReportAttendance temp = getItem(position);
        holder.txtRA_name_row.setText(temp.getStud_name());
        holder.txtRA_uid_row.setText(temp.getStud_uid());
        if (temp.getStatus().equals("Present")){
            holder.txtRA_status_row.setText("P");
            holder.cardRA_row.setCardBackgroundColor(context.getResources().getColor(R.color.teal_200));
        }else{
            holder.txtRA_status_row.setText("A");
            holder.cardRA_row.setCardBackgroundColor(context.getResources().getColor(R.color.black));
        }
    }


    public class ViewHolderRA extends RecyclerView.ViewHolder {

        public TextView txtRA_name_row;
        public TextView txtRA_uid_row;
        public TextView txtRA_status_row;
        public CardView cardRA_row;

//        public Activity mActivity;
//        RealmResults<ModelReportAttendance> mList;

        public ViewHolderRA(@NonNull final View itemView) {
            super(itemView);

            txtRA_name_row = itemView.findViewById(R.id.txtRA_name_row);
            txtRA_uid_row = itemView.findViewById(R.id.txtRA_uid_row);
            txtRA_status_row = itemView.findViewById(R.id.txtRA_status_row);
            cardRA_row = itemView.findViewById(R.id.cardRA_row);

        }

    }


}

