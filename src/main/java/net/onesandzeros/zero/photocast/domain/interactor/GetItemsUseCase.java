package net.onesandzeros.zero.photocast.domain.interactor;


import net.onesandzeros.zero.photocast.common.QueryParams;
import net.onesandzeros.zero.photocast.domain.usecase.Callback;
import net.onesandzeros.zero.photocast.executor.PostExecutionThread;

public interface GetItemsUseCase<T> extends Interactor {

    void getItem(QueryParams queryParams, PostExecutionThread postExecutionThread, Callback<T> callback, boolean async,
                 boolean applyUserState);

}
