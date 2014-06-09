package org.dronix.android.unisannio.fragments;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.adapters.ArticleAdapter;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.parsers.ScienzeParser;
import org.dronix.android.unisannio.retrievers.ScienzeRetriever;
import org.dronix.android.unisannio.settings.URLS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;

public class ScienzeAvvisiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    FragmentManager mFragmentManager;

    private List<Article> mNewsList;

    private ArticleAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ateneo_avvisi, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.ptr_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(R.color.unisannio_blue, R.color.unisannio_yellow, R.color.unisannio_blue, R.color.unisannio_yellow);
        mSwipeRefreshLayout.setEnabled(true);

        mNewsList = new ArrayList<Article>();
        mAdapter = new ArticleAdapter(inflater, (List<Article>) mNewsList);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Article article = mNewsList.get(position);

                mFragmentManager
                        .beginTransaction()
                        .addToBackStack("scienze_detail")
                        .replace(R.id.container, WebviewFragment.newInstance(URLS.SCIENZE + article.getLink()))
                        .commit();
            }
        });

        refreshList();

        return rootView;
    }

    @Override
    public void onRefresh() {
        refreshList();
    }

    private void refreshList() {
        mSwipeRefreshLayout.setRefreshing(true);

        ScienzeRetriever.getArticles(URLS.SCIENZE_NEWS)
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("UNISANNIO", e.getMessage());
                    }

                    @Override
                    public void onNext(List<Article> list) {
                        mNewsList = list;
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.setNewsList(list);
                    }
                });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).inject(this);
    }
}