package com.example.check;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.check.dummy.DummyContent;
import com.example.check.model.URLModel;
import com.example.check.model.URLResponse;

/**
 * A fragment representing a list of Items.
 */
public class URLListFragment extends Fragment implements MyItemRecyclerViewAdapter.AdapterDelegate{
    public static URLResponse anotherList;
    private URLResponse urlList;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public URLListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static URLListFragment newInstance(int columnCount) {
        URLListFragment fragment = new URLListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_url_list, container, false);
        return view;
    }

    @Override
    public void didSelectRow(int index) {

    }
}