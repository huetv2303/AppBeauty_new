package com.btl.beauty_new.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.components.NotifyCard;
import com.btl.beauty_new.model.Notify;

import java.util.ArrayList;

public class NotifyFragment extends Fragment {
    private LinearLayout notifyContainer;
    private LinearLayout btnNotifyApps, btnNotifyUser;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public NotifyFragment() {
        // Required empty public constructor
    }

    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_notify, container, false);

        notifyContainer = mainView.findViewById(R.id.layout_notify);
        btnNotifyApps = mainView.findViewById(R.id.btn_notify_apps);
        TextView tvNotifyApps = mainView.findViewById(R.id.tv_notify_apps);
        btnNotifyUser = mainView.findViewById(R.id.btn_notify_user);
        TextView tvNotifyUser = mainView.findViewById(R.id.tv_notify_user);

        btnNotifyApps.setOnClickListener(view -> {
            btnNotifyApps.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tvNotifyApps.setTextColor(Color.WHITE);
            btnNotifyUser.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_white));
            tvNotifyUser.setTextColor(Color.BLACK);

            LoadNotify("apps");
        });

        btnNotifyUser.setOnClickListener(view -> {
            btnNotifyApps.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_white));
            tvNotifyApps.setTextColor(Color.BLACK);
            btnNotifyUser.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tvNotifyUser.setTextColor(Color.WHITE);

            LoadNotify("user");
        });

        LoadNotify("apps");

        return mainView;
    }

    private void LoadNotify(String type) {
        // xóa tất cả thông báo trước đó
        notifyContainer.removeAllViews();

        ArrayList<Notify> listNotify;
        if (type.equals("apps")) {
            listNotify = HomeActivity.dao.getSystemNotify();
        } else {
            listNotify = HomeActivity.dao.getUserNotify(HomeActivity.user.getId());
        }

        for (Notify notify : listNotify) {
            notifyContainer.addView(new NotifyCard(getActivity(), notify));
        }
    }
}
