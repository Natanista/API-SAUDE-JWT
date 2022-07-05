package br.com.conexasaude.desafio.repository;

import br.com.conexasaude.desafio.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    boolean existsPacienteByCpf(String cpf);

    Paciente getPacienteByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
