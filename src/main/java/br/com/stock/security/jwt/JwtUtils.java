package br.com.stock.security.jwt;


import br.com.stock.exceptions.TokenException;
import br.com.stock.security.type.AuthConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtUtils {


	public String generateJwtTokenWithUsername(String username) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND,  Integer.parseInt(AuthConfig.EXPIRE_TIME.getConfig()));
		Date expiresAt = cal.getTime();
		return JWT.create()
			.withSubject(username)
			.withIssuedAt(new Date())
			.withExpiresAt(expiresAt)
			.sign(Algorithm.HMAC512(AuthConfig.SECRET_KEY.getConfig().getBytes()));
	}

	public String validateJwtToken(String token) {
		try {
			return JWT.require(Algorithm.HMAC512(AuthConfig.SECRET_KEY.getConfig().getBytes()))
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new TokenException("Token inválido.");
		}
	}

}
