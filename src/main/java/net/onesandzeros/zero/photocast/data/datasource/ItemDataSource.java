package net.onesandzeros.zero.photocast.data.datasource;

import java.util.List;


public interface ItemDataSource<T> {

    List<T> getPhotos();

    List<T> getAlbums();

    List<T> getVideos();

    List<T> getPhotosGrid();
}
