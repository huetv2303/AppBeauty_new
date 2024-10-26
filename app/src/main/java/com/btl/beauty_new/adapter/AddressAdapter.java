package com.btl.beauty_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.btl.beauty_new.R;
import com.btl.beauty_new.model.Address;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<Address> {
    private final Context context;
    private final List<Address> addresses;

    public AddressAdapter(Context context, List<Address> addresses) {
        super(context, R.layout.address_item, addresses);
        this.context = context;
        this.addresses = addresses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.address_item, parent, false);
        }

        TextView textViewAddress = convertView.findViewById(R.id.textViewAddress);
        Address address = addresses.get(position);
        textViewAddress.setText(
                "Địa chỉ " + (position + 1) +":" + "\n" +
                "Tên Người nhân: " + address.getNameRecipient() + "\n" +
                "Số điện thoại:" + address.getPhone() + "\n" +
                "Tòa nhà: " + address.getBuilding() + "\n" +
                "Cổng: " + address.getGate() + "\n" +
                "Loại nhà: " + address.getType_address() + "\n" +
                "Ghi chú: " + address.getNote());

        return convertView;
    }
}
