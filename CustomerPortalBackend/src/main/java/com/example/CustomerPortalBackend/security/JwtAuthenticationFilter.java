/*
package com.example.CustomerPortalBackend.security;

import com.example.CustomerPortalBackend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        logger.info("Authorization header: {}", header);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try{
                if (!jwtService.isAccessToken(token)) {// || SecurityContextHolder.getContext().getAuthentication() != null -> Already authenticated or not?
                    filterChain.doFilter(request, response);
                    return;
                }
                Jws<Claims> parse = jwtService.parse(token);
                Claims payload = parse.getPayload();
                String userId = payload.getSubject();
                UUID userUuid = UUID.fromString(userId);
                userRepository.findById(userUuid).ifPresent(user ->{
                    if (user.isEnabled()) {
                        List<GrantedAuthority> authorities = user.getRole() == null ? List.of() :
                                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
                                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                if(SecurityContextHolder.getContext().getAuthentication() == null) SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            }
                        });
            }catch (ExpiredJwtException e){
                request.setAttribute("error","Token expired");
            }catch (Exception e){
                request.setAttribute("error","Invalid token");
            }
        }
        //Pass to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}*/
