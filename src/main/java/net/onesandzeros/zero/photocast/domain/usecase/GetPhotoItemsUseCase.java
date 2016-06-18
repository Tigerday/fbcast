package net.onesandzeros.zero.photocast.domain.usecase;

import net.onesandzeros.zero.photocast.common.QueryParams;
import net.onesandzeros.zero.photocast.data.repository.ContentRepository;
import net.onesandzeros.zero.photocast.domain.dto.PhotoItem;
import net.onesandzeros.zero.photocast.domain.interactor.GetItemsUseCase;
import net.onesandzeros.zero.photocast.executor.PostExecutionThread;
import net.onesandzeros.zero.photocast.executor.ThreadExecutor;
import net.onesandzeros.zero.photocast.utils.LogUtils;

import java.util.List;

public class GetPhotoItemsUseCase extends BaseUseCase implements GetItemsUseCase<List<PhotoItem>> {

    private static final String     LOG_TAG = "GET_PHOTO_ITEMS_USE_CASE";

    private final ContentRepository mContentRepository;

    private String                  mQuery;

    public GetPhotoItemsUseCase(ContentRepository contentRepository, ThreadExecutor executor) {
        mContentRepository = contentRepository;
        mThreadExecutor = executor;
    }

    @Override
    public void getItem(QueryParams queryParams, PostExecutionThread postExecutionThread, Callback callback,
                        boolean async, boolean applyUserState) {
        mQuery = queryParams.getTEXT();
        mCallback = callback;
        mPostExecutionThread = postExecutionThread;
        mAsync = async;
        mApplyUserState = applyUserState;
        getData();
    }

    @Override
    public void run() {
        try {
            if (mQuery.equals("photo")) {
                @SuppressWarnings("unchecked")
                List<PhotoItem> result = mContentRepository.getPhotos();
                notifyOnSuccess(result);
                LogUtils.errorLog(LOG_TAG, "PICTURE SUCCESS : ");
            } else if (mQuery.equals("album")) {
                @SuppressWarnings("unchecked")
                List<PhotoItem> result = mContentRepository.getAlbums();
                notifyOnSuccess(result);
                LogUtils.errorLog(LOG_TAG, "ALBUM SUCCESS : ");
            } else if (mQuery.equals("video")) {
                @SuppressWarnings("unchecked")
                List<PhotoItem> result = mContentRepository.getVideos();
                notifyOnSuccess(result);
                LogUtils.errorLog(LOG_TAG, "VIDEO SUCCESS : ");
            }else if (mQuery.equals("photoGrid")) {
                @SuppressWarnings("unchecked")
                List<PhotoItem> result = mContentRepository.getPhotosGrid();
                notifyOnSuccess(result);
                LogUtils.errorLog(LOG_TAG, "GRID SUCCESS : ");
            }


        } catch (Exception e) {
            LogUtils.errorLog(LOG_TAG, "Exception on background thread... ", e);
            notifyOnError(e);
        }
    }

    private void getData() {
        if (mAsync) {
            if (!isTaskRunning()) {
                mFuture = mThreadExecutor.execute(this);
            }
        } else {
            run();
        }
    }
}
