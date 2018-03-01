package com.projects.vkotov.todotestapp.other.di;

import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.model.api.ApiInterface;
import com.projects.vkotov.todotestapp.model.api.ApiModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by skill on 01.03.2018.
 */
@Module
public class ModelModule {
    @Provides
    @Singleton
    ApiInterface provideApiInterface() {
        return ApiModule.getApiInterface(Constants.BASE_URL);
    }

    @Provides
    @Singleton
    @Named(Constants.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }


    @Provides
    @Singleton
    @Named(Constants.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }
}