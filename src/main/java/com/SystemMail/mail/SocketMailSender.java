package com.SystemMail.mail;

import com.SystemMail.domain.entity.Email;
import com.SystemMail.dto.MailDto;
import com.SystemMail.exception.SMTPException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

@Component
public class SocketMailSender{

    private Socket smtp;
    private BufferedReader input;
    private PrintStream output;
    private String serverReply;
    static private final String serverDomain = "sender.com";
    static private final int PORT = 25;


    /**
     * 메일 발송 정보 입력
     * @param mailDto 메일 발송 정보
     * @throws SMTPException
     */
    @Async
    public void send(MailDto mailDto) throws SMTPException{
        connect(mailDto.getEmail().getDomain());
        hail(mailDto.getHeaderDto().getMailTo(), mailDto.getHeaderDto().getMailFrom());
        sendMessage(mailDto);
        quit();
    }

    /**
     * smtp 수신 서버 연결
     * @param lookup 수신 서버 MX 주소
     * @throws SMTPException
     */
    private void connect(String lookup) throws SMTPException{
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

    private void hail(Email mailFrom, Email mailTo) throws SMTPException{
        if(submitCommand(SMTPCommand.HELO + serverDomain))
            throw new SMTPException("Error occured during HELO command.");
        if(submitCommand(SMTPCommand.create(SMTPCommand.MAILFROM,mailFrom.getAddress())))
            throw new SMTPException("Error during MAIL command");
        if(submitCommand(SMTPCommand.create(SMTPCommand.RCPTTO,mailTo.getAddress())))
            throw new SMTPException("Error during RCPT command.");
    }

    /**
     * 메일 발송
     * @param mailDto
     * @throws SMTPException
     */

    private void sendMessage(MailDto mailDto) throws SMTPException{
        StringBuilder sb = new StringBuilder();
        try{
            if(submitCommand(SMTPCommand.DATA))
                throw new SMTPException("Error during DATA command.");
                sb.append(mailDto.getHeaderDto().getHeaderInfo());
            if(submitCommand(sb.toString()+mailDto.getContent()+"\r\n."))
                throw new SMTPException("Error during mail transmission.");
        }catch(Exception e){
        }
    }

    /**
     * smtp 명령어 실행
     * @param command HELO, MAIL FROM, REPLY TO
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
    private void quit() throws SMTPException {
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