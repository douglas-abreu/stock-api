package br.com.stock.util;

public class MsgSystem {


    public static String sucCreate(String objName){
        return String.format("%s criado(a) com sucesso", objName);
    }

    public static String sucUpdate(String objName){
        return String.format("%s atualizado(a) com sucesso", objName);
    }

    public static String sucDelete(String objName){
        return String.format("%s deletado(a) com sucesso", objName);
    }

    public static String sucGet(String objName){
        return String.format("%s encontrado(a) com sucesso", objName);
    }

    public static String errGet(String objName){
        return String.format("%s não encontrado(a) com o id informado", objName);
    }

    public static String errLogin() { return "Usuário ou senha inválido"; }


}
