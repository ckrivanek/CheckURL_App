package com.example.check;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.check.model.URL;
import com.example.check.model.URLModel;
import com.example.check.model.URLResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

public class historyFragment extends Fragment implements MyItemRecyclerViewAdapter.AdapterDelegate{
    public static URLResponse urlList = new URLResponse(new ArrayList<URL>(), 0, "");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public historyFragment() {
        // Required empty public constructor
    }

    public static historyFragment newInstance(String param1, String param2) {
        historyFragment fragment = new historyFragment();
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
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        URL request = new URL();
        URLModel model = new URLModel();
        model.getHistory(request, new URLModel.GetHistoryResponse() {
            @Override
            public void response(URLResponse urls) {
                urlList = urls;
            }

            @Override
            public void error() {
                //TODO
            }
        });

        if (view.findViewById(R.id.urlRecyclerView) instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.urlRecyclerView);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            URLModel urlModel = new URLModel();
            String anotherList = getArguments().getString("urlList");
            Gson gson = new Gson();
            URLResponse list = gson.fromJson(anotherList, URLResponse.class);
            MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(list);
            adapter.delegate=this;
            recyclerView.setAdapter(adapter);
        }

        view.findViewById(R.id.backButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        return view;
    }

    @Override
    public void didSelectRow(int id) {
        URL request = new URL();
        request.urlId = id;
        URLModel model = new URLModel();
        model.getHistory(request, new URLModel.GetHistoryResponse() {
            @Override
            public void response(URLResponse urlHistory) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String urlHistoryJsonString = gson.toJson(urlHistory);
                bundle.putString("urlHistory", urlHistoryJsonString);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Navigation.findNavController(getView()).navigate(R.id.action_historyFragment_to_resultsFragment, bundle);
            }

            @Override
            public void error() {
                //TODO
            }
        });
    }
}