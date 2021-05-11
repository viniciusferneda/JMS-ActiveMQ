package br.com.caelum.jms.activemq.fila;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;
import java.util.Scanner;

public class TesteConsumidorFila {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        //usando a interface Queue
        /*
        QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
        QueueConnection conexao = factory.createQueueConnection();
        conexao.start();
        QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue fila = (Queue) context.lookup("financeiro");
        QueueReceiver receiver = sessao.createReceiver(fila);
        Message message = receiver.receive();
        */

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection conexao = factory.createConnection();
        conexao.start();

        Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //Session sessao = conexao.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //Session sessao = connection.createSession(true, Session.SESSION_TRANSACTED);

        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = sessao.createConsumer(fila);

        //Message message = consumer.receive();
        //System.out.println("Recebendo msg: " + message);

        //checa se a mensagem foi entregue
        QueueBrowser browser = sessao.createBrowser((Queue) fila);
        Enumeration msgs = browser.getEnumeration();
        while (msgs.hasMoreElements()) {
            TextMessage msg = (TextMessage) msgs.nextElement();
            System.out.println("Message: " + msg.getText());
        }

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    //message.acknowledge(); // fazendo programaticamente
                    System.out.println(textMessage.getText());
                    //sessao.commit();
                    //sessao.rollback();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();
        context.close();
    }
}
