package com.cine.cine_remake.services.authentication;

import com.cine.cine_remake.model.AuthRequest;

public interface AuthenticationService {

    String signInAndReturnJwt(AuthRequest signInRequest);

}
