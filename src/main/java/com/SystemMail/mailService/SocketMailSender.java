package com.SystemMail.mailService;

import com.SystemMail.dns.DNSLookup;
import com.SystemMail.domain.MailDTO;
import com.SystemMail.exception.SMTPException;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.time.LocalDateTime;
import java.util.*;

public class SocketMailSender{

    private Socket smtp ;//SMTP 서버에 접속하기 위한것
    private BufferedReader input;
    private PrintStream output;
    private String serverReply;
    static private final String serverDomain = "sender.com";
    static private final int PORT = 25;

    public static void main(String[] args) throws Exception{
        String content = "<html><head><meta name=\"GENERATOR\" content=\"MSHTML 9.00.8112.16737\"></head><body>한글깨짐?\n" +
                "</body></html>";
        MailDTO mailDTO = MailDTO.builder()
                .mailFrom("pdj13579@nate.com")
                .mailTo("pdj13579@nate.com")
                .content(content)
                .encoding("8bit")
                .subject("메일 제목")
                .domain("nate.com")
                .build();
        DNSLookup dnsLookup = new DNSLookup();
        String lookup = dnsLookup.lookup(mailDTO.getDomain());
        SocketMailSender mail = new SocketMailSender();
        mail.send(mailDTO,lookup);

    }

    public void send(MailDTO mailDTO, String lookup) throws SMTPException{
        connect(lookup);
        hail(mailDTO.getMailFrom(), mailDTO.getMailTo());
        sendMessage(mailDTO);
        logout();
    }

    public void connect(String lookup) throws SMTPException{
        try{
            smtp = new Socket(lookup, PORT);
            input = new BufferedReader(new InputStreamReader(smtp.getInputStream()));
            output = new PrintStream(smtp.getOutputStream());
            serverReply = input.readLine();
            if(serverReply.startsWith("250") || serverReply.startsWith("354")|| serverReply.startsWith("221")){
            }else{
                throw new SMTPException("Error connecting to SMTP server " + lookup+" on port"+PORT);
            }
        }catch(Exception e){
            throw new SMTPException(e.getMessage());
        }

    }

    public void hail(String from, String to) throws SMTPException{
        if(submitCommand("HELO " + serverDomain))
            throw new SMTPException("Error occured during HELO command.");
        if(submitCommand("MAIL FROM:"+from))
            throw new SMTPException("Error during MAIL command");
        if(submitCommand("RCPT TO:"+to))
            throw new SMTPException("Error during RCPT command.");
    }

    public void sendMessage(MailDTO mailDTO) throws SMTPException{
        StringBuilder sb = new StringBuilder();
        try{
            if(submitCommand("DATA"))
                throw new SMTPException("Error during DATA command.");
            String subject = MailHeader.create(MailHeader.HEADER_SUBJECT, MailHeader.encodeHeader(mailDTO.getSubject(),"utf-8"));
            sb.append(subject);
            sb.append("From:<").append(mailDTO.getMailFrom()).append(">").append("\r\n");
            sb.append("To:<").append(mailDTO.getMailTo()).append(">").append("\r\n");
            sb.append("Reply-To:<").append(mailDTO.getReplyTo()).append(">").append("\r\n");
            sb.append("Date:").append(LocalDateTime.now()).append("\r\n");
            sb.append("Mime-Version: 1.0;").append("\r\n");
            sb.append("Content-Type: text/html; charset=\"utf-8\";").append("\r\n");
            sb.append("Content-Transfer-Encoding :").append(mailDTO.getEncoding()).append("\r\n");



            sb.append("\r\n");
            if(submitCommand(sb.toString()+mailDTO.getContent()+"\r\n."))
                throw new SMTPException("Error during mail transmission.");

        }catch(Exception e){
        }
    }


    private boolean submitCommand(String command) throws SMTPException{
        try{
            output.print(command+"\r\n");
            System.out.println("send : "+command);
            serverReply = input.readLine();
            System.out.println("return : "+serverReply);
            if(serverReply.charAt(0)=='4'||serverReply.charAt(0)=='5')//전송실패등..
                return true;
            else return false;
        }catch(Exception e){
            throw new SMTPException(e.getMessage());
        }
    }

    public void logout() throws SMTPException {
        try{
            if(submitCommand("Quit"))
                throw new SMTPException("Error during QUIT command");
            input.close();
            output.flush();
            output.close();
            smtp.close();
        }catch(Exception e){}
    }
}