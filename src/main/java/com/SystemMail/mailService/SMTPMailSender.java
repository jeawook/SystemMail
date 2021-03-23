package com.SystemMail.mailService;

import com.SystemMail.exception.SMTPException;

import java.io.*;
import java.net.*;
import java.text.*;
import java.time.LocalDateTime;
import java.util.*;
// SendMail을 소켓으로 보내기..

public class SMTPMailSender implements Serializable{

    private Socket smtp ;//SMTP 서버에 접속하기 위한것
    private BufferedReader input;
    private PrintStream output;
    private String smtpServer;
    private String serverReply;
    private int port = 25;
    private String from = "pjw4825@naver.com";
    private String to = "pdj13579@nate.com";
    private String subject = "메일 연습입니다..";
    private String message = "메일 본문 내용입니다 ~ ";

    public static void main(String[] args) throws Exception{

        SMTPMailSender mail = new SMTPMailSender();
        mail.smtpServer ="mx1.nate.com";
        mail.smtp = new Socket(mail.smtpServer, mail.port);
        mail.send();

    }

    public void send() throws SMTPException {
        connect();
        hail(from,to);
        sendMessage(from, to, subject, message);
        logout();
    }

    public void connect() throws SMTPException{
        try{
            smtp = new Socket(smtpServer, port);
            input = new BufferedReader(new InputStreamReader(smtp.getInputStream()));
            output = new PrintStream(smtp.getOutputStream());
            serverReply = input.readLine();
            System.out.println(serverReply);
            if(serverReply.charAt(0)=='2' || serverReply.charAt(0)=='3'){
            }else{
                throw new SMTPException("Error connecting to SMTP server " + smtpServer+"on port"+port);
            }
        }catch(Exception e){
            throw new SMTPException(e.getMessage());
        }

    }

    public void hail(String from, String to) throws SMTPException{
        if(submitCommand("HELO " + smtpServer))
            throw new SMTPException("Error occured during HELO command.");
        if(submitCommand("MAIL FROM:"+from))
            throw new SMTPException("Error during MAIL command");
        if(submitCommand("RCPT TO:"+to))
            throw new SMTPException("Error during RCPT command.");
    }

    public void sendMessage(String from, String to, String subject, String message) throws SMTPException{
        LocalDateTime ldate_today = LocalDateTime.now();
        SimpleDateFormat lgmt_date = new SimpleDateFormat("d MMM yyyy HH:mm:ss 'GMT'");
        lgmt_date.setTimeZone(TimeZone.getTimeZone("GMT"));
        lgmt_date.format(ldate_today);
        try{
            if(submitCommand("DATA"))
                throw new SMTPException("Error during DATA command.");

            String header = "From: " + from + "\r\n";
            header += "To:"+to+"\r\n";
            header += "Subject:" + han(subject)+"\r\n";
            header += "Date:"+lgmt_date + "\r\n\r\n";
            if(submitCommand(header+han(message)+"\r\n."))
                throw new SMTPException("Error during mail transmission.");

        }catch(Exception e){
        }
    }

    private boolean submitCommand(String command) throws SMTPException{
        try{
            output.print(command+"\r\n");
            serverReply = input.readLine();
            System.out.println(serverReply);
            if(serverReply.charAt(0)=='4'||serverReply.charAt(0)=='5')
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


    public static String han(String Unicodestr) throws UnsupportedEncodingException{
        if(Unicodestr==null) return null;
        return new String(Unicodestr.getBytes("8859_1"),"KSC5601");

    }
}

