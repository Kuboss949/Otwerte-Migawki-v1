package pl.otwartemigawki.OtwarteMigawkiApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


public interface JwtService {
    String extractUserName(String token);
    Date extractExpiration(String token);
    boolean isValid(String token, UserDetails user);
    boolean isTokenExpired(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
    String generateToken(User user);

}
