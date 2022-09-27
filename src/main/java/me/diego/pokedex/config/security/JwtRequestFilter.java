package me.diego.pokedex.config.security;

import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.service.UserDetailsServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtRequestFilter(JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authorizationField = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationField == null || !authorizationField.startsWith(SecurityConstants.PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = authorizationField.replace(SecurityConstants.PREFIX_TOKEN, "");
        final String username = jwtTokenService.validateTokenAndGetUsername(token);
        if (username == null) {
            chain.doFilter(request, response);
            return;
        }

        UserModel user = userDetailsService.loadUserModelByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
