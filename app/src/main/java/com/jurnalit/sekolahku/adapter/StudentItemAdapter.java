package com.jurnalit.sekolahku.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jurnalit.sekolahku.R;
import com.jurnalit.sekolahku.model.Student;

import org.w3c.dom.Text;

import java.util.List;

public class StudentItemAdapter extends ArrayAdapter<Student> {

    public StudentItemAdapter(@NonNull Context context, @NonNull List<Student> objects) {
        super(context, R.layout.array_adapter_layout, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItemView = convertView;

        if (listItemView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItemView = inflater.inflate(R.layout.array_adapter_layout, parent, false);
        }

        TextView namaLengkap = listItemView.findViewById(R.id.tv_nama);
        TextView gender = listItemView.findViewById(R.id.tv_gender);
        TextView jenjang = listItemView.findViewById(R.id.tv_jenjang);
        TextView noHp = listItemView.findViewById(R.id.tv_no_hp);

        Student student = getItem(position);

        namaLengkap.setText(student.getNamaDepan() + " " + student.getNamaBelakang());
        gender.setText(student.getGender());
        jenjang.setText(student.getJenjang());
        noHp.setText(student.getNoHp());

        return listItemView;

    }

}
