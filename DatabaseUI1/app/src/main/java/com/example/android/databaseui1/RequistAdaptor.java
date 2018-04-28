package com.example.android.databaseui1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RequistAdaptor extends ArrayAdapter<Request> {
    private Request req;
    private ArrayList<Request> list;

    public RequistAdaptor(@NonNull Context context, ArrayList<Request> list) {
        super(context,0 , list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        req = getItem(position);
        View listView = convertView;
        if (listView==null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        TextView name = (TextView) listView.findViewById(R.id.requesterName);
        TextView hospitalName = (TextView) listView.findViewById(R.id.hospitalName);
        TextView hospitalAddress = (TextView) listView.findViewById(R.id.hospitalAddress);
        TextView bloodType = (TextView) listView.findViewById(R.id.requesterBloodType);
        name.setText(req.getUserName());
        hospitalName.setText(req.getHospitalName());
        hospitalAddress.setText(req.getHospitalAddress());
        bloodType.setText(req.getBloodType());
        return listView;
    }
}
