package com.whydigit.wms.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.TokenVO;
import com.whydigit.wms.entity.UserVO;
import com.whydigit.wms.repo.TokenRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	@Value("${app.auth.tokenKey:baseprojectkey}")
	private String tokenKey;

	@Value("${app.auth.tokenSecret:baseprojectcret}")
	private String tokenSecret;

	@Value("${app.auth.tokenExpirationMsec:1200000}")
	private long tokenExpInMSec;

	@Value("${app.auth.refreshtokenExpirationMsec:604800000}")
	private long refreshTokenExpInMSec;

	private byte[] hmacSHA512Byte;

	private static final String HMAC_SHA512 = "HmacSHA512";

	@Autowired
	TokenRepo tokenRepo;

	public TokenVO createToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenExpInMSec);
		Key hmacSHA512Key = new SecretKeySpec(hmacSHA512Byte, HMAC_SHA512);
		Claims claims = Jwts.claims();
		claims.put("id", userPrincipal.getUserId());
		TokenVO tokenVO = tokenRepo
				.save(TokenVO.builder().id(UUID.randomUUID().toString()).userId(userPrincipal.getUserId())
						.createdDate(new Date()).expDate(new Date(now.getTime() + refreshTokenExpInMSec)).build());
		String token = Jwts.builder().setClaims(claims).setSubject(userPrincipal.getUserName()).setIssuedAt(now)
				.setExpiration(expiryDate).signWith(hmacSHA512Key, SignatureAlgorithm.HS512).compact();
		tokenVO.setToken(token);
		return tokenVO;

	}

	public String getUserIdFromToken(String token) {
		Key hmacSHA512Key = new SecretKeySpec(hmacSHA512Byte, HMAC_SHA512);
		Claims claims = Jwts.parserBuilder().setSigningKey(hmacSHA512Key).build().parseClaimsJws(token).getBody();
		return claims.get("id").toString();
	}

	public boolean validateToken(String authToken) {
		try {
			Key hmacSHA512Key = new SecretKeySpec(hmacSHA512Byte, HMAC_SHA512);
			Jwts.parserBuilder().setSigningKey(hmacSHA512Key).build().parseClaimsJws(authToken);
			return true;
		} catch (SecurityException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}

	public TokenVO createToken(long userId) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenExpInMSec);
		Key hmacSHA512Key = new SecretKeySpec(hmacSHA512Byte, HMAC_SHA512);
		TokenVO tokenVO = tokenRepo.save(TokenVO.builder().id(UUID.randomUUID().toString()).userId(userId)
				.createdDate(new Date()).expDate(new Date(now.getTime() + refreshTokenExpInMSec)).build());
		String token = Jwts.builder().setSubject(String.valueOf(userId)).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(hmacSHA512Key, SignatureAlgorithm.HS512).compact();
		tokenVO.setToken(token);
		return tokenVO;
	}

	public TokenVO createToken(long userId, String userName) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenExpInMSec);
		Key hmacSHA512Key = new SecretKeySpec(hmacSHA512Byte, HMAC_SHA512);
		Claims claims = Jwts.claims();
		claims.put("id", userId);
		TokenVO tokenVO = tokenRepo.save(TokenVO.builder().id(UUID.randomUUID().toString()).userId(userId)
				.createdDate(new Date()).expDate(new Date(now.getTime() + refreshTokenExpInMSec)).build());
		String token = Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(hmacSHA512Key, SignatureAlgorithm.HS512).compact();
		tokenVO.setToken(token);
		return tokenVO;
	}

	@PostConstruct
	public void getHMacSHA512Key() {
		try {
			Mac sha512Hmac = Mac.getInstance(HMAC_SHA512);
			SecretKeySpec keySpec = new SecretKeySpec(tokenKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA512);
			sha512Hmac.init(keySpec);
			hmacSHA512Byte = sha512Hmac.doFinal(tokenSecret.getBytes(StandardCharsets.UTF_8));
			logger.debug("getHMacSHA512Key byte : {}", hmacSHA512Byte);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			logger.error("Error while getHMacSHA512Key :: {}", e.getMessage());
		}
	}

	public TokenVO createRefreshToken(TokenVO tokenVO, UserVO userVO) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenExpInMSec);
		Key hmacSHA512Key = new SecretKeySpec(hmacSHA512Byte, HMAC_SHA512);
		Claims claims = Jwts.claims();
		claims.put("id", userVO.getUserId());
		tokenVO.setExpDate(new Date(now.getTime() + refreshTokenExpInMSec));
		tokenRepo.save(tokenVO);
		String token = Jwts.builder().setClaims(claims).setSubject(userVO.getUserName()).setIssuedAt(now)
				.setExpiration(expiryDate).signWith(hmacSHA512Key, SignatureAlgorithm.HS512).compact();
		tokenVO.setToken(token);
		return tokenVO;
	}
}
