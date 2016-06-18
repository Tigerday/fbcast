package net.onesandzeros.zero.photocast.common;

import com.facebook.AccessToken;

import net.onesandzeros.zero.photocast.utils.LogUtils;

public class ApiConstants {

    private static final String LOG_TAG = "API CONSTANTS";

    public interface Photo {
        String ID            = "id";
        String NAME          = "name";
        String SOURCE        = "source";
        String FROM          = "from";
        String HEIGHT        = "height";
        String WIDTH         = "width";
        String IMAGES        = "images";
        String COVER_PHOTO   = "cover_photo";
        String ALBUM_NAME    = "album";
        String VIDEO_PREVIEW = "picture";
    }

    public static String getPhotoUrl() {
        return getPhotoUrl(200);
    }

    private static String getPhotoUrl(int pageSize) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.6/me/photos?fields=id,from,images&limit=" + pageSize + "&access_token="
                + AccessToken.getCurrentAccessToken().getToken();
    }

    public static String getAlbumUrl() {
        return getAlbumUrl(200);
    }

    private static String getAlbumUrl(int pageSize) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.6/me/albums?fields=id,name,j,album,cover_photo&limit=" + pageSize
        //return "https://graph.facebook.com/v2.6/me?fields=id%2Cname%2Cphotos%7Balbum%7D&=" + pageSize
                + "&access_token=" + AccessToken.getCurrentAccessToken().getToken();
    }

    public static String getCoverPhotoUrl(String id) {
        AccessToken.getCurrentAccessToken().getToken();
        LogUtils.debugLog(LOG_TAG, "COVERPHOTOS--> " + id);
        return "https://graph.facebook.com/v2.6/" + id + "/picture?access_token="
                + AccessToken.getCurrentAccessToken().getToken();

    }


    public static String getVideoUrl() {
        return getVideoUrl(200);
    }

    private static String getVideoUrl(int pageSize) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.6/me/videos/uploaded?fields=id,from,picture&limit=" + pageSize + "&access_token="
                + AccessToken.getCurrentAccessToken().getToken();
    }

}
