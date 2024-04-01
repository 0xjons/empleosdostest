package org.xjons.empleosdos.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PlainTextPasswordEncoder implements PasswordEncoder  {

	public PlainTextPasswordEncoder() {
		// TODO Auto-generated constructor stub
	}

 @Override
 public String encode(CharSequence rawPassword) {
     return rawPassword.toString(); // Devuelve la contraseña sin codificar
 }

 @Override
 public boolean matches(CharSequence rawPassword, String encodedPassword) {
     return rawPassword.toString().equals(encodedPassword); // Compara las contraseñas en texto plano
 }

}
