package com.btl.beauty_new.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.ActivityImpl.AddressActivity;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.activity.ActivityImpl.SignInActivity;
import com.btl.beauty_new.activity.ActivityImpl.SignUpActivity;
import com.btl.beauty_new.activity.ActivityImpl.UserInformationActivity;


public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mainView;
    private Intent intent;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        referenceComponent();
        // Inflate the layout for this fragment
        return mainView;
    }

    private void referenceComponent() {
        //
        LinearLayout user_information = mainView.findViewById(R.id.layout_user_information);
        user_information.setOnClickListener(view -> startActivity(new Intent(getActivity(), UserInformationActivity.class)));



        LinearLayout address = mainView.findViewById(R.id.btn_layout_address);
        address.setOnClickListener(view -> {
                    intent = new Intent(getActivity(), AddressActivity.class);
                    intent.putExtra("request", "address");
                    startActivity(intent);
        });


        LinearLayout history = mainView.findViewById(R.id.account_btn_layout_history);
        history.setOnClickListener(view -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "history");
            startActivity(intent);
        });

        //
        LinearLayout check = mainView.findViewById(R.id.account_btn_layout_check);
        check.setOnClickListener(view -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "check");
            startActivity(intent);
        });

        //
        LinearLayout hint = mainView.findViewById(R.id.account_btn_layout_hint);
        hint.setOnClickListener(view -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "hint");
            startActivity(intent);
        });

        // del account
        LinearLayout delete = mainView.findViewById(R.id.account_btn_layout_delete);
        delete.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("Xác nhận xóa tài khoản");
            dialog.setMessage("Bạn chắc chắn muốn xóa tài khoản ?");
            dialog.setPositiveButton("Có", ((dialogInterface, i) -> {
                HomeActivity.dao.deleteUser(HomeActivity.user.getId());
                SharedPreferences preferences = getActivity().getSharedPreferences("store_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(this.getActivity(),"Bạn đã xóa tài khoản thành công !!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }));
            dialog.setNegativeButton("Không", ((dialogInterface, i) -> {
            }));
            dialog.show();
        });

        // log out
        LinearLayout logout = mainView.findViewById(R.id.account_btn_layout_logout);
        logout.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Bạn có muốn đăng xuất tài khoản ?");
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                Toast.makeText(this.getActivity(), "Đã đăng xuất khỏi hệ thống!", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
                startActivity(new Intent(getActivity(), SignInActivity.class));
            });
            dialog.setNegativeButton("Không", (dialogInterface, i) -> {
            });
            dialog.show();
        });

        TextView txtUser_name = mainView.findViewById(R.id.account_user_name);
        txtUser_name.setText(HomeActivity.user.getName());
    }
}