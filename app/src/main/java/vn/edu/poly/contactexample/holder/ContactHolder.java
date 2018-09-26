package vn.edu.poly.contactexample.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import vn.edu.poly.contactexample.R;

public class ContactHolder extends RecyclerView.ViewHolder {

    public final TextView tvName;
    public final TextView tvPhone;

    public ContactHolder(View convertView) {
        super(convertView);
        tvName = convertView.findViewById(R.id.tvName);
        tvPhone = convertView.findViewById(R.id.tvPhone);
    }

}
