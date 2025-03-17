package com.example.tela_login.Util;

import java.util.regex.Pattern;

public class AuthUtil {

    private static final Pattern CARACTERES_ESPECIAIS = Pattern.compile("[^a-zA-Z0-9]");
    private static final Pattern REPETICAO_EXCESSIVA = Pattern.compile("(.)\\1{3,}"); // 4 ou mais caracteres repetidos


    public static String validarUsuario(String usuario) {
        if (usuario == null || usuario.isEmpty()) {
            return "Usuário não pode estar em branco.";
        }
        if (CARACTERES_ESPECIAIS.matcher(usuario).find()) {
            return "Usuário não pode conter caracteres especiais.";
        }

        if(usuario.contains(" ")) {
            return "Usuário não pode conter espaço.";
        }

        if (usuario.length() < 8 || usuario.length() > 20) {
            return "Usuário deve ter entre 8 e 20 caracteres.";
        }

        //adicionar if para usuario nao deve ter espaços
        return null; // pode retornar mensagens de sucesso
    }

    public static String validarSenha(String senha) {
        if (senha == null || senha.isEmpty()) {
            return "Senha não pode estar em branco.";
        }
        if (REPETICAO_EXCESSIVA.matcher(senha).find()) {
            return "Senha não pode conter caracteres repetidos sequencialmente.";
        }
        return null;
    }

    public static String validarConfirmacaoSenha(String senha, String confSenha){
        if (confSenha == null || confSenha.isEmpty()) {
            return "Confirmação de senha não pode estar em branco.";
        }
        if ( !senha.equals(confSenha)){
            return "Senha e confirmação de senha não correspondem";
        }
        return null;
    }

}
