package com.example.app_guru.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app_guru.Ujian;

import java.util.List;

public class UjianAdapter extends ArrayAdapter<Ujian> {
    private Context context;
    private List<Ujian> ujianList;

    public UjianAdapter(Context context, List<Ujian> ujianList) {
        super(context, 0, ujianList);
        this.context = context;
        this.ujianList = ujianList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Ujian currentUjian = ujianList.get(position);

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        text1.setText(currentUjian.getNamaUjian());
        text2.setText("Tanggal: " + currentUjian.getTanggal() + " - Waktu: " + currentUjian.getWaktuMulai() + " - " + currentUjian.getWaktuSelesai());

        return convertView;
    }
}

