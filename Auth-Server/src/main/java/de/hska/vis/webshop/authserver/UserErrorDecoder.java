package de.hska.vis.webshop.authserver;

import de.hska.vis.webshop.authserver.exception.UserNotExist;
import feign.Response;
import feign.codec.ErrorDecoder;

class UserErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new UserNotExist();
    }
}