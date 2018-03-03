package com.projects.vkotov.todotestapp.presenter.mappers;

import com.projects.vkotov.todotestapp.model.dto.LoginDTO;
import com.projects.vkotov.todotestapp.presenter.vo.Login;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import rx.functions.Func1;

/**
 * Created by skill on 28.02.2018.
 */

public class LoginMapper implements Function<LoginDTO, Login> {

    @Inject
    public LoginMapper() {}

    @Override
    public Login apply(LoginDTO loginDTO) throws Exception {
        if (loginDTO == null)
            return null;

        return new Login(loginDTO.getToken());
    }
}
