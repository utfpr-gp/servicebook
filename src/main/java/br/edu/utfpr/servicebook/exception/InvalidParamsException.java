package br.edu.utfpr.servicebook.exception;

//retorna uma resposta 404 se os parâmetros forem inválidos
//@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Parâmetro(s) inválido(s).")
public class InvalidParamsException extends IllegalArgumentException {

	public InvalidParamsException(String message){
		super(message);
	}
}
