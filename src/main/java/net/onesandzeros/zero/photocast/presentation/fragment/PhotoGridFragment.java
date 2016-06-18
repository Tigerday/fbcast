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
import android.widget.GridView;
import android.widget.RelativeLayout;

import net.onesandzeros.zero.photocast.common.QueryParams;
import net.onesandzeros.zero.photocast.domain.dto.PhotoItem;
import net.onesandzeros.zero.photocast.domain.usecase.UseCaseFactory;
import net.onesandzeros.zero.photocast.presentation.adapter.PhotoGridAdapter;
import net.onesandzeros.zero.photocast.presentation.presenter.ItemPresenter;
import net.onesandzeros.zero.photocast.presentation.view.BaseView;
import net.onesandzeros.zero.photocast.presentation.view.CollectionView;
import net.onesandzeros.zero.photocast.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collection;

public class PhotoGridFragment extends PresenterFragment<ItemPresenter<PhotoItem>> implements
        CollectionView<PhotoItem>, PhotoGridAdapter.OnItemClickListener {

    private static final String            FRAGMENT_TAG       = PhotoGridFragment.class.getName();

    private static final String            LOG_TAG            = "PHOTO_GRID_FRAGMENT";

    private static final String            SCROLL_POSITION    = "scroll_position";

    private int                            mScrollPosition    = 3;

    private PhotoGridAdapter               mPhotoGridAdapter;

    private GridView                       mGridView;

    private RelativeLayout                 mProgressView;

    private BaseView.InteractionListener<PhotoItem> mListener;

    private String                         mGridQuery;

    private String                         mFragmentTagSuffix = "";

    /**
     * Should not be called from outside this fragment.
     */
    public PhotoGridFragment() {
    }

    public static Bundle getItemBundle(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        return bundle;
    }

    public static PhotoGridFragment newInstance(Bundle bundle) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        if (bundle != null) {
            fragment.mGridQuery = bundle.getString("query", "photoGrid");
            fragment.setFragmentTagSuffix(fragment.mGridQuery);
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
        View v = inflater.inflate(net.onesandzeros.zero.photocast.R.layout.fragment_media_grid, container, false);
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
        queryParams.setText(mGridQuery);
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
        if (mGridView != null && mGridView.getAdapter() != null) {
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
        mGridView = (GridView) view.findViewById(net.onesandzeros.zero.photocast.R.id.gv_grid_view);
        mProgressView = (RelativeLayout) view.findViewById(net.onesandzeros.zero.photocast.R.id.rl_progress);
    }

    private void bindViews() {
        mPhotoGridAdapter = new PhotoGridAdapter(mActivity, new ArrayList<PhotoItem>());
        mGridView.setAdapter(mPhotoGridAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void renderCollection(Collection<PhotoItem> photoItems) {
        if (photoItems != null && mPhotoGridAdapter != null) {
            mPhotoGridAdapter.setCollection(photoItems);
            mPhotoGridAdapter.notifyDataSetChanged();
            mPhotoGridAdapter.setOnItemClickListener(this);
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
        mPhotoGridAdapter.setCollection(new ArrayList<PhotoItem>());
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
