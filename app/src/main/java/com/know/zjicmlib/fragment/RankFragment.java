package com.know.zjicmlib.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.activity.SearchActivity;
import com.know.zjicmlib.adapter.RankListAdapter;
import com.know.zjicmlib.modle.RankModel;
import com.know.zjicmlib.modle.bean.Ranker;
import com.know.zjicmlib.retrofit.ServiceFactory;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yang on 2016/6/7.
 */
public class RankFragment extends Fragment {

    @Bind(R.id.list_rank_boo)RecyclerView rankList;
    RankListAdapter rankListAdapter;
    List<Ranker> rankers;

    RankModel rankModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rank,container,false);
        ButterKnife.bind(this,view);

        rankModel = new RankModel();

        initRankList();

        getRankList();

        return view;
    }

    private void initRankList(){

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        rankList.setLayoutManager(layoutManager);

        rankers = new ArrayList<>();
        QueryBuilder query = new QueryBuilder(Ranker.class);
        rankers.clear();
        rankers.addAll(APP.mDb.query(query));

        rankListAdapter = new RankListAdapter(rankers);
        rankListAdapter.setOnItemClickListener(position -> {

            Ranker ranker = rankers.get(position);
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("word",ranker.getWord());
            startActivity(intent);

        });



        rankList.setAdapter(rankListAdapter);




    }


    public void getRankList(){

        ServiceFactory.getService().getRank()
                .map(rankModel::getRankList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rankerss -> {

                    rankers.clear();
                    rankers.addAll(rankerss);

                    rankListAdapter.notifyDataSetChanged();
                    saveRank(rankerss);
                    //APP.mDb.delete(Ranker.class);

                }, Throwable::printStackTrace);

    }

    private void saveRank(List<Ranker> rankers){
        APP.mDb.insert(rankers, ConflictAlgorithm.Ignore);
    }

}
