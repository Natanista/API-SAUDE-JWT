package br.com.conexasaude.desafio.repository;

import br.com.conexasaude.desafio.model.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {
    Optional<MedicoModel> findByEmail(String email);

    Optional<MedicoModel> findMedicoModelByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
