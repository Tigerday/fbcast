package net.onesandzeros.zero.photocast.utils;

import android.content.Context;

import net.onesandzeros.zero.photocast.common.ApiConstants;
import net.onesandzeros.zero.photocast.common.ApiUtils;
import net.onesandzeros.zero.photocast.domain.dto.PhotoItem;
import net.onesandzeros.zero.photocast.entity.PhotosResponse;

import java.util.List;

public class RequestUtils extends ApiUtils {

    private static final String LOG_TAG = "REQUEST_UTILS";

    public static List<PhotoItem> getPhotos(Context context) {
        final String url = ApiConstants.getPhotoUrl();
        final ResultWrapper resultWrapper = new ResultWrapper();
        makeSyncGetJsonRequest(new PhotosResponse(), context, url, 10000, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Photo Results received " + url);
                resultWrapper.setResult(response.getResult());
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (List<PhotoItem>) resultWrapper.getResult();
    }

    public static List<PhotoItem> getAlbums(Context context) {
        final String url = ApiConstants.getAlbumUrl();
        final ResultWrapper resultWrapper = new ResultWrapper();
        makeSyncGetJsonRequest(new PhotosResponse(), context, url, 10000, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Album Results received " + url );
                resultWrapper.setResult(response.getResult());
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (List<PhotoItem>) resultWrapper.getResult();
    }

    public static List<PhotoItem> getVideos(Context context) {
        final String url = ApiConstants.getVideoUrl();
        final ResultWrapper resultWrapper = new ResultWrapper();
        makeSyncGetJsonRequest(new PhotosResponse(), context, url, 10000, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Video Results received " + url);
                resultWrapper.setResult(response.getResult());
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (List<PhotoItem>) resultWrapper.getResult();
    }
    public static List<PhotoItem> getPhotosGrid(Context context) {
        final String url = ApiConstants.getPhotoUrl();
        final ResultWrapper resultWrapper = new ResultWrapper();
        makeSyncGetJsonRequest(new PhotosResponse(), context, url, 10000, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Photo Grid Results received " + url);
                resultWrapper.setResult(response.getResult());
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (List<PhotoItem>) resultWrapper.getResult();
    }

}
