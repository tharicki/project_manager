package com.tharicki.project_manager.controllers;

import com.tharicki.project_manager.models.Pessoa;
import com.tharicki.project_manager.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping("/pessoa")
    public String listPessoas(Model model) {
        model.addAttribute("pessoas", service.listAll());
        return "pessoas";
    }

    @RequestMapping(value="/pessoa", method= RequestMethod.POST)
    public String salvar(@Valid Pessoa pessoa, BindingResult erros, Model model) {
        if (erros.hasErrors()) {
            model.addAttribute("erros", erros.getAllErrors());
            return "erros";
        }
        service.save(pessoa);

        model.addAttribute("mensagem", "Pessoa salva com sucesso!");

        return "mensagem";
    }

    @RequestMapping(value="/pessoa/{id}/excluir", method=RequestMethod.GET)
    public String excluir(@PathVariable Long id, Model model) {
        service.delete(id);
        model.addAttribute("mensagem", "Pessoa excluida com sucesso!");

        return "mensagem";
    }

}
