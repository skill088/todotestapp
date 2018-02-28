package com.projects.vkotov.todotestapp;

import android.content.Context;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by skill on 28.02.2018.
 */

public abstract class Error extends Exception implements Serializable {

    public Error() {
        super();
    }

    public Error(String message) {
        super(message);
    }

    public static class AuthError extends Error {
        public AuthError(String message) { super(message); }
    } // 401

    public static class UnknownStatus extends Error {
        public UnknownStatus(String message) { super(message); }
    } // ?

    public static class NullResponse extends Error {
        public NullResponse() { super(); }
    } // api response is null

    public static String getMessage(@NotNull Context context, @NotNull Throwable e) {
        String message = e.getMessage();
        if (e instanceof Error.AuthError) {
            if (TextUtils.isEmpty(message)) {
                message = context.getString(R.string.error_auth);
            }
        }
        return  message;
    }
}
