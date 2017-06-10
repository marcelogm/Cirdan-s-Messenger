package util;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitário de gerenciamento de patterns e regex
 * @author marce
 */
public class UPattern {
    private static Pattern p;
    private static Matcher m;
    
    /**
     * Executa verificação
     * @param string
     * @param mask
     * @return 
     */
    public static boolean execute(String string, String mask){
        UPattern.p = Pattern.compile(mask); 
        UPattern.m = p.matcher(string);

        return m.matches();
    }
        
    /**
     * Remove problemas com UTF-8
     * @param string
     * @return 
     */
    public static String removeMarks(String string){
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,255})$";
    public static final String NAME_PATTERN = "^[a-zA-Z ]+${3,35}";
}
