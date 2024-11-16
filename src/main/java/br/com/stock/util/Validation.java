package br.com.stock.util;

public class Validation {

    public static boolean isEmptyOrNull(Object obj){
        if(obj == null) return true;
        String objType = obj.getClass().getTypeName();
        if(objType.equals("java.lang.String"))  return String.valueOf(obj).isEmpty();
        return false;
    }


    public static String isEmptyFields(Object[][] args){
        String msgErr = "";
        for (int i = 0; i < args.length; i++) {
            if(isEmptyOrNull(args[i][0])){
                msgErr = String.format("Campo '%s' deve ser preenchido", args[i][1]);
                return msgErr;
            }
        }
        return msgErr;
    }

}
