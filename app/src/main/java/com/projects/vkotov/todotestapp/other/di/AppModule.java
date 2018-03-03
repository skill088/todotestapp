package com.projects.vkotov.todotestapp.other.di;

import android.content.Context;

import com.projects.vkotov.todotestapp.other.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skill on 02.03.2018.
 */
@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return app;
    }
}
