package org.dronix.android.unisannio.fragments;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.adapters.ArticleAdapter;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.retrievers.IngegneriaRetriever;
import org.dronix.android.unisannio.settings.URLS;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;
import rx.Observer;

public class IngegneriaAvvisiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ISimpleDialogListener {

    @Inject
    FragmentManager mFragmentManager;

    @Inject
    MainActivity mActivity;

    @InjectView(R.id.listView)
    GridView mListView;

    @InjectView(R.id.ptr_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    DetailedAndroidLogger mLogger;

    @Inject
    IngegneriaRetriever mIngegneriaRetriever;

    private List<Article> mNewsList;

    private ArticleAdapter mAdapter;

    private IngegneriaAvvisiFragment mContext;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_avvisi, container, false);
        ButterKnife.inject(this, rootView);
        ((MainActivity) getActivity()).inject(this);

        mContext = this;

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(R.color.unisannio_blue, R.color.unisannio_yellow, R.color.unisannio_blue, R.color.unisannio_yellow);
        mSwipeRefreshLayout.setEnabled(true);

        mNewsList = new ArrayList<Article>();
        mAdapter = new ArticleAdapter(inflater, mNewsList, new ListItemButtonClickListener());
        mListView.setAdapter(mAdapter);

        refreshList();

        return rootView;
    }

    @Override
    public void onRefresh() {
        refreshList();
    }

    private void refreshList() {
        mSwipeRefreshLayout.setRefreshing(true);

        mIngegneriaRetriever.getArticles(URLS.INGEGNERIA_NEWS_RSS, URLS.INGEGNERIA_NEWS_PHP)
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLogger.error(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Article> list) {
                        mAdapter.setNewsList(list);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onPositiveButtonClicked(int i) {
        if(!isAdded()) {
            return;
        }
        share(i);
    }

    private void share(int articleIndex) {
        Article article = mNewsList.get(articleIndex);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = article.getTitle() + " - " + article.getLink();
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onNegativeButtonClicked(int i) {

    }

    private final class ListItemButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = mListView.getFirstVisiblePosition(); i <= mListView.getLastVisiblePosition(); i++) {
                Article article = mNewsList.get(i);

                if (v == mListView.getChildAt(i - mListView.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_1)) {
                    SimpleDialogFragment
                            .createBuilder(mActivity, mFragmentManager)
                            .setTitle(article.getTitle())
                            .setMessage(Html.fromHtml(article.getDescription()))
                            .setPositiveButtonText(mActivity.getString(R.string.share))
                            .setNegativeButtonText(mActivity.getString(R.string.close))
                            .setTargetFragment(mContext, i)
                            .show();
                } else if (v == mListView.getChildAt(i - mListView.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_2)) {
                    share(i);
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