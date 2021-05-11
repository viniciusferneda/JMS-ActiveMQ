package br.com.caelum.jms.activemq.log;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorFilaLog {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection conexao = factory.createConnection();
        conexao.start();

        Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("log");

        MessageProducer producer = sessao.createProducer(fila);

        Message message = sessao.createTextMessage("INFO | ....");
        producer.send(message, DeliveryMode.NON_PERSISTENT, 3, 60000);
        //producer.send(message, DeliveryMode.PERSISTENT, 3, 60000);

        sessao.close();
        conexao.close();
        context.close();
    }
}
