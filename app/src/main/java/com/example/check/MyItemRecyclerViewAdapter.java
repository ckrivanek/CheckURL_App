package com.example.check;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.check.dummy.DummyContent.DummyItem;
import com.example.check.model.URL;
import com.example.check.model.URLModel;
import com.example.check.model.URLResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
        public interface AdapterDelegate{
            void didSelectRow(int index);
        }

        public com.example.check.MyItemRecyclerViewAdapter.AdapterDelegate delegate;

        private final ArrayList<URL> mValues = new ArrayList<URL>();

        public MyItemRecyclerViewAdapter(URLResponse urls) {
            //Reverse order to show newest search first in recycler view
            for (int i = urls.data.size()-1; i >= 0; i--){
                mValues.add(urls.data.get(i));
            }
        }

        @Override
        public com.example.check.MyItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_url, parent, false);
            return new com.example.check.MyItemRecyclerViewAdapter.ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final com.example.check.MyItemRecyclerViewAdapter.ViewHolder holder, int position) {
            // Set values for recycler view rows
            URL currentURL = mValues.get(position);
            String url = currentURL.url;
            holder.mURL = mValues.get(position);
            holder.mIdView.setText((String.valueOf(currentURL.urlId)));
            holder.mURLView.setText(url);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Values for recycler view
            public final View mView;
            public final TextView mIdView;
            public final TextView mURLView;
            private URL mURL;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.item_number);
                mURLView = (TextView) view.findViewById(R.id.content);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //int index = getLayoutPosition();
                        int id = Integer.parseInt((String) mIdView.getText());
                        delegate.didSelectRow(id);
                    }
                });
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mURLView.getText() + "'";
            }
        }
    }