package net.onesandzeros.zero.photocast.executor;

public interface ThreadExecutor {
    java.util.concurrent.Future<?> execute(final Runnable runnable);
}
