package com.example.check;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.check.model.URLModel;
import com.example.check.model.URL;
import com.example.check.model.URLResponse;
import com.google.gson.Gson;

public class resultsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public resultsFragment() {
        // Required empty public constructor
    }

    public static resultsFragment newInstance(String param1, String param2) {
        resultsFragment fragment = new resultsFragment();
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
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        TextView urlTextView = view.findViewById(R.id.urlTextView);
        TextView maliciousTextView = view.findViewById(R.id.maliciousTextView);

        if (getArguments().get("url")!=null){
            String submittedURL = getArguments().getString("url");
            urlTextView.setText(submittedURL);

            //Attempted request for posting a URL
            URL requestUrl = new URL(0,submittedURL, "", "", "");
            URLModel model = new URLModel();
            model.checkURL(requestUrl, new URLModel.GetCheckResponse() {
                @Override
                public void response(URLResponse urls) {
                    if (urls.data!=null){
                        URL entry  = urls.data.get(0);
                        if (entry.malicious=="false"){
                            maliciousTextView.setText("Clean");
                        }
                        else {
                            maliciousTextView.setText("Malicious");
                        }
                    }
                    else{
                        maliciousTextView.setText(urls.error);
                    }
                }

                @Override
                public void error() {
                    //TODO
                }
            });
        }
        else if(getArguments().get("urlHistory")!=null){
            String historyResponseJSON = getArguments().getString("urlHistory");
            Gson gson = new Gson();
            URLResponse historyResponse = gson.fromJson(historyResponseJSON, URLResponse.class);
            URL history = historyResponse.data.get(0);
            if (history.url!=null){
                urlTextView.setText(history.url);
            }
            if (history.malicious!=null){
                if (history.malicious.length()==0){
                    maliciousTextView.setText("Clean");
                }
                else{
                    maliciousTextView.setText("Malicious");
                }
            }
            else {
                maliciousTextView.setText("An unknown error occurred");
            }
        }

        view.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        return view;
    }
}