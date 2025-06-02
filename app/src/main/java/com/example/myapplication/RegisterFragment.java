package com.example.myapplication;

import android.app.Person;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        EditText registerEmail = view.findViewById(R.id.registerEmail);
        EditText registerPassword = view.findViewById(R.id.registerPassword);
        EditText registerPhone = view.findViewById(R.id.editTextPhone);
        EditText registerAddress = view.findViewById(R.id.editTextTextPostalAddress);
        EditText registerDescription = view.findViewById(R.id.registerDescription);
        Button registerBtn = view.findViewById(R.id.registerBtn);








        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity != null){
                    String email = registerEmail.getText().toString();
                    String password = registerPassword.getText().toString();
                    String phone = registerPhone.getText().toString();
                    String address =  registerAddress.getText().toString();
                    String description = registerDescription.getText().toString();
                    mainActivity.register(success -> {
                        if (success){
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_homePageFragment);
                            mainActivity.saveRegisteredUser(email,password,phone,address,description);
                        }
                    });
                }
            }
        });

        return view;
    }
}