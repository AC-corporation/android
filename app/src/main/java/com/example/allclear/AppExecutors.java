package com.example.allclear;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {
    private final ExecutorService mDiskIO;

    public AppExecutors(ExecutorService diskIO) {
        this.mDiskIO = diskIO;
    }

    public ExecutorService diskIO() {
        return mDiskIO;
    }

    private static final int NUMBER_OF_THREADS = 4;

    private static final ExecutorService diskIO = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final AppExecutors INSTANCE = new AppExecutors(diskIO);

    public static AppExecutors getInstance() {
        return INSTANCE;
    }
}

