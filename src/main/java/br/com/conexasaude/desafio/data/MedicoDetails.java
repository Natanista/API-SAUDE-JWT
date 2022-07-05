package br.com.conexasaude.desafio.data;

import br.com.conexasaude.desafio.model.MedicoModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class MedicoDetails implements UserDetails {

    private final Optional<MedicoModel> medicoModelOptinal;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return medicoModelOptinal.orElse(new MedicoModel()).getSenha();
    }

    @Override
    public String getUsername() {
        return  medicoModelOptinal.orElse(new MedicoModel()).getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
