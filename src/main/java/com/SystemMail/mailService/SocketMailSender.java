package com.SystemMail.mailService;

import com.SystemMail.dns.DNSLookup;
import com.SystemMail.exception.SMTPException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;



public class SocketMailSender {
    private BufferedReader input;
    private PrintStream output;

    public static void sendMail(String smtpServer, String sender, String recipient, String content)
            throws Exception {
        Socket socket=new Socket(smtpServer, 25);
        BufferedReader br=new BufferedReader(new InputStreamReader( socket.getInputStream() ) );
        PrintWriter pw=new PrintWriter( socket.getOutputStream(), true );
        System.out.println("서버에 연결되었습니다.");

        String line=br.readLine();
        System.out.println("응답:"+line);
        if (!line.startsWith("220")) throw new Exception("SMTP서버가 아닙니다:"+line);

        System.out.println("HELO 명령을 전송합니다.");
        pw.println("HELO mydomain.name");
        line=br.readLine();
        System.out.println("응답:"+line);
        if (!line.startsWith("250")) throw new Exception("HELO 실패했습니다:"+line);

        System.out.println("MAIL FROM 명령을 전송합니다.");
        pw.println("MAIL FROM: "+sender);
        line=br.readLine();
        System.out.println("응답:"+line);
        if (!line.startsWith("250")) throw new Exception("MAIL FROM 에서 실패했습니다:"+line);

        System.out.println("RCPT 명령을 전송합니다.");
        pw.println("RCPT TO: "+recipient);
        line=br.readLine();
        System.out.println("응답:"+line);
        if (!line.startsWith("250")) throw new Exception("RCPT TO 에서 실패했습니다:"+line);

        System.out.println("DATA 명령을 전송합니다.");
        pw.println("DATA");
        line=br.readLine();
        System.out.println("응답:"+line);
        if (!line.startsWith("354")) throw new Exception("DATA 에서 실패했습니다:"+line);

        System.out.println("본문을 전송합니다.");
        pw.println(content);
        pw.println(".");
        line=br.readLine();
        System.out.println("응답:"+line);
        if (!line.startsWith("250")) throw new Exception("내용전송에서 실패했습니다:"+line);

        System.out.println("접속 종료합니다.");
        pw.println("quit");

        br.close();
        pw.close();
        socket.close();

    }

    private boolean submitCommand(String command) throws SMTPException {
        try {
            output.print(command + "\r\n");
            String serverReply = input.readLine();
            if (serverReply.charAt(0) == '4' || serverReply.charAt(0) == '5')//전송실패등..
                return true;
            else return false;
        } catch (Exception e) {
            throw new SMTPException(e.getMessage());
        }
    }

    public static void main(String args[]) {
        DNSLookup dnsLookup = new DNSLookup();
        String mx = dnsLookup.lookup("gmail.com");
        try {
            SocketMailSender.sendMail(
                    mx,
                    "<pdj13579@nate.com>",
                    "<pdj13579@gmail.com>",
                    "내용"
            );
            System.out.println("==========================");
            System.out.println("메일이 전송되었습니다.");
        } catch(Exception e) {
            System.out.println("==========================");
            System.out.println("메일이 발송되지 않았습니다.");
            System.out.println(e.toString());
        }
    }



}
