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
         String[] palabraConEspacios;

//localhost: 127.0.0.1
//Mi ip: 85.55.245.139
//Cuvi: 154.62.44.50
//Olivencia: 154.47.140.75
//Raul: 176.57.103.161

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
         
                    int contadorEspacios = 0;

                    palabraConEspacios = fromUser.split(" ");

                    for(int i = 0; i < fromUser.length(); i++){
                        if(fromUser.charAt(i) == ' '){
                        contadorEspacios++;
                        }
                    }    
                    System.out.println(contadorEspacios);

                    for(int i = 0; i < palabraConEspacios.length; i++) {   
                        System.out.println(palabraConEspacios[i]);
                        out.println("PROTOCOL_PSP_JUNIO#ASK_FOR#" + palabraConEspacios[i] + "#BY#" + nombre);
                    }
                    for(int i  = 0; i < contadorEspacios; ++i){
                        fromServer = in.readLine();
                        System.out.println("Server" +fromServer);
                    }
                    
            }   
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
    }
}
}
