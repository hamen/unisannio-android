package org.dronix.android.unisannio.fragments;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.adapters.NewsAdapter;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.models.News;
import org.dronix.android.unisannio.retrievers.AteneoRetriever;
import org.dronix.android.unisannio.settings.URLS;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;

public class AteneoAvvisiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    FragmentManager mFragmentManager;

    @InjectView(R.id.listView)
    ListView mListView;

    @InjectView(R.id.ptr_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<News> mNewsList;

    private NewsAdapter mAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_avvisi, container, false);
        ButterKnife.inject(this, rootView);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(R.color.unisannio_blue, R.color.unisannio_yellow, R.color.unisannio_blue, R.color.unisannio_yellow);
        mSwipeRefreshLayout.setEnabled(true);

        mNewsList = new ArrayList<News>();
        mAdapter = new NewsAdapter(inflater, mNewsList, new ListItemButtonClickListener());
        mListView.setAdapter(mAdapter);

        refreshList();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).inject(this);
    }

    @Override
    public void onRefresh() {
        refreshList();
    }

    private void refreshList() {
        mSwipeRefreshLayout.setRefreshing(true);

        AteneoRetriever.getNewsList(URLS.ATENEO_NEWS)
                .subscribe(new Observer<List<News>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("UNISANNIO", e.getMessage());
                    }

                    @Override
                    public void onNext(List<News> list) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.setNewsList(list);
                    }
                });
    }

    private final class ListItemButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = mListView.getFirstVisiblePosition(); i <= mListView.getLastVisiblePosition(); i++) {
                News article = mNewsList.get(i);

                if (v == mListView.getChildAt(i - mListView.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_1)) {
                    mFragmentManager
                            .beginTransaction()
                            .addToBackStack("scienze_detail")
                            .replace(R.id.container, WebviewFragment.newInstance(URLS.ATENEO_DETAIL_BASE_URL + mNewsList.get(i).getId()))
                            .commit();
                } else if (v == mListView.getChildAt(i - mListView.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_2)) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = article.getBody() + " - " + URLS.ATENEO_DETAIL_BASE_URL + mNewsList.get(i).getId();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            }
        }
    }

    private final class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "Clicked on List Item " + position, Toast.LENGTH_SHORT).show();
        }
    }
}