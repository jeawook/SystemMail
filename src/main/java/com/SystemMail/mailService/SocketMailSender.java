package com.SystemMail.mailService;

import com.SystemMail.dns.DNSLookup;
import com.SystemMail.domain.MailDTO;
import com.SystemMail.exception.SMTPException;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class SocketMailSender{

    private Socket smtp;
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

    /**
     * 메일 발송 정보 입력
     * @param mailDTO 메일 발송 정보
     * @param lookup 수신 서버 정보
     * @throws SMTPException
     */

    public void send(MailDTO mailDTO, String lookup) throws SMTPException{
        connect(lookup);
        hail(mailDTO.getMailFrom(), mailDTO.getMailTo());
        sendMessage(mailDTO);
        quit();
    }

    /**
     * smtp 수신 서버 연결
     * @param lookup 수신 서버 정보
     * @throws SMTPException
     */
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

    /**
     * 메일 발송
     * @param mailDTO
     * @throws SMTPException
     */

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

    /**
     * smtp 명령어 실행
     * @param command
     * @return
     * @throws SMTPException
     */
    private boolean submitCommand(String command) throws SMTPException{
        try{
            output.print(command+"\r\n");
            serverReply = input.readLine();
            if(serverReply.charAt(0)=='4'||serverReply.charAt(0)=='5')
                return true;
            else return false;
        }catch(Exception e){
            throw new SMTPException(e.getMessage());
        }
    }

    /**
     * smtp 통신 종료
     * @throws SMTPException
     */
    public void quit() throws SMTPException {
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