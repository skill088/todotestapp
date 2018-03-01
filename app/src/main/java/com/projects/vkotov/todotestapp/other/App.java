package com.projects.vkotov.todotestapp.other;

import android.app.Application;

import com.projects.vkotov.todotestapp.other.di.AppComponent;
import com.projects.vkotov.todotestapp.other.di.DaggerAppComponent;

/**
 * Created by skill on 01.03.2018.
 */

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .build();
    }
}
