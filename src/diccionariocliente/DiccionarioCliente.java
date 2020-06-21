/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diccionariocliente;
import java.io.*;
import java.net.*;
import java.util.Scanner; 
import java.util.ArrayList;
/**
 *
 * @author anton
 */
public class DiccionarioCliente {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
         ArrayList<String> palabraConEspacios = new ArrayList<String>();
//        if (args.length != 2) {
//            System.err.println(
//                "Usage: java EchoClient <host name> <port number>");
//            System.exit(1);
//        }
//127.0.0.1
        String hostName = "127.0.0.1";
        int portNumber = 3478;
        String nombre = "Carrasco";
     
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            String inicioSesion = "PROTOCOL_PSP_JUNIO#HELLO_ITS_ME#"+nombre;
            out.println(inicioSesion);

            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    //System.out.println();
                    if (fromUser.contains("bye") || fromUser.contains("adios")){
                        out.println("PROTOCOL_PSP_JUNIO#GOODBYE_MY_LOVE#BY#" + nombre);
                        System.exit(-1);   
                        }
                    }
                if (fromUser.contains(" ")){
                    int contadorEspacios = 0;
                    
                    for(int i = 0; i < fromUser.length(); i++){
                        if(fromUser.charAt(i) == ' '){
                        contadorEspacios++;
                        }
                    }    
                    System.out.println(contadorEspacios);

                    String[] espacios = fromUser.split(" ");
        
                    for(int i = 0; i <= contadorEspacios; i++) {   
                        palabraConEspacios.add(espacios[i]);
                    }

                    for(int i = 0; i <= contadorEspacios; i++) {   
                        System.out.println(palabraConEspacios.get(i));
                        out.println("PROTOCOL_PSP_JUNIO#ASK_FOR#" + palabraConEspacios.get(i) + "#BY#" + nombre);
                    }

                    for(int i = 0; i < contadorEspacios; i++){
                        fromServer = in.readLine();
                        System.out.println(fromServer);
                    }
                } else out.println("PROTOCOL_PSP_JUNIO#ASK_FOR#" + fromUser + "#BY#" + nombre);
                    System.out.println("Server: " + fromServer);
                palabraConEspacios.clear();
            }   
        }catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}