package br.com.giannatech.minhasfinancas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.giannatech.minhasfinancas.models.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
