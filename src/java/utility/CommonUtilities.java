package utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dangxuananh1997
 */
public class CommonUtilities {
    
    public static int convertPriceHangchinhhieu(String priceString) {
        try {
            if (priceString.equals("Liên hệ")) {
                return 0;
            }

            // remove 'đ' character
            priceString = priceString.substring(0, priceString.length() - 1);

            while (priceString.contains(",")) {
                int pos = priceString.indexOf(",");
                priceString = priceString.substring(0, pos) + priceString.substring(pos + 1, priceString.length());
            }

            return Integer.parseInt(priceString);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static int convertPriceXgear(String priceString) {
        try {
            if (priceString.equals("Liên hệ")) {
                return 0;
            }

            // remove 'đ' character
            priceString = priceString.substring(0, priceString.length() - 1);
            
            while (priceString.contains(".")) {
                int pos = priceString.indexOf(".");
                priceString = priceString.substring(0, pos) + priceString.substring(pos + 1, priceString.length());
            }

            return Integer.parseInt(priceString);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static String addCloseTagToLine(String line, String tag) {
        int openTagPos = line.indexOf("<" + tag);
        boolean closeTagProperly = line.indexOf("/>", openTagPos) > 0;
        
        if (!closeTagProperly) {
            int closeTagPos = line.indexOf(">", openTagPos);
            line = line.substring(0, closeTagPos) + "/" + line.substring(closeTagPos, line.length());
        }
        
        return line;
    }
    
    public static String getMD5Hash(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
}
