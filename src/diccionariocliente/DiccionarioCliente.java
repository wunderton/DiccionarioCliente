/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diccionariocliente;
import java.io.*;
import java.net.*;
import java.util.Scanner; 
/**
 *
 * @author anton
 */
public class DiccionarioCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
         
//        if (args.length != 2) {
//            System.err.println(
//                "Usage: java EchoClient <host name> <port number>");
//            System.exit(1);
//        }
 
        String hostName = "127.0.0.1";
        int portNumber = 4444;
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
            
            if ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                String inicioSesion = "PROTOCOL_PSP_JUNIO#HELLO_ITS_ME#"+nombre;
                out.println(inicioSesion);
            }
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println();
                    if (fromUser.contains("bye") || fromUser.contains("adios")){
                        out.println("PROTOCOL_PSP_JUNIO#GOODBYE_MY_LOVE#BY#" + nombre);
                        System.exit(-1);   
                        }
                    }
                    out.println("PROTOCOL_PSP_JUNIO#ASK_FOR#" + fromUser + "#BY#" + nombre);
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