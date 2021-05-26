package com.SystemMail.mail;

import com.SystemMail.Service.SendInfoService;
import com.SystemMail.common.SmtpCode;
import com.SystemMail.domain.entity.Email;
import com.SystemMail.domain.entity.SendInfo;
import com.SystemMail.dto.MailDto;
import com.SystemMail.exception.SMTPException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Component
@RequiredArgsConstructor
public class SocketMailSender{

    private Socket smtp;
    private BufferedReader input;
    private PrintStream output;
    private String serverReply;
    static private final String serverDomain = "sender.com";
    static private final int PORT = 25;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SendInfoService sendInfoService;


    /**
     * 메일 발송 정보 입력
     * @param mailDto 메일 발송 정보
     * @throws SMTPException
     */
    @Async("threadPoolTaskExecutor")
    public void send(MailDto mailDto){
        SendInfo sendInfo = mailDto.getSendInfo();

        try {
            connect(mailDto.getEmail().getDomain());
            hail(mailDto.getHeaderDto().getMailTo(), mailDto.getHeaderDto().getMailFrom());
            sendMessage(mailDto);
            quit();
            sendInfo.setComplete();
        } catch (SocketTimeoutException e) {

        } catch (SMTPException e) {

        } catch (Exception e) {

        }


        sendInfoService.saveSendInfo(sendInfo);
    }

    /**
     * smtp 수신 서버 연결
     * @param lookup 수신 서버 MX 주소
     * @throws SMTPException
     */
    private void connect(String lookup) throws SocketTimeoutException, Exception{
        try{
            smtp = new Socket(lookup, PORT);
            smtp.setSoTimeout(1000);
            input = new BufferedReader(new InputStreamReader(smtp.getInputStream()));
            output = new PrintStream(smtp.getOutputStream());
            serverReply = input.readLine();
            if(serverReply.startsWith(SmtpCode.SUCCESS) || serverReply.startsWith(SmtpCode.PROCESS)){

            }else{
                logger.trace("Error connecting to SMTP server " + lookup+" on port "+PORT);
            }
        } catch (SocketTimeoutException e) {
            logger.trace("SocketTimeoutException :  " + e.getMessage()+" lookup : "+lookup);
        }catch (Exception e){
            logger.trace("exception :  " + e.getMessage()+" lookup : "+lookup);
        }

    }

    private void hail(Email mailFrom, Email mailTo) throws SMTPException{
        String heloCommand = submitCommand(SMTPCommand.HELO + serverDomain);
        if(heloCommand.startsWith(SmtpCode.SUCCESS)) {
            logger.trace(" HELO error :  " + serverDomain);
            throw new SMTPException("Error occured during HELO command." + heloCommand);
        }
        String mailFromCommand = submitCommand(SMTPCommand.create(SMTPCommand.MAILFROM, mailFrom.getAddress()));
        if(mailFromCommand.startsWith(SmtpCode.SUCCESS)) {
            logger.trace(" mailFrom error :  " + mailFrom.getAddress());
            throw new SMTPException("Error during MAIL command : "+mailFromCommand);
        }
        if(submitCommand(SMTPCommand.create(SMTPCommand.RCPTTO,mailTo.getAddress())).startsWith(SmtpCode.SUCCESS)) {
            logger.trace(" rcptTo error :  " + mailTo.getAddress());
            throw new SMTPException("Error during RCPT command.");
        }
    }

    /**
     * 메일 발송
     * @param mailDto
     * @throws SMTPException
     */

    private void sendMessage(MailDto mailDto) throws SMTPException{
        StringBuilder sb = new StringBuilder();
        try{
            if(submitCommand(SMTPCommand.DATA).startsWith(SmtpCode.PROCESS)) {
                throw new SMTPException("Error during DATA command.");
            }
            sb.append(mailDto.getHeaderDto().getHeaderInfo());
            if(submitCommand(sb.toString()+mailDto.getContent()+"\r\n.").startsWith(SmtpCode.SUCCESS)) {
                throw new SMTPException("Error during mail transmission.");
            }
        }catch(Exception e){

        }
    }

    /**
     * smtp 명령어 실행
     * @param command HELO, MAIL FROM, REPLY TO
     * @return
     * @throws SMTPException
     */
    private String submitCommand(String command) throws SMTPException{
        try{
            output.print(command+"\r\n");
            serverReply = input.readLine();
            return serverReply;
        }catch(Exception e){
            logger.trace("Command :" + command+ " Error :  " + e.getMessage());
            throw new SMTPException(e.getMessage());
        }
    }

    /**
     * smtp 통신 종료
     * @throws SMTPException
     */
    private void quit() throws SMTPException {
        try{
            if(submitCommand("Quit").startsWith(SmtpCode.SERVER_CLOSE)) {
                logger.trace("Command : Quit error");
                throw new SMTPException("Error during QUIT command");
            }
            input.close();
            output.flush();
            output.close();
            smtp.close();
        }catch(Exception e){
            logger.trace("Command : Quit Error :  " + e.getMessage());
            throw new SMTPException("Error during QUIT command Exception : "+e.getMessage() );
        }
    }
}