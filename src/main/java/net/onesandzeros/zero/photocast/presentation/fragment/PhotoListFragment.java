package net.onesandzeros.zero.photocast.presentation.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import net.onesandzeros.zero.photocast.common.QueryParams;
import net.onesandzeros.zero.photocast.domain.dto.PhotoItem;
import net.onesandzeros.zero.photocast.domain.usecase.UseCaseFactory;
import net.onesandzeros.zero.photocast.presentation.adapter.PhotoListAdapter;
import net.onesandzeros.zero.photocast.presentation.presenter.ItemPresenter;
import net.onesandzeros.zero.photocast.presentation.view.BaseView;
import net.onesandzeros.zero.photocast.presentation.view.CollectionView;
import net.onesandzeros.zero.photocast.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collection;

public class PhotoListFragment extends PresenterFragment<ItemPresenter<PhotoItem>> implements
        CollectionView<PhotoItem>, PhotoListAdapter.OnItemClickListener {

    private static final String            FRAGMENT_TAG       = PhotoListFragment.class.getName();

    private static final String            LOG_TAG            = "PHOTO_LIST_FRAGMENT";

    private static final String            SCROLL_POSITION    = "scroll_position";

    private int                            mScrollPosition    = 0;

    private PhotoListAdapter               mPhotoListAdapter;

    private ListView                       mListView;

    private RelativeLayout                 mProgressView;

    private BaseView.InteractionListener<PhotoItem> mListener;

    private String                         mQuery;

    private String                         mFragmentTagSuffix = "";

    /**
     * Should not be called from outside this fragment.
     */
    public PhotoListFragment() {
    }

    public static Bundle getItemBundle(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        return bundle;
    }

    public static PhotoListFragment newInstance(Bundle bundle) {
        PhotoListFragment fragment = new PhotoListFragment();
        if (bundle != null) {
            fragment.mQuery = bundle.getString("query", "photo");
            fragment.setFragmentTagSuffix(fragment.mQuery);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public void setFragmentTagSuffix(String fragmentTagSuffix) {
        mFragmentTagSuffix = fragmentTagSuffix;
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG + mFragmentTagSuffix;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (BaseView.InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewBundle(getArguments());
    }

    @Override
    public void onNewBundle(Bundle bundle) {
        super.onNewBundle(bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(net.onesandzeros.zero.photocast.R.menu.menu_home, menu);
    }

    @Override
    protected ItemPresenter<PhotoItem> createPresenter() {
        return new ItemPresenter<PhotoItem>(UseCaseFactory.newGetPhotoItemUseCaseInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v = inflater.inflate(net.onesandzeros.zero.photocast.R.layout.fragment_media_list, container, false);
        findViews(v);
        bindViews();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMediaList();
        if (savedInstanceState != null) {
            int posArray = savedInstanceState.getInt(SCROLL_POSITION);
            if (posArray > 0) {
                mScrollPosition = posArray;
            }
        }
    }

    private void loadMediaList() {
        QueryParams queryParams = QueryParams.getNewInstance();
        queryParams.setText(mQuery);
        presenter.init(queryParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
        mActivity.supportInvalidateOptionsMenu();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mListView != null && mListView.getAdapter() != null) {
            outState.putInt(SCROLL_POSITION, mScrollPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.debugLog(LOG_TAG, item.getItemId() + " ");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    private void findViews(View view) {
        mListView = (ListView) view.findViewById(net.onesandzeros.zero.photocast.R.id.lv_list_view);
        mProgressView = (RelativeLayout) view.findViewById(net.onesandzeros.zero.photocast.R.id.rl_progress);
    }

    private void bindViews() {
        mPhotoListAdapter = new PhotoListAdapter(mActivity, new ArrayList<PhotoItem>());
        mListView.setAdapter(mPhotoListAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void renderCollection(Collection<PhotoItem> photoItems) {
        if (photoItems != null && mPhotoListAdapter != null) {
            mPhotoListAdapter.setCollection(photoItems);
            mPhotoListAdapter.notifyDataSetChanged();
            mPhotoListAdapter.setOnItemClickListener(this);
        }
    }

    @Override
    public void viewItem(PhotoItem photoItem) {
        mListener.onItemClick(photoItem);
    }

    @Override
    public void showLoading() {
        this.mProgressView.setVisibility(View.VISIBLE);
        if (mActivity != null) {
            mActivity.setProgressBarIndeterminateVisibility(true);
        }
    }

    @Override
    public void hideLoading() {
        this.mProgressView.setVisibility(View.GONE);
        if (mActivity != null) {
            mActivity.setProgressBarIndeterminateVisibility(false);
        }
    }

    @Override
    public void showRetry() {
        mPhotoListAdapter.setCollection(new ArrayList<PhotoItem>());
    }

    @Override
    public void hideRetry() {
    }

    @Override
    public void showError(String message) {
        LogUtils.errorLog(LOG_TAG, message);
    }

    @Override
    public Context getContext() {
        return mApplication;
    }

    @Override
    public void onItemClicked(View v, PhotoItem photoItem) {
        if (presenter != null && photoItem != null) {
            presenter.onItemClicked(photoItem);
        }
    }
}
