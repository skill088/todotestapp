package com.projects.vkotov.todotestapp.presenter.vo;

import java.io.Serializable;

/**
 * Created by skill on 28.02.2018.
 */

public class Login implements Serializable {

    private String token;

    public Login() {}

    public Login(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
