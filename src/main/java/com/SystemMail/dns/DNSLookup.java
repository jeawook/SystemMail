package com.SystemMail.dns;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

public class DNSLookup
{
    private InitialDirContext iDirC;
    private final static String record = "MX";
    public DNSLookup ()
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.dns.DnsContextFactory");
        try {
            iDirC = new InitialDirContext(env);
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String lookup (String host)
    {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(host);
            // show the Internet Address as name/address
            System.out.println(inetAddress.getHostName() + " " + inetAddress.getHostAddress());

            // get the DNS records for inetAddress

            Attributes attributes = iDirC.getAttributes("dns:\\"+inetAddress.getHostName(), new String[] {record});
            // get an enumeration of the attributes and print them out
            //NamingEnumeration<?> attributeEnumeration = attributes.getAll();
/*          while (attributeEnumeration.hasMore())
            {
                System.out.println("" + attributeEnumeration.next());
            }
            attributeEnumeration.close();*/
            Attribute mxRecord = attributes.get(record);
            for (int i=0; i<mxRecord.size();i++) {
                System.out.println(mxRecord.get(i));
            }
            return mxRecord.get(0).toString().split(" ")[1];

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        DNSLookup looker = new DNSLookup();
        looker.lookup("gmail.com");
    }
}