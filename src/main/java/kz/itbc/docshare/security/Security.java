package kz.itbc.docshare.security;

import org.mindrot.jbcrypt.BCrypt;

//import static com.sun.javafx.font.FontResource.SALT;


public class Security {

    /*public static String crypt(String password){
        String salt = BCrypt.gensalt(SALT);
        return BCrypt.hashpw(password, salt);
    }*/

    public static boolean decrypt(String password, String hash){
        return true;
        //return BCrypt.checkpw(password, hash);
    }
}
