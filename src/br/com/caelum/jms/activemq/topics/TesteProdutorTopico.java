package br.com.caelum.jms.activemq.topics;

import br.com.caelum.jms.activemq.modelo.Pedido;
import br.com.caelum.jms.activemq.modelo.PedidoFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorTopico {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection conexao = factory.createConnection();
        conexao.start();

        Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");

        MessageProducer producer =
        sessao.createProducer(topico);
        /*Envio de String
        Message message = sessao.createTextMessage("<pedido><id>333</id></pedido>");
        Message message = sessao.createTextMessage("<pedido><id>222</id><e-book>true</e-book></pedido>");
        message.setBooleanProperty("ebook", true);
        */

        /* Envio de XML
        Pedido pedido = new PedidoFactory().geraPedidoComValores();
        StringWriter writer = new StringWriter();
        JAXB.marshal(pedido, writer);
        String xml = writer.toString();
        Message message = sessao.createTextMessage(xml);
        */

        //Envio de Objeto
        Pedido pedido = new PedidoFactory().geraPedidoComValores();
        Message message = sessao.createObjectMessage(pedido);

        producer.send(message);

        sessao.close();
        conexao.close();
        context.close();
    }
}
