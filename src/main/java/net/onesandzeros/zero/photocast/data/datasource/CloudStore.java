package net.onesandzeros.zero.photocast.data.datasource;

import net.onesandzeros.zero.photocast.domain.dto.PhotoItem;
import net.onesandzeros.zero.photocast.presentation.activity.MyApplication;
import net.onesandzeros.zero.photocast.utils.RequestUtils;

import java.util.List;

public class CloudStore implements ItemDataSource<PhotoItem> {

    @Override
    public List<PhotoItem> getPhotos() {
        List<PhotoItem> results;
        results = RequestUtils.getPhotos(MyApplication.getMyApplicationContext());
        return results;
    }

    @Override
    public List<PhotoItem> getAlbums() {
        List<PhotoItem> results;
        results = RequestUtils.getAlbums(MyApplication.getMyApplicationContext());
        return results;
    }

    @Override
    public List<PhotoItem> getVideos() {
        List<PhotoItem> results;
        results = RequestUtils.getVideos(MyApplication.getMyApplicationContext());
        return results;
    }


    @Override
    public List<PhotoItem> getPhotosGrid() {
        List<PhotoItem> results;
        results = RequestUtils.getPhotosGrid(MyApplication.getMyApplicationContext());
        return results;
    }
}
