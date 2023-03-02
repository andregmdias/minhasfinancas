package br.com.giannatech.minhasfinancas.resources;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import br.com.giannatech.minhasfinancas.api.dto.UsuarioDto;
import br.com.giannatech.minhasfinancas.models.entity.Usuario;
import br.com.giannatech.minhasfinancas.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioResourceTest {

  @InjectMocks
  UsuarioResource resource;

  @Mock
  UsuarioService service;

  @Mock
  UsuarioDto dto;

  @Test
  public void salvaUsuarioComSucesso() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    var usuarioDto = UsuarioDto.builder().nome("Carlota Joaquina")
        .email("carlotajoaquina@gmail.com").senha("carlotinha123").build();

    var usuario = Usuario.builder().id(1L).nome("Carlota Joaquina")
        .email("carlotajoaquina@gmail.com").senha("carlotinha123").build();

    Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

    var responseEntity = resource.salvar(usuarioDto);

    Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);

    var usuarioSalvo = (Usuario) responseEntity.getBody();

    assertAll("usuario", () -> assertEquals(usuarioSalvo.getNome(), usuario.getNome()),
        () -> assertEquals(usuarioSalvo.getEmail(), usuario.getEmail()),
        () -> assertEquals(usuarioSalvo.getId(), usuario.getId()),
        () -> assertEquals(usuarioSalvo.getSenha(), usuario.getSenha()));
  }
}
