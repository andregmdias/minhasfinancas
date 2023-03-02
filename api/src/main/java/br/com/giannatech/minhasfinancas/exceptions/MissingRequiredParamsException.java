package br.com.giannatech.minhasfinancas.exceptions;

import java.util.stream.Collectors;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import antlr.collections.List;

public class MissingRequiredParamsException extends MethodArgumentNotValidException {

  public MissingRequiredParamsException(MethodParameter parameter, BindingResult bindingResult) {
    super(parameter, bindingResult);
    // TODO Auto-generated constructor stub
  }

  // private List<String> handleMissingParamsException(MethodArgumentNotValidException msg) {
  // return msg.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
  // .collect(Collectors.toList());
  // }
}
