package com.tharicki.project_manager.services;

import com.tharicki.project_manager.models.Pessoa;
import com.tharicki.project_manager.models.Projeto;
import com.tharicki.project_manager.models.Status;
import com.tharicki.project_manager.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> listAll() {
        return projetoRepository.findAll();
    }

    public Projeto findById(long id) {
        Optional<Projeto> bookmark = this.projetoRepository.findById(id);
        return bookmark.orElse(null);
    }

    public Projeto save(Projeto projeto) {
        return this.projetoRepository.save(projeto);
    }

    public void delete(Long id) {
        Projeto projeto = findById(id);

        //não deixa excluir nos status selecionados
        if (Arrays.asList(Status.INICIADO, Status.EM_ANDAMENTO, Status.ENCERRADO).contains(projeto.getStatus())) {
            throw new ValidationException("Não é permitido excluir um projeto já iniciado!");
        }
        this.projetoRepository.deleteById(id);
    }

    public Projeto addMembro(Long idProjeto, Long idPessoa) throws ValidationException {
        Projeto projeto = findById(idProjeto);

        PessoaService pessoaService = new PessoaService();
        Pessoa membro = pessoaService.findById(idPessoa);

        if (projeto == null) {
            throw new ValidationException("Projeto não encontrado");
        }
        if (membro == null) {
            throw new ValidationException("Pessoa não encontrada");
        }
        if (!membro.isFuncionario()) {
            throw new ValidationException("Somente funcionários podem ser membros de projeto");
        }
        projeto.getMembros().add(membro);

        return projetoRepository.save(projeto);
    }

}
