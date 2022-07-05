package br.com.conexasaude.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "paciente_id")
    private Long id;
    @NotEmpty(message = "Nome nao pode estar vazio e nem ser nulo")
    private String nome;
    @CPF
    @NotEmpty(message = "CPF nao pode estar vazio e nem ser nulo")
    private String cpf;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private List<Agendamento> agendamentos;

}
