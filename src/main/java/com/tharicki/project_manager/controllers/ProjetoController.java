package com.tharicki.project_manager.controllers;

import com.tharicki.project_manager.models.Projeto;
import com.tharicki.project_manager.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@Controller
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @GetMapping("/projeto")
    public String listProjetos(Model model) {
        model.addAttribute("projetos", service.listAll());
        return "projetos";
    }

    @RequestMapping(value = "/projeto", method = RequestMethod.POST)
    public String salvar(@Valid Projeto projeto, BindingResult erros, Model model) {
        if (erros.hasErrors()) {
            model.addAttribute("erros", erros.getAllErrors());
            return "erros";
        }
        service.save(projeto);

        model.addAttribute("mensagem", "Registro salvo com sucesso!");

        return "mensagem";
    }

    @RequestMapping(value = "/projeto/{id}/excluir", method = RequestMethod.GET)
    public String excluir(@PathVariable Long id, Model model) {
        try {
            service.delete(id);
            model.addAttribute("mensagem", "Projeto excluido com sucesso!");
        } catch (ValidationException e) {
            model.addAttribute("erro", e.getMessage());
            return "/projeto/form";
        }
        return "/projeto/index";
    }

    @ResponseBody
    @RequestMapping(value = "/projeto/{idProjeto}/adicionarmembro/{idPessoa}", method = RequestMethod.PUT,
            produces = "application/json; charset=UTF-8")
    public String addProjectMember(@PathVariable Long idProjeto, @PathVariable Long idPessoa) {
        try {
            this.service.addMembro(idProjeto, idPessoa);
            return String.format("{\"message\": \"%s\"}", "Membro adicionado com sucesso");
        } catch (ValidationException e) {
            return String.format("{\"erro\": \"%s\"}", e.getMessage());
        }

    }
}
