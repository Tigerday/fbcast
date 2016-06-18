package net.onesandzeros.zero.photocast.domain.usecase;


import net.onesandzeros.zero.photocast.data.repository.RepositoryFactory;
import net.onesandzeros.zero.photocast.domain.interactor.GetItemsUseCase;
import net.onesandzeros.zero.photocast.executor.ExecutorFactory;

public class UseCaseFactory {

    public static GetItemsUseCase newGetPhotoItemUseCaseInstance() {
        return new GetPhotoItemsUseCase(RepositoryFactory.getPhotosRepositoryInstance(),
                ExecutorFactory.getThreadExecutorInstance());
    }
}
