package com.mhome.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Fragment extends android.support.v4.app.Fragment {

    View view;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Item> itemList;
    private String[] tabs = {"TEST-ONE","TEST-TWO","TEST-THREE"};
    private int[] images = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4};
    Drawable[] drawables;
    private static final String TAG = "Tag";
    private int type;
    public Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ArrayList<Item> itemArrayList = simulationData();
                        itemList.clear();
                        itemList.addAll(itemArrayList);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },412);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getInt(TAG);
        itemList = simulationData();
        Log.i("ginger","itemList.size() == "+ itemList.size());
        adapter = new MyRecyclerViewAdapter(itemList);
        drawables = new Drawable[]{getResources().getDrawable(R.drawable.image1), getResources().getDrawable(R.drawable.image2),
                getResources().getDrawable(R.drawable.image3), getResources().getDrawable(R.drawable.image4)};
    }

    public static Fragment newInstance(int type){
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putInt(TAG, type);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<Item> simulationData(){
        ArrayList<Item> itemArrayList = new ArrayList<>();
        int t = (int) Math.ceil(Math.random()*16);
        String title = tabs[type];
        String subtitle;
        for(int i = 0; i < t; i++){
            subtitle = "测试数据" + i;
            itemArrayList.add(new Item(title,subtitle,i%4));
        }
        Log.i("ginger",itemArrayList.size()+"");
        return itemArrayList;
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

        private List<Item> items;

        MyRecyclerViewAdapter(List<Item> items){
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View itemView = inflater.inflate(R.layout.list_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            Log.i("ginger","items.size() == "+items.size());
            return items.size();

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Item item = items.get(position);

            TextView title = holder.title;
            TextView subtitle = holder.subTitle;
            ImageView image = holder.iv;

            title.setText(item.title);
            subtitle.setText(item.subtitle);
            Glide.with(getContext()).load(images[item.image]).into(image);
            holder.cv.setOnClickListener(new CardView.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(),ScrollingActivity.class);
                    intent.putExtra(TAG,images[item.image]);
                    intent.putExtra("subtitle",item.subtitle);
                    view.getContext().startActivity(intent);
                }
            });


        }

        class ViewHolder extends RecyclerView.ViewHolder{
            CardView cv;
            TextView title,subTitle;
            ImageView iv;
            public ViewHolder(View view){
                super(view);
                cv = (CardView) view.findViewById(R.id.cv);
                title = (TextView) view.findViewById(R.id.tv_title);
                subTitle = (TextView) view.findViewById(R.id.tv_subtitle);
                iv = (ImageView) view.findViewById(R.id.iv);
            }
        }
    }

    public class Item{
        int image;
        String title,subtitle;
        Item(String t, String s,int i){
            image = i;
            title = t;
            subtitle = s;
        }
    }


}
