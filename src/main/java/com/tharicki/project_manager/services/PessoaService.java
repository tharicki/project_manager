package com.tharicki.project_manager.services;

import com.tharicki.project_manager.models.Pessoa;
import com.tharicki.project_manager.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listAll() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> findByName(String name) {
        return pessoaRepository.findByNomeContainingIgnoreCase(name);
    }

    public Pessoa findById(long id) {
        Optional<Pessoa> bookmark = this.pessoaRepository.findById(id);
        return bookmark.orElse(null);
    }

    public Pessoa save(Pessoa pessoa) {
        return this.pessoaRepository.save(pessoa);
    }

    public void delete(Long id) {
        this.pessoaRepository.deleteById(id);
    }
}
