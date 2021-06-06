package com.SystemMail.mail;

import com.SystemMail.Service.MailResultDetailService;
import com.SystemMail.Service.MailResultInfoService;
import com.SystemMail.common.SmtpCode;
import com.SystemMail.dns.DNSLookup;
import com.SystemMail.domain.entity.*;
import com.SystemMail.dto.MailDto;
import com.SystemMail.exception.SMTPException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;

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

    private final MailResultInfoService mailResultInfoService;
    private final MailResultDetailService mailResultDetailService;
    private final DNSLookup dnsLookup;



    /**
     * 메일 발송 정보 입력
     * @param mailDto 메일 발송 정보
     * @throws SMTPException
     */
    //@Async("threadPoolTaskExecutor")
    public void send(MailDto mailDto){
        SendInfo sendInfo = mailDto.getSendInfo();
        try {
            String lookup = dnsLookup.lookup(sendInfo.getMailGroup().getEmail().getDomain());
            logger.debug("lookup : "+lookup);
            connect(lookup);
            String helo = SMTPCommand.create(SMTPCommand.HELO, serverDomain);
            sendMessage(helo,SmtpCode.GREETING);
            String mailFrom = SMTPCommand.create(SMTPCommand.MAILFROM, mailDto.getSendInfo().getMailInfo().getMailFrom().getAddress());
            sendMessage(mailFrom,SmtpCode.SUCCESS);
            String rcptTo = SMTPCommand.create(SMTPCommand.RCPTTO, mailDto.getSendInfo().getMailGroup().getEmail().getAddress());
            sendMessage(rcptTo,SmtpCode.SUCCESS);
            String data = SMTPCommand.create(SMTPCommand.DATA, mailDto.getData());
            sendMessage(data, SmtpCode.SUCCESS);

            sendMessage(SMTPCommand.QUIT, SmtpCode.SERVER_CLOSE);
            //sendMessage(mailDto);
            quit();
            resultProcess(sendInfo, quit, ResultStatus.SUCCESS);
        } catch (ConnectException e) {
            resultProcess(sendInfo,"", ResultStatus.SERVER_ERROR);
        } catch (SMTPException e) {
            resultProcess(sendInfo, e.getMessage(), ResultStatus.NETWORK_ERROR);
        } catch (Exception e) {
            resultProcess(sendInfo, "", ResultStatus.UNKNOWN_ERROR);
        }
    }

    /**
     * smtp 수신 서버 연결
     * @param lookup 수신 서버 MX 주소
     * @throws SMTPException
     */
    private void connect(String lookup) throws Exception{

        smtp = new Socket(lookup, PORT);
        smtp.setSoTimeout(300);
        input = new BufferedReader(new InputStreamReader(smtp.getInputStream()));
        output = new PrintStream(smtp.getOutputStream());
        serverReply = input.readLine();
        System.out.println("server : "+serverReply);
        if(serverReply.startsWith(SmtpCode.SUCCESS) || serverReply.startsWith(SmtpCode.PROCESS) || serverReply.startsWith(SmtpCode.GREETING)){

        }else{
            logger.debug("Error connecting to SMTP server " + lookup+" on port "+PORT);
        }
    }


    private String sendMessage(String message, String returnCode) throws SMTPException{
        String resultMessage = submitCommand(message);
        if(resultMessage.startsWith(returnCode)) {
            logger.debug(" error :  " + serverDomain);
            throw new SMTPException("command Exception" + resultMessage);
        }
        return resultMessage;
    }

    /**
     * 메일 발송
     * @param mailDto
     * @throws SMTPException
     */

   /* private void sendMessage(MailDto mailDto) throws SMTPException{
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
    }*/

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

    private void quit() throws SMTPException {
        try{
            input.close();
            output.flush();
            output.close();
            smtp.close();
        }catch(Exception e){
            logger.trace("close Error :  " + e.getMessage());
            throw new SMTPException("close Exception : "+e.getMessage() );
        }
    }

    private void resultProcess(SendInfo sendInfo, String message, ResultStatus resultStatus) {
        MailResultInfo mailResultInfo = sendInfo.getMailResultInfo();
        if (resultStatus.equals(ResultStatus.SUCCESS)) {
            mailResultInfo.addSuccess();
        } else {
            mailResultInfo.addFail();
        }
        MailResultDetail mailResultDetail = MailResultDetail.builder()
                .message(message)
                .email(sendInfo.getMailGroup().getEmail().getAddress())
                .resultStatus(resultStatus)
                .sendInfo(sendInfo)
                .build();
        mailResultDetailService.saveResultDetail(mailResultDetail);
    }
}