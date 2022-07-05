package br.com.conexasaude.desafio.service;

import br.com.conexasaude.desafio.dto.MedicoLoginDTO;
import br.com.conexasaude.desafio.model.Agendamento;
import br.com.conexasaude.desafio.model.MedicoModel;
import br.com.conexasaude.desafio.model.Paciente;
import br.com.conexasaude.desafio.repository.AgendamentoRepository;
import br.com.conexasaude.desafio.repository.MedicoRepository;
import br.com.conexasaude.desafio.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicoService {
    private MedicoRepository medicoRepository;

    private AgendamentoRepository agendamentoRepository;
    private PacienteRepository pacienteRepository;
    private PasswordEncoder passwordEncoder;


    public ResponseEntity signup(MedicoModel medicoModel) {
        if (!medicoModel.getSenha().equals(medicoModel.getConfirmacaoSenha())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As senhas nao conferem!");
        }
        if (medicoRepository.existsByEmail(medicoModel.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nao e possivel criar uma conta com esse email.");
        }
        if (medicoRepository.existsByCpf(medicoModel.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nao e possivel criar uma conta com esse cpf.");
        }
        medicoModel.setSenha(passwordEncoder.encode(medicoModel.getSenha()));
        medicoModel.setConfirmacaoSenha(passwordEncoder.encode(medicoModel.getSenha()));
        medicoRepository.save(medicoModel);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity login(MedicoLoginDTO medicoLoginDTO) {
        Optional<MedicoModel> medicoModelOptional = medicoRepository.
                findMedicoModelByEmail(medicoLoginDTO.getEmail());

        if (medicoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        MedicoModel medicoEncontrado = medicoModelOptional.get();
        boolean passwordMatch = passwordEncoder.matches(medicoLoginDTO.getSenha(), medicoEncontrado.getSenha());

        HttpStatus status = (passwordMatch) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).build();
    }

    public ResponseEntity logoff() {
        //TODO: implementar logoff
        return ResponseEntity.ok().build();
    }


    public ResponseEntity attendance(Agendamento agendamento) {
        if (!pacienteRepository.existsByCpf(agendamento.getPaciente().getCpf())) {
            pacienteRepository.save(agendamento.getPaciente());
        }
        agendamentoRepository.save(
                new Agendamento(null, agendamento.getDataHora(),
                        pacienteRepository.getPacienteByCpf(agendamento.getPaciente().getCpf())));


        return ResponseEntity.ok().build();
    }

}
