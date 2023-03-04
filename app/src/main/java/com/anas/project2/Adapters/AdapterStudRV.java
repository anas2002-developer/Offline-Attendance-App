package com.anas.project2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.project2.Model.ModelReport;
import com.anas.project2.Model.ModelReportAttendance;
import com.anas.project2.Model.ModelSec;
import com.anas.project2.Model.ModelStud;
import com.anas.project2.R;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class AdapterStudRV extends RealmRecyclerViewAdapter<ModelStud, AdapterStudRV.ViewHolderStud> {


    RealmResults<ModelStud> results;
    Context context;

    String dateNdRoom;

    Realm realm = Realm.getDefaultInstance();
    RealmChangeListener realmChangeListener;


    OrderedRealmCollection<ModelStud> data;
    public AdapterStudRV(OrderedRealmCollection<ModelStud> data, String dateNdRoom) {
        super(data, true);
        this.data=data;
        this.dateNdRoom=dateNdRoom;
    }

    @NonNull
    @Override
    public ViewHolderStud onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stud_row, parent, false);
        return new ViewHolderStud(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderStud holder, int position) {

        ModelStud temp = getItem(position);

        Realm.init(context);
        realm = Realm.getDefaultInstance();

        holder.txtStud_name_row.setText(temp.getStud_name());
        holder.txtStud_uid_row.setText(temp.getStud_uid());


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String uid = preferences.getString(temp.getStud_uid(), null);
        if (uid==null){

        }else {
            if (uid.equals("Present")) {
                holder.radioP.setChecked(true);
            } else {
                holder.radioA.setChecked(true);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Update Dialog Required", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public class ViewHolderStud extends RecyclerView.ViewHolder {

        public final TextView txtStud_name_row;
        public final TextView txtStud_uid_row;
        RadioGroup radioG;
        RadioButton radioP;
        RadioButton radioA;


        public Activity mActivity;
        RealmResults<ModelSec> results;

        public ViewHolderStud(@NonNull View itemView) {
            super(itemView);

            txtStud_name_row = itemView.findViewById(R.id.txtStud_name_row);
            txtStud_uid_row = itemView.findViewById(R.id.txtStud_uid_row);
            radioG = itemView.findViewById(R.id.radioG);
            radioP = itemView.findViewById(R.id.radioP);
            radioA = itemView.findViewById(R.id.radioA);

            Realm.init(context);
            realm = Realm.getDefaultInstance();

            realmChangeListener = new RealmChangeListener() {
                @Override
                public void onChange(Object o) {
                    long reports_size = realm.where(ModelReport.class)
                            .equalTo("date_room_id", dateNdRoom)
                            .count();
                    if (!(reports_size==0)){
                        radioG.setVisibility(View.GONE);
                    }else if (reports_size==0) {
                        radioG.setVisibility(View.VISIBLE);
                    }
                }
            };
            realm.addChangeListener(realmChangeListener);
            long reports_size = realm.where(ModelReport.class)
                    .equalTo("date_room_id", dateNdRoom)
                    .count();
            if (!(reports_size==0)){
                radioG.setVisibility(View.GONE);
            }else if (reports_size==0) {
                radioG.setVisibility(View.VISIBLE);
            }

            radioP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String status = "Present";
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(data.get(getAdapterPosition()).getStud_uid(), status);
                    editor.apply();



                    final ModelReportAttendance model = new ModelReportAttendance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            model.setStud_name(data.get(getAdapterPosition()).getStud_name());
                            model.setStud_uid(data.get(getAdapterPosition()).getStud_uid());
                            model.setStatus(status);
                            model.setRoom_id(data.get(getAdapterPosition()).getRoom_id());
                            model.setDate_room_id(dateNdRoom);
                            model.setUnik_id(data.get(getAdapterPosition()).getStud_uid()+dateNdRoom);

                            realm.copyToRealmOrUpdate(model);
                        }
                    });
                }});

            radioA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String status = "Absent";
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(data.get(getAdapterPosition()).getStud_uid(), status);
                    editor.apply();



                    final ModelReportAttendance model = new ModelReportAttendance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            model.setStud_name(data.get(getAdapterPosition()).getStud_name());
                            model.setStud_uid(data.get(getAdapterPosition()).getStud_uid());
                            model.setStatus(status);
                            model.setRoom_id(data.get(getAdapterPosition()).getRoom_id());
                            model.setDate_room_id(dateNdRoom);
                            model.setUnik_id(data.get(getAdapterPosition()).getStud_uid()+dateNdRoom);

                            realm.copyToRealmOrUpdate(model);
                        }
                    });
                }});

        }
    }

}
