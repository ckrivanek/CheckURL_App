package com.example.check;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.model.URL;
import com.example.check.model.URLModel;
import com.example.check.model.URLResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

public class searchFragment extends Fragment {
    private static URLResponse anotherList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchFragment() {
        // Required empty public constructor
    }

    public static searchFragment newInstance(String param1, String param2) {
        searchFragment fragment = new searchFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        URL request = new URL(0,"","","","");
        URLModel model = new URLModel();
        //Prefetch for recycler view
        model.getHistory(request, new URLModel.GetHistoryResponse() {
            @Override
            public void response(URLResponse urls) {
                anotherList = urls;
            }

            @Override
            public void error() {
                //TODO
            }
        });


        fragmentView.findViewById(R.id.checkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView errorField = fragmentView.findViewById(R.id.errorTextView);
                errorField.setText("");
                EditText urlField = fragmentView.findViewById(R.id.urlEditText);
                String url = urlField.getText().toString();
                if (url.length()>6 && url.contains(".") && !url.endsWith(".") && ((url.startsWith("http://") && url.charAt(7)!='.') || (url.startsWith("https://") && url.charAt(8)!='.'))) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    Toast.makeText(getActivity(), "Sending Request", Toast.LENGTH_LONG).show();
                    //errorField.setText("Sending Request");
                    Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_resultsFragment, bundle);
                }
                else {
                    errorField.setText("Please use a valid URL");
                }
            }
        });

        fragmentView.findViewById(R.id.historyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchFragment.anotherList!=null){
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String urlListJsonString = gson.toJson(searchFragment.anotherList);
                    bundle.putString("urlList", urlListJsonString);
                    Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_historyFragment, bundle);
                }
            }
        });
        return fragmentView;
    }
}