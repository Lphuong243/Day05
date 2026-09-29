package murach.util;

import util.MailUtil;
import jakarta.mail.MessagingException;

public class MailUtilGmail {
    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) 
            throws MessagingException {
        MailUtil.sendMail(to, from, subject, body, bodyIsHTML);
    }
}
