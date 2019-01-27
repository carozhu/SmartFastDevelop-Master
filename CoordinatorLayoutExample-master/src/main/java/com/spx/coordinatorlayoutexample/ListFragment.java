package com.spx.coordinatorlayoutexample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spx.coordinatorlayoutexample.adapter.SampleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private Context context;
    private List<String> data = new ArrayList<>();
    private String title;

    public static ListFragment newInstance(String title) {
        ListFragment listFragment = new ListFragment();
        listFragment.title = title;
        return listFragment;
    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(context);
        context = c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);

        for (int i = 0; i < 50; i++) {
            data.add(title + "第" + i + "个数据");
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new SampleAdapter(data));

        return view;
    }

//    private class SimpleViewHolder extends RecyclerView.ViewHolder {
//        public TextView titleTv;
//
//        public SimpleViewHolder(View itemView) {
//            super(itemView);
//            titleTv = itemView.findViewById(R.id.title_tv);
//        }
//    }
//
//    private class SampleAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
//
//        @NonNull
//        @Override
//        public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.sample_item_layout, parent, false);
//            return new SimpleViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
//            final String item = data.get(position);
//            holder.titleTv.setText(item);
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//    }
}
