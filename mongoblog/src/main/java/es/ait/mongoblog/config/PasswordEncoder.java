package es.ait.mongoblog.config;

import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Class to encode passwords.
 *
 * @author aitkiar
 */
public class PasswordEncoder
{
    /**
     * Encodes an String using 1024 iterations of the PBKDF2WithHmacSHA512 algorithm
     * and returns the result as BASE64 encoded for easy BBDD store an comparison.
     * 
     * @param string
     * @param salt
     * @return cadena en base64 de 1024 caracteres.
     * @throws Exception 
     */
    public static String encode( String string, String salt ) throws Exception
    {
        PBEKeySpec spec = new PBEKeySpec( string.toCharArray(), salt.getBytes(), 1024, 1024 * 6 );
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        
        return Base64.getEncoder().encodeToString( skf.generateSecret(spec).getEncoded() );
    }
    
    /**
     * Generates a random password of the indicated length
     * @param length
     * @return
     */
    public static String randomPassword( int length )
    {
    	String password = "";
    	for ( int i = 0; i < length; i ++ )
    	{
    		password += randomPasswordCharacter();
    	}
    	return password;
    }
    
    private static char randomPasswordCharacter()
    {
    	String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789|@#~¬\\ªº\"·$%&/()=?¿[]+*{}çÇ,;.:-_";
    	int pos = new Double( Math.floor( Math.random() * characters.length())).intValue();
    	return characters.charAt( pos < characters.length() ? pos : pos -1 );
    }
}

