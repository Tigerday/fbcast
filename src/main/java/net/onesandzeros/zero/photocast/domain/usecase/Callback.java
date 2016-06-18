package net.onesandzeros.zero.photocast.domain.usecase;

public interface Callback<T> {

    void onSuccess(T obj);

    void onError(Exception ex);
}
