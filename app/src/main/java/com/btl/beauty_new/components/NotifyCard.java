package com.btl.beauty_new.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btl.beauty_new.R;
import com.btl.beauty_new.model.Notify;


public class NotifyCard extends LinearLayout implements BaseComponent{
    TextView tvTitle, tvContent, tvDateMake;
    Notify notify;

    public NotifyCard(Context context) {
        super(context);
    }

    public NotifyCard(Context context, Notify notify) {
        super(context);
        this.notify = notify;
        initControl(context);
    }

    @Override
    public void initUI() {
        tvTitle = findViewById(R.id.tvTitleNotify);
        tvContent = findViewById(R.id.tvContentNotify);
        tvDateMake = findViewById(R.id.tvDateMakeNotify);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_notify_card, this);

        initUI();

        //Set text
        tvTitle.setText(notify.getTitle());
        tvContent.setText(notify.getContent());
        tvDateMake.setText(notify.getDateMake());
    }
}
