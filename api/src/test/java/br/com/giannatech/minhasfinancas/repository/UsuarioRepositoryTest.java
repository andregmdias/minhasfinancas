package br.com.giannatech.minhasfinancas.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.giannatech.minhasfinancas.models.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

  @Autowired
  UsuarioRepository repository;

  @Autowired
  TestEntityManager entityManager;

  @Test
  @DisplayName("existsByEmail -> Quando não existe usuario com o email dado, retorna true")
  public void verificaAExistenciaDeUmEmailNoBanco() {
    var usuario = Usuario.builder().nome("Carlota Joaquina").email("carlotajoaquina@gmail.com")
        .senha("carlotinha123").build();

    entityManager.persist(usuario);

    var result = repository.existsByEmail("carlotajoaquina@gmail.com");

    Assertions.assertThat(result).isTrue();

  }

  @Test
  @DisplayName("existsByEmail -> Quando existe usuario com o email dado, retorna false")
  public void deveRetornarFalsoQuandoNaoEncontrarUsuarioNoBanco() {
    var usuario = Usuario.builder().nome("Carlota Joaquina").email("carlotajoaquina@gmail.com")
        .senha("carlotinha123").build();

    repository.save(usuario);

    Assertions.assertThat(repository.existsByEmail("young_yoda@gmail.com")).isFalse();
  }

  @Test
  @DisplayName("findByEmailAndSenha -> quando não encontra usuario com os dados informados, retorna empty")
  public void quandoNaoEncontraEmailESenhaCadastradaRetornaEmpty() {
    var resultado = repository.findByEmailAndSenha("pedroalvarescabral@gmail.com", "cabral123");

    Assertions.assertThat(resultado).isEmpty();
  }

  @Test
  @DisplayName("findByEmailAndSenha -> quando encontra usuario com os dados informados, retorna o usuario")
  public void quandoEncontraUsuarioComOsDadosInformadosRetornaOUsuario() {
    var usuario = Usuario.builder().nome("Pedro Alvares Cabras")
        .email("pedroalvarescabral@gmail.com").senha("cabral123").build();

    entityManager.persist(usuario);

    var resultado = repository.findByEmailAndSenha("pedroalvarescabral@gmail.com", "cabral123");

    Assertions.assertThat(resultado).isPresent();
  }
}
