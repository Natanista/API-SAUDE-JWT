package br.com.conexasaude.desafio.security;

import br.com.conexasaude.desafio.data.MedicoDetails;
import br.com.conexasaude.desafio.model.MedicoModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public static final String TOKEN_PASSWORD = "8d50bd78-fb16-11ec-b939-0242ac120002";
    public static final int TOKEN_EXPIRACAO = 600_000;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            MedicoModel medicoModel = new ObjectMapper().readValue(request.getInputStream(), MedicoModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    medicoModel.getEmail(),
                    medicoModel.getSenha(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao autenticar usuario", e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MedicoDetails medicoDetails = (MedicoDetails) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(medicoDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));


        response.getWriter().write(token);
        response.getWriter().flush();
    }

}
