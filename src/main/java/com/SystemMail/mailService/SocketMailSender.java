package com.SystemMail.mailService;

import com.SystemMail.dto.MailDto;
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

    /**
     * 메일 발송 정보 입력
     * @param mailDTO 메일 발송 정보
     * @param lookup 수신 서버 정보
     * @throws SMTPException
     */
    public void send(MailDto mailDTO, String lookup) throws SMTPException{
        connect(lookup);
        hail(mailDTO.getMailFrom(), mailDTO.getMailTo());
        sendMessage(mailDTO);
        quit();
    }

    /**
     * smtp 수신 서버 연결
     * @param lookup 수신 서버 MX 주소
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
                throw new SMTPException("Error connecting to SMTP server " + lookup+" on port "+PORT);
            }
        }catch(Exception e){
            throw new SMTPException(e.getMessage());
        }

    }

    public void hail(String from, String to) throws SMTPException{
        if(submitCommand(SMTPCommand.HELO + serverDomain))
            throw new SMTPException("Error occured during HELO command.");
        if(submitCommand(SMTPCommand.create(SMTPCommand.MAILFROM,from)))
            throw new SMTPException("Error during MAIL command");
        if(submitCommand(SMTPCommand.create(SMTPCommand.RCPTTO,to)))
            throw new SMTPException("Error during RCPT command.");
    }

    /**
     * 메일 발송
     * @param mailDTO
     * @throws SMTPException
     */

    public void sendMessage(MailDto mailDTO) throws SMTPException{
        StringBuilder sb = new StringBuilder();
        String defaultCharset = "utf-8";
        String mimeVersion = "1.0";
        String contentType = "text/html";
        try{
            if(submitCommand(SMTPCommand.DATA))
                throw new SMTPException("Error during DATA command.");
            sb.append(MailHeader.create(MailHeader.HEADER_SUBJECT, MailHeader.encodeHeader(mailDTO.getSubject(),defaultCharset)));
            sb.append(MailHeader.create(MailHeader.HEADER_FROM,MailHeader.encodeHeader(mailDTO.getMailFrom(),defaultCharset)));
            sb.append(MailHeader.create(MailHeader.HEADER_TO,MailHeader.encodeHeader(mailDTO.getMailTo(),defaultCharset)));
            sb.append(MailHeader.create(MailHeader.HEADER_REPLY_TO,MailHeader.encodeHeader(mailDTO.getReplyTo(),defaultCharset)));
            sb.append(MailHeader.create(MailHeader.HEADER_DATE,MailHeader.encodeHeader(LocalDateTime.now().toString(), defaultCharset)));
            sb.append(MailHeader.create(MailHeader.HEADER_MIME_VERSION,MailHeader.encodeHeader(mimeVersion,defaultCharset)));
            sb.append(MailHeader.create(MailHeader.HEADER_CONTENT_TYPE,MailHeader.encodeHeader(contentType, defaultCharset)));
            sb.append(MailHeader.create(MailHeader.HEADER_CONTENT_TRANSFER_ENCODING,MailHeader.encodeHeader(mailDTO.getEncoding(),defaultCharset)));
            if(submitCommand(sb.toString()+mailDTO.getContent()+"\r\n."))
                throw new SMTPException("Error during mail transmission.");

        }catch(Exception e){
        }
    }

    /**
     * smtp 명령어 실행
     * @param command HELO, MAIL FROM, REPLY TO,
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