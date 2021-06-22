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
import org.springframework.scheduling.annotation.Async;
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
        String resultMessage = "";
        ResultStatus resultStatus = ResultStatus.SUCCESS;
        SendInfo sendInfo = mailDto.getSendInfo();
        try {
            String lookup = dnsLookup.lookup(sendInfo.getMailGroup().getEmail().getDomain());
            logger.debug("lookup : "+lookup);
            connect(lookup);
            String helo = SMTPCommand.create(SMTPCommand.HELO, serverDomain);
            sendMessage(helo,SmtpCode.SUCCESS);
            String mailFrom = SMTPCommand.create(SMTPCommand.MAILFROM, mailDto.getSendInfo().getMailInfo().getMailFrom().getAddress());
            sendMessage(mailFrom,SmtpCode.SUCCESS);
            String rcptTo = SMTPCommand.create(SMTPCommand.RCPTTO, mailDto.getSendInfo().getMailGroup().getEmail().getAddress());
            sendMessage(rcptTo,SmtpCode.SUCCESS);
            String data = SMTPCommand.create(SMTPCommand.DATA, mailDto.getData());
            sendMessage(data, SmtpCode.SUCCESS);

            resultMessage = sendMessage(SMTPCommand.QUIT, SmtpCode.SERVER_CLOSE);
        } catch (ConnectException e) {
            resultStatus = ResultStatus.NETWORK_ERROR;
            resultMessage = e.getMessage();
        } catch (SMTPException e) {
            resultStatus = ResultStatus.SERVER_ERROR;
            resultMessage = e.getMessage();
        } catch (Exception e) {
            resultStatus = ResultStatus.UNDEFINED_ERROR;
            resultMessage = e.getMessage();
        }
        resultProcess(sendInfo, resultMessage, resultStatus);
        quit();
    }

    /**
     * smtp 수신 서버 연결
     * @param lookup 수신 서버 MX 주소
     * @throws SMTPException
     */
    private void connect(String lookup) throws Exception{

        smtp = new Socket(lookup, PORT);
        smtp.setSoTimeout(10000);
        input = new BufferedReader(new InputStreamReader(smtp.getInputStream()));
        output = new PrintStream(smtp.getOutputStream());
        String serverReply = input.readLine();
        String resultCode = getCode(serverReply);
        if (!resultCode.equals(SmtpCode.GREETING)) {
            throw new SMTPException(serverReply, resultCode);
        }

    }

    /**
     * return code 추출 return 메시지의 앞 3자리는 숫자
     * @param message return message
     * @return code
     * @throws SMTPException
     */

    private String getCode(String message) throws SMTPException{
        if(message.length() < 3) {
            throw new SMTPException("Smtp protocol Exception ", SmtpCode.SERVER_ERROR);
        }
        return message.substring(0, 3);
    }


    private String sendMessage(String message, String returnCode) throws SMTPException{
        String resultMessage = submitCommand(message);
        String code = getCode(resultMessage);
        logger.debug("send : "+message);
        logger.debug("return : "+resultMessage);
        logger.debug("code : "+code);
        logger.debug("returnCode : "+returnCode);
        logger.debug("code.equals(returnCode) : "+code.equals(returnCode));
        if(!code.equals(returnCode)) {
            throw new SMTPException("Server Error " + resultMessage, code);
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
            return input.readLine();
        }catch(Exception e){
            logger.trace("Command :" + command+ " Error :  " + e.getMessage());
            throw new SMTPException(e.getMessage(), SmtpCode.SERVER_ERROR);
        }
    }

    private void quit(){
        try{
            input.close();
            output.flush();
            output.close();
            smtp.close();
        }catch(Exception e){
            logger.trace("close Error :  " + e.getMessage());
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