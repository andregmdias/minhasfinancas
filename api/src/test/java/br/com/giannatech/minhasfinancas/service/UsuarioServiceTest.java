package br.com.giannatech.minhasfinancas.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.com.giannatech.minhasfinancas.exceptions.ErroAutenticacaoException;
import br.com.giannatech.minhasfinancas.exceptions.RegraNegocioException;
import br.com.giannatech.minhasfinancas.models.entity.Usuario;
import br.com.giannatech.minhasfinancas.repository.UsuarioRepository;
import br.com.giannatech.minhasfinancas.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

  @SpyBean
  UsuarioServiceImpl service;

  @MockBean
  UsuarioRepository repository;

  @Test
  @DisplayName("autenticar -> quando executado com sucesso, retorna o usuário com o email e senha informados")
  public void quandoEcontradoOUsuarioComEmailESenhaInformadosRetornaOUsuario() {

    var usuario = Usuario.builder().id(1L).nome("Carlota Joaquina")
        .email("carlotajoaquina@gmail.com").senha("carlotinha123").build();


    Mockito.when(repository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha()))
        .thenReturn(Optional.of(usuario));

    var resultado = service.autenticar("carlotajoaquina@gmail.com", "carlotinha123");

    Assertions.assertEquals(resultado, usuario);
  }

  @Test
  @DisplayName("autenticar -> quando o email ou senha estão incorretos, deve lançar uma excessão")
  public void quandoOEmailEstaCorretoEASenhaErradaLancaUmaExcessao() {

    Mockito.when(repository.findByEmailAndSenha(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(ErroAutenticacaoException.class, () -> {
      service.autenticar("carlotajoaquina@gmail.com", "carlota123");
    }, "Não foi encontrado usuário com os dados informados.");
  }

  @Test
  @DisplayName("salvarUsuario-> cadastra com sucesso")
  public void deveCadastrarOUsuarioComSucesso() {
    var usuario = Usuario.builder().nome("Carlota Joaquina").email("carlotajoaquina@gmail.com")
        .senha("carlotinha123").build();

    Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
    Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

    var usuarioSalvo = service.salvarUsuario(new Usuario());
    assertAll("usuario", () -> assertEquals(usuarioSalvo.getNome(), usuario.getNome()),
        () -> assertEquals(usuarioSalvo.getEmail(), usuario.getEmail()),
        () -> assertEquals(usuarioSalvo.getId(), usuario.getId()),
        () -> assertEquals(usuarioSalvo.getSenha(), usuario.getSenha()));
  }

  @Test
  @DisplayName("salvarUsuario -> lança uma excessão quando o email do usuário já está cadastrado na base de dados")
  public void deveLancarExcessaoQuandoUsuarioJaExiste() {

    var email = "email@email.com";

    var usuario = Usuario.builder().email(email).build();

    Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(Mockito.anyString());

    Mockito.verify(repository, Mockito.never()).save(usuario);

    Assertions.assertThrows(RegraNegocioException.class, () -> {
      service.salvarUsuario(usuario);
    }, "Já existe um usuário cadastrado com este email");
  }

  @Test
  @DisplayName("validarEmail -> não há retorno quando o email de cadastro é valido")
  public void deveValidarEmailCadastrado() {
    Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

    Assertions.assertDoesNotThrow(() -> {
      service.validarEmail("carlotajoaquina@gmail.com");
    });
  }

  @Test
  @DisplayName("validarEmail -> lança uma excessão quando o email já está cadastrado na base de dados")
  public void deveLancarUmaExcessaoQuandoEmailJaExistir() {

    Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

    Assertions.assertThrows(RegraNegocioException.class, () -> {

      service.validarEmail("carlotajoaquina@gmail.com");
    }, "Já existe um usuário cadastrado com este email");
  }
}
