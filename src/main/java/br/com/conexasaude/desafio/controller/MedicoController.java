package br.com.conexasaude.desafio.controller;

import br.com.conexasaude.desafio.dto.MedicoLoginDTO;
import br.com.conexasaude.desafio.model.Agendamento;
import br.com.conexasaude.desafio.model.MedicoModel;
import br.com.conexasaude.desafio.model.Routes;
import br.com.conexasaude.desafio.service.MedicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Routes.PATH_V1)
@AllArgsConstructor
public class MedicoController {

    private MedicoService medicoService;

    @PostMapping(Routes.PAHT_SIGNUP)
    public ResponseEntity signup(
            @Valid @RequestBody MedicoModel medicoModel){
        return medicoService.signup(medicoModel);
    }

    @PostMapping(Routes.PATH_LOGIN)
    public ResponseEntity login(
            @Valid @RequestBody MedicoLoginDTO medicoLoginDTO
    ){
        return medicoService.login(medicoLoginDTO);
    }

    @PostMapping(Routes.PATH_LOGOFF)
    public ResponseEntity logoff(){
        return medicoService.logoff();
    }

    @PostMapping(Routes.PATH_ATTENDANCE)
    public ResponseEntity attendace(
            @Valid @RequestBody Agendamento agendamento
            ){
        return medicoService.attendance(agendamento);
    }




}
