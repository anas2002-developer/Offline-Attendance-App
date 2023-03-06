package com.anas.project2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.project2.Model.ModelSec;
import com.anas.project2.R;
import com.anas.project2.StudActivity;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class AdapterSecRV extends RealmRecyclerViewAdapter<ModelSec, AdapterSecRV.ViewHolderSec> {


//    private final Activity mActivity;
    RealmResults<ModelSec> results;
    Context context;

    Realm realm;
    RealmChangeListener realmChangeListener;


    OrderedRealmCollection<ModelSec> data;
    public AdapterSecRV(OrderedRealmCollection<ModelSec> data) {
        super(data, true);
        this.data=data;
    }


    @NonNull
    @Override
    public ViewHolderSec onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sec_row, parent, false);
        return new ViewHolderSec(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSec holder, int position) {

        final ModelSec temp = getItem(position);

        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
//        realmChangeListener = new RealmChangeListener() {
//            @Override
//            public void onChange(Object o) {
//                long count = realm.where(Students_List.class)
//                        .equalTo("class_id", temp.getId())
//                        .count();
//                holder.total_students.setText("Students : " + count);
//            }
//        };
//        realm.addChangeListener(realmChangeListener);

//        long count = realm.where(Students_List.class)
//                .equalTo("class_id", temp.getId())
//                .count();
//        holder.total_students.setText("Students : " + count);
        holder.txtSec_name_row.setText(temp.getSec_name());
        holder.txtSub_name_row.setText(temp.getSub_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, StudActivity.class);
                i.putExtra("sec_name",temp.getSec_name());
                i.putExtra("sub_name",temp.getSub_name());
                i.putExtra("room_id",temp.getId());
                context.startActivity(i);
            }
        });

    }


    public class ViewHolderSec extends RecyclerView.ViewHolder {

        public final TextView txtSec_name_row;
        public final TextView txtSub_name_row;


        public ViewHolderSec(@NonNull View itemView) {
            super(itemView);

            txtSec_name_row = itemView.findViewById(R.id.txtSec_name_row);
            txtSub_name_row = itemView.findViewById(R.id.txtSub_name_row);


        }
    }

}
