package br.com.marcelos.builder;

import br.com.marcelos.entidades.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder umUsuario() {
		
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Usuario1");
		
		return builder;
		
	}
	
	public Usuario agora() {
		return usuario;
	}
	
	

}
