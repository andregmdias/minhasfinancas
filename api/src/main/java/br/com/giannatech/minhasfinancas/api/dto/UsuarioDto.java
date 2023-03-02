package br.com.giannatech.minhasfinancas.api.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDto {

  @NotEmpty(message = "Parâmetro nome é obrigatório")
  private String nome;

  @NotEmpty(message = "Parâmetro email é obrigatório")
  private String email;

  @NotEmpty(message = "Parâmetro senha é obrigatório")
  private String senha;
}
