package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();

        EditText descriptionInput = view.findViewById(R.id.descriptionInput);
        EditText passwordField = mainActivity.findViewById(R.id.loginPassword);  // Get password from login field
        Button uploadButton = view.findViewById(R.id.uploadDes);
        Button getButton = view.findViewById(R.id.getDes);
        TextView descriptionOutput = view.findViewById(R.id.descriptionOutput);

        uploadButton.setOnClickListener(v -> {
            String password = mainActivity.getCurrentUserPassword();
            String description = descriptionInput.getText().toString();
            if (!password.isEmpty()) {
                mainActivity.saveUserString(password, description);
            } else {
                Toast.makeText(mainActivity, "Password required to save description", Toast.LENGTH_SHORT).show();
            }
        });

        getButton.setOnClickListener(v -> {
            String password = mainActivity.getCurrentUserPassword();
            if (!password.isEmpty()) {
                mainActivity.loadUserString(password, value -> descriptionOutput.setText(value));
            } else {
                Toast.makeText(mainActivity, "Password required to load description", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}