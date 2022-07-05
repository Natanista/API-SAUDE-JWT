package br.com.conexasaude.desafio.service;

import br.com.conexasaude.desafio.data.MedicoDetails;
import br.com.conexasaude.desafio.model.MedicoModel;
import br.com.conexasaude.desafio.repository.MedicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class MedicoDetailsServiceImplementation implements UserDetailsService {

    private final MedicoRepository medicoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MedicoModel> medicoModelOptinal =  medicoRepository.findByEmail(email);
        if(medicoModelOptinal.isEmpty()){
            throw new UsernameNotFoundException("Email nao encontrado: " + email);
        }
        return new MedicoDetails(medicoModelOptinal);
    }


}
