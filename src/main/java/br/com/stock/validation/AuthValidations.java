package br.com.stock.validation;

import br.com.stock.exceptions.RequestLoginException;
import br.com.stock.exceptions.TokenException;
import br.com.stock.model.RequestLogin;
import br.com.stock.security.type.AuthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthValidations {

    public static void validateRequestLogin(RequestLogin requestLogin) throws RequestLoginException {
        if (requestLogin.getUsername() == null || requestLogin.getUsername().isEmpty())
            throw new RequestLoginException("RequestLogin sem usuário preenchido.");
    }

    public static void validateToken(String token) throws RequestLoginException {
        if (token == null || token.isEmpty())
            throw new TokenException("Token inválido.");
    }

}
