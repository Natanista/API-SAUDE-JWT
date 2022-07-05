package br.com.conexasaude.desafio.controller;

import br.com.conexasaude.desafio.dto.MedicoLoginDTO;
import br.com.conexasaude.desafio.model.Agendamento;
import br.com.conexasaude.desafio.model.MedicoModel;
import br.com.conexasaude.desafio.model.Paciente;
import br.com.conexasaude.desafio.repository.AgendamentoRepository;
import br.com.conexasaude.desafio.repository.MedicoRepository;
import br.com.conexasaude.desafio.repository.PacienteRepository;
import br.com.conexasaude.desafio.service.MedicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class MedicoControllerTest {

    @Autowired
    private MedicoController medicoController;



    @MockBean
    PacienteRepository pacienteRepository;

    @MockBean
    AgendamentoRepository agendamentoRepository;
    @MockBean
    private MedicoRepository medicoRepository;

    private  LocalDate date = LocalDate.of(1990, 1, 8);


    @Test
    @DisplayName("signup deve retornar 200 caso cadastro seja sucesso")
    void signupDeveRetornar200() {
        //Given

        MedicoModel medicoModel = new MedicoModel(
                null,
                "natan@outlook.com",
                "password",
                "password",
                "odontologia",
                "84153540021",
                date,
                "119283838484");
        //When
        ResponseEntity response = medicoController.signup(medicoModel);

        //Then
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("signup deve retornar 409 caso email ja esteja cadastrado")
    void signupDeveRetornar409CasoEmailEstejaCadastrado() {
        //Given

        MedicoModel medicoModel = new MedicoModel(
                null,
                "natan@natan.com",
                "password",
                "password",
                "odontologia",
                "98018527040",
                date,
                "119283838484");

        //When
        when(medicoRepository.existsByEmail(medicoModel.getEmail())).thenReturn(true);
        ResponseEntity response = medicoController.signup(medicoModel);

        //Then
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("signup deve retornar 409 caso cpf ja esteja cadastrado")
    void signupDeveRetornar409CasoCPFEstejaCadastrado() {
        //Given
        MedicoModel medicoModel = new MedicoModel(
                null,
                "natan@natan.com",
                "password",
                "password",
                "odontologia",
                "98018527040",
                date,
                "119283838484");

        //When
        when(medicoRepository.existsByCpf(medicoModel.getCpf())).thenReturn(true);

        ResponseEntity response = medicoController.signup(medicoModel);

        //Then
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("signup deve retornar 401 caso confirmacao de senha esteja errada")
    void signupDeveRetornar401CasoSenhasEstejamErradas() {
        //Given
        MedicoModel medicoModel = new MedicoModel(
                null,
                "natan@natan.com",
                "password",
                "password1",
                "odontologia",
                "98018527040",
                date,
                "119283838484");

        medicoRepository.save(medicoModel);
        //When
        ResponseEntity response = medicoController.signup(medicoModel);

        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }



    @Test
    @DisplayName("Login nao deve retornar status 401 caso credenciais nao conferem")
    void loginDeveRetornarStatus401CasoCredenciaisNaoConferem() {
        //GIVEN
        MedicoModel medicoModel = new MedicoModel(
                null,
                "natan@natan.com",
                "password",
                "password",
                "odontologia",
                "98018527040",
                date,
                "119283838484");
        medicoRepository.save(medicoModel);
        MedicoLoginDTO medicoLoginDTO = new MedicoLoginDTO(
                "natan@gmail.com", "password"
        );

        //WHEN
        ResponseEntity response = medicoController.login(medicoLoginDTO);

        //THEN
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Login deve retornar 401 caso medico exista")
    void loginDeveRetornar401CasoMedicoNaoExista() {
        //GIVEN
        MedicoLoginDTO medicoLoginDTO = new MedicoLoginDTO(
                "natan@gmail.com", "password"
        );

        //WHEN
        ResponseEntity response = medicoController.login(medicoLoginDTO);

        //THEN
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("attendance deve retornar 200 caso crie o agendamento")
    void attendace() {
        //given
        Agendamento agendamento = new Agendamento(
                null,
                LocalDateTime.of(
                        1999,
                        02,
                        2,
                        2,
                        2),
                new Paciente(
                        1L,
                        "Natan",
                        "98018527040",
                        new ArrayList<>()));
        //when
        ResponseEntity response = medicoController.attendace(agendamento);

        //then
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}