package br.com.caelum.jms.activemq.fila;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class TesteProdutorFila {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection conexao = factory.createConnection();
        conexao.start();

        Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");

        MessageProducer producer = sessao.createProducer(fila);

        for (int i = 0; i < 1000; i++) {
            Message message = sessao.createTextMessage("<pedido><id>" + i + "</id></pedido>");
            producer.send(message);
        }

        sessao.close();
        conexao.close();
        context.close();
    }
}
