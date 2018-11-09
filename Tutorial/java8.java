
//=======================================================================
//JAVA 8
//=======================================================================
//=======================================================================
//ENUM
//=======================================================================
package nbtelecomtv.com.br.nbtelecom_allversion.adapter.enums;

import nbtelecomtv.com.br.nbtelecom_allversion.R;

public enum Chanels {

   NB_TELECOM("NB Telecom"),
   CNT("Rede CNT"),
   REDE_TV("Rede TV"),
   NBR("NBR"),
   TV_SAUDE("TV Saúde"),
   SBT("SBT"),
   TV_BRASIL("TV Brasil"),
   TV_ESCOLA("TV Escola"),
   RECORD_NEWS("Record News"),
   GLOBO("Globo"),
   RECORD("Record"),
   BAND("BAND"),
   CINE_BRASIL("Cine Brasil"),
   BLENDER("Blender"),
   SEMPRE_UM_PAPO("Sempre um Papo"),
   VASCO_TV("vasco TV"),

   NUM_NB_TELECOM("50"),
   NUM_CNT("01"),
   NUM_REDE_TV("02"),
   NUM_NBR("03"),
   NUM_TV_SAUDE("04"),
   NUM_SBT("05"),
   NUM_TV_BRASIL("06"),
   NUM_TV_ESCOLA("08"),
   NUM_RECORD_NEWS("09"),
   NUM_GLOBO("10"),
   NUM_RECORD("11"),
   NUM_BAND("12"),
   NUM_CINE_BRASIL("55"),
   NUM_BLENDER("20"),
   NUM_SEMPRE_UM_PAPO("22"),
   NUM_VASCO_TV("23");

   private String chanel;

   Chanels(String chanel) {
      this.chanel = chanel;
   }

   public String getChanel() {
      return chanel;
   }

   public void setChanel(String chanel) {
      this.chanel = chanel;
   }
}

//USAR 
private Chanels chanels;
private String chanel;
chanels = Chanels.NUM_NB_TELECOM;
chanel = chanels.getChanel();

//=======================================================================
//FIM ENUM
//=======================================================================

//=======================================================================
//CONSUMO MEMORIA
//=======================================================================
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumojava;

import com.sun.management.OperatingSystemMXBean;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

/**
 *
 * @author Ednei
 */
public class Consumo {

    private static long memoryDisc = new File("/").getTotalSpace();
    private static long memoryDiscUsed = new File("/").getFreeSpace();
    private static String userName = System.getProperty("user.name");
    private static long memoryRam = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    private static long memoryRamFree = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize();
    private static long memoryRamUsed = (memoryRam - memoryRamFree);

    public static void calc() {

        System.out.println("User Name: " + userName);
        System.out.println("Size C: " + convertBystes(memoryDisc));
        System.out.println("Size C[Used]: " + convertBystes(memoryDiscUsed));
        System.out.println("RAM: " + convertBystes(memoryRam));
        System.out.println("RAM Free[Disponivel]: " + convertBystes(memoryRamFree));
        System.out.println("RAM used[Usada]: " + convertBystes(memoryRamUsed));
    }

    public static String getNameUser(){
        return userName;
    }
    
    public static String sizeMemoryDisk() {
        return convertBystes(memoryDisc);
    }
    
    public static String sizeMemoryDiskUsed() {
        return convertBystes(memoryDiscUsed);
    }

    public static String sizeMemoryRam() {
        return convertBystes(memoryRam);
    }

    public static String sizeMemoryRamFree() {
        return convertBystes(memoryRamFree);
    }

    public static String sizeMemoryRamUsed() {
        return convertBystes(memoryRamUsed);
    }

    public static String convertBystes(long size) {
        String hrSize = null;
        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");
        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }

}

//=======================================================================
//FIM CONSUMO MEMORIA
//=======================================================================

//=======================================================================
//IO
//=======================================================================
package io;

import java.io.*;

public class InputStreams {

    /*
    * Para ler um byte de um arquivo, vamos usar o leitor de arquivo, o FileInputStream.
    * Para um FileInputStream conseguir ler um byte, ele precisa saber de onde ele deverá ler.
    * Essa informação é tão importante que quem escreveu essa
    * classe obriga você a passar o nome do arquivo pelo construtor: sem isso o objeto não pode ser construído.
    * */
    public static void ReadFile(String path) throws IOException {

        InputStream in = new FileInputStream(path);
        int b = in.read();
    }

    /*
    * Para recuperar um caractere, precisamos traduzir os bytes com o encoding dado para o respectivo código unicode,
    * isso pode usar um ou mais bytes.
    * Escrever esse decodificador é muito complicado, quem faz isso por você é a classe InputStreamReader.
    * */
    public static int  readCharacter(String path) throws IOException {

        InputStream in = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        int c = inputStreamReader.read();
        return c;

    }

    /*
    * Apesar da classe abstrata Reader já ajudar no trabalho de manipulação de caracteres, ainda seria difícil pegar uma String.
    * A classe BufferedReader é um Reader que recebe outro
    * Reader pelo construtor e concatena os diversos chars para formar uma String através do método readLine:
    * */

    public static String bufferReades(String path) throws IOException {

        InputStream in = new FileInputStream(path);
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String s = bufferedReader.readLine();

        while (s != null) {
            System.out.println(s);
            s = bufferedReader.readLine();
        }

        bufferedReader.close();
        return s;
    }

    /*
    *  Ler arquivo e bytes
    *
    * */
    public int readFileBytes(String path) throws IOException {

        int total_byte = 0;

        FileInputStream fileInputStream = new FileInputStream(path);

        boolean final_ar = false;
        int  byte_entrada = 0;

        File file = new File(path);

        //ler arquivo
        while(!final_ar){

            if(byte_entrada== -1){
                final_ar = true;//fim while
            };

            total_byte++;
        }

        System.out.println("Total bytes "+total_byte);
        fileInputStream.close();
        return byte_entrada;
    }

    public static byte[] read(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;

        try {
            byte[] buffer = new byte[1024*2];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        }finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }
}
//=======================================================================
//FIM IO
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
