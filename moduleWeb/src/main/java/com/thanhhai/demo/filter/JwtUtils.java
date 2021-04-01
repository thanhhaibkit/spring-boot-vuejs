package com.thanhhai.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultHeader;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import phoneappli.booking.auth.JwtResponse;
import phoneappli.booking.auth.UserPrinciple;
import phoneappli.booking.constant.SecurityConstants;
import phoneappli.booking.enums.ERole;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@PropertySource(value = {"classpath:application.properties"})
public class JwtUtils implements Serializable {

    @Value("${jwt.secret.key}")
    private String SECRET;

    public String doGenerateToken(Authentication auth) throws AuthenticationException {
        try {
            UserPrinciple userPrinciple = (UserPrinciple) auth.getPrincipal();
            Map<String, Object> claims = new HashMap<>();
            List<ERole> eRoles = userPrinciple.getRoles();

            claims.put("role", eRoles);
            claims.put("userId", userPrinciple.getId());
            claims.put("firstName", userPrinciple.getFirstName());
            claims.put("lastName", userPrinciple.getLastName());
            claims.put("accountCode", userPrinciple.getAccountCode());
            claims.put("accountName", userPrinciple.getAccountName());
            claims.put("isAutoBooking", userPrinciple.getIsAutoBooking());
            claims.put("ticketNumber", userPrinciple.getTicketNumber());
            claims.put("birthday", userPrinciple.getBirthday() != null ? userPrinciple.getBirthday().toString() : "");
            String token = Jwts.builder().setClaims(claims)
                    .setSubject(userPrinciple.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();

            JwtResponse jwtResponse = new JwtResponse(token, null);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(jwtResponse);

            return jsonString;
        } catch (Exception ex) {
            throw new AuthenticationException(ex.getMessage());
        }
    }

    //for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw ex;
        }

    }

    public String getUsernameFromToken(String token) {
        if (isTokenExpired(token))
            throw new ExpiredJwtException(new DefaultHeader(), null, "Token expired!");
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return Long.parseLong(String.valueOf(claims.get("userId")));
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
