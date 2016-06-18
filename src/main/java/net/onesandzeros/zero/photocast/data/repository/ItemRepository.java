package net.onesandzeros.zero.photocast.data.repository;

import net.onesandzeros.zero.photocast.data.datasource.CloudStore;
import net.onesandzeros.zero.photocast.data.datasource.ItemDataSource;
import net.onesandzeros.zero.photocast.domain.dto.PhotoItem;

import java.util.List;

public class ItemRepository implements ContentRepository<PhotoItem> {

    private static final String LOG_TAG = "ITEM_REPOSITORY";

    ItemDataSource              mCloud  = new CloudStore();

    @Override
    public List<PhotoItem> getPhotos() {
        return mCloud.getPhotos();
    }

    @Override
    public List<PhotoItem> getAlbums() {
        return mCloud.getAlbums();
    }

    @Override
    public List<PhotoItem> getVideos() { return mCloud.getVideos(); }

    @Override
    public List<PhotoItem> getPhotosGrid() { return mCloud.getPhotosGrid(); }
}
