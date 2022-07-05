package br.com.conexasaude.desafio.repository;

import br.com.conexasaude.desafio.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
