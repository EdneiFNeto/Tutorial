
//=======================================================================
//JAVA 8
//=======================================================================
//NOVAS FIORMAS DE LISTA
//=======================================================================
package model;

public class Usuario {

	private String nome;
	private int pontos;
	private boolean moderador;


	public Usuario(String nome, int pontos) {
		this.nome = nome;
		this.pontos = pontos;
	}
    //GETTTES E SETTINGS

	@Override
	public String toString() {
		return String.format("%s, %s, %s", getNome(), getPontos(), isModerador());
	}
}



//=======================================================================
//FORMAS DE EXECUTAR
//=======================================================================
package utilTest;

import model.Usuario;
import sun.plugin.javascript.navig.Array;
import util.Mostrador;
import java.util.Arrays;
import java.util.List;
public class MainCapitulo2UtilTest {

	public static void main(String ...args){

		Usuario user1 = new Usuario("X1", 150);
		Usuario user2 = new Usuario("X2", 120);

		System.out.println("================================");
		System.out.println("FORMA DE LISTAR ");
		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);
		for(Usuario u: usuarios)
			System.out.println(u.toString());

		System.out.println("================================");
		System.out.println("NOVA FORMA DE LISTAR ");
		System.out.println("================================");

		Consumer<Usuario> mostrador = new Consumer<Usuario>() {
			@Override
			public void accept(Usuario usuario) {
				System.out.println(user1.getNome());
			}
		};
	}
}

//=======================================================================
//LAMBADAS
//=======================================================================
Usuario user1 = new Usuario("X1", 150);
Usuario user2 = new Usuario("X2", 120);
Usuario user3 = new Usuario("X3", 130);
Usuario user4 = new Usuario("X4", 190);

List<Usuario> usuarios = Arrays.asList(user1, user2, user3);
usuarios.forEach(u->System.out.println(u.getNome()));

public static  void exemplo1(){

	Runnable r = ()->{
		MessageUtil.msg("Runnable lambada");
	};
	new Thread(r).start();
}


public static void exemplo2(){

	new Thread (()->{
		MessageUtil.msg("Thread lambada");
	}).start();
}

button.addActionListener( (event) -> {
	System.out.println("evento do click acionado");
});

//=======================================================================
//INTERFACES FUNCIONAL
//=======================================================================

package interfaces;
@FunctionalInterface
public interface Validador<T> {
	boolean valida(T t);
}


//=======================================================================
Validador<String> validaCPF = valor->{
	return valor.matches("[0-9]{5}-[0-9]{3}");
};


//=======================================================================
//ORDENADO
//=======================================================================

Comparator<Usuario> comparator = new Comparator<Usuario>() {
	@Override
	public int compare(Usuario u1, Usuario u2) {
		return u1.getNome().compareTo(u2.getNome());
	}
};

Collections.sort(usuarios, comparator);

//=======================================================================
//USANDO LAMBADA
//=======================================================================
Comparator<Usuario> comparator1 = (u1, u2)-> u1.getNome().compareTo(u2.getNome());
Collections.sort(usuarios, comparator1);


Usuario user1 = new Usuario("X1", 150);
Usuario user2 = new Usuario("B2", 120);
Usuario user3 = new Usuario("W3", 130);
Usuario user4 = new Usuario("Z4", 190);

List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4);
//ordena
usuarios.sort((u1, u2)-> u1.getNome().compareTo(u2.getNome()));
//imprime
usuarios.forEach(u->System.out.println(u.getNome()));



//=======================================================================
//SOKET
//=======================================================================

package sokets;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;

public class ReadServeFile extends JFrame {

    private JTextField enterField;
    private JEditorPane contentArea;

    public ReadServeFile(){
        super("Simpes Browser");

        enterField = new JTextField("Enter file url");
        enterField.addActionListener((event)->{
            getThePage(event.getActionCommand());
        });

        add(enterField, BorderLayout.NORTH);

        contentArea = new JEditorPane();
        contentArea.setEditable(true);
        contentArea.addHyperlinkListener((event)->{
            if(event.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
                getThePage(event.getURL().toString());
        });

        add(new JScrollPane(contentArea));
        setSize(400, 300);
        setVisible(true);
    }

    private void getThePage(String location) {

        try{
            contentArea.setPage(location);
            enterField.setText(location);
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error", "BAD URL", JOptionPane.ERROR_MESSAGE);
        }
    }
}

//=======================================================================
public class Main {

    public static void main(String[] args){
        ReadServeFile app = new ReadServeFile();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


//=======================================================================
//SOCKET
//=======================================================================
package sokets;

import util.MessagUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {

    JTextField enterFiled;
    JTextArea displayArea;
    ObjectOutputStream output;
    ObjectInputStream input;
    ServerSocket server;
    Socket connection;
    int counter = 1;


    public Server() {
        super("Server");
        enterFiled = new JTextField();
        enterFiled.setEditable(true);
        enterFiled.addActionListener((event) -> {
            sendData(event.getActionCommand());
            enterFiled.setText("");
        });

        add(enterFiled, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea));
        setSize(300, 150);
        setVisible(true);
    }

    private void sendData(String message) {
        try{
            output.writeObject("SERVER>>"+message);
            output.flush();//esvazia
            MessagUtil.msg("SERVER>>"+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void runServer() {

        try {

            waitForConnection();
            getStreams();
            precessConnection();
        } catch (Exception e) {
            MessagUtil.msg("\nServer terminate");
        }
        finally {
            closeConnection();
            ++counter;
        }
    }

    private void closeConnection() {
        MessagUtil.msg("Fim connection");
        setTexFiledEditable(false);

        try{
            output.close();
            input.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void precessConnection() {

        String message = "Connection sucessfull";
        sendData(message);

        setTexFiledEditable(true);

        do{
            try{
                message = (String) input.readObject();//le uma novammenssagem
                MessagUtil.msg("\n"+message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }while(!message.equals("Cliente>>> TERMINATE"));

    }

    private void setTexFiledEditable(final boolean b) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                enterFiled.setEditable(b);
            }
        });
    }

    private void getStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();//exvazia buffer de saida para enviar as informações

        input = new ObjectInputStream(connection.getInputStream());
        MessagUtil.msg("\nGo I/O streams");
    }

    private void waitForConnection() throws IOException {
        MessagUtil.msg("Waiting for connection");
        connection = server.accept();
        MessagUtil.msg("Connection "+ counter+" receive from "+ connection.getInetAddress().getHostName());
    }
}

public class Main {

    public static void main(String[] args){
        Server app = new Server();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.runServer();
    }
}
