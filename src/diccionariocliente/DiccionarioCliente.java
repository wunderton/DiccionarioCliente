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
         

    //localhost: 127.0.0.1
    //Mi ip: 85.55.245.139
    //Cuvi: 154.62.44.50
    //Olivencia: 154.47.140.75
    //Raul: 176.57.103.161

        String hostName = "127.0.0.1";
        int portNumber = 3478;
        String nombre = "Carrasco";
     
        try (
            Socket cliente = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));
        ) {
            BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in));
            String mensajeDesdeServidor = "";
            String mensajeDelUsuario = "";
            String inicioSesion = "PROTOCOL_PSP_JUNIO#HELLO_ITS_ME#"+nombre;
            System.out.println("Cliente: PROTOCOL_PSP_JUNIO#HELLO_ITS_ME#" +nombre);
            out.println(inicioSesion);

            mensajeDesdeServidor = in.readLine();
            System.out.println("Server: " +mensajeDesdeServidor);

            while (mensajeDelUsuario != "bye") {
                mensajeDelUsuario = fromKeyboard.readLine();
                if (mensajeDelUsuario.contains("bye")) {
                    out.println("PROTOCOL_PSP_JUNIO#GOODBYE_MY_LOVE#BY#" + nombre);
                    System.out.println("Cliente: PROTOCOL_PSP_JUNIO#GOODBYE_MY_LOVE_BY" + nombre);
                    mensajeDesdeServidor = in.readLine();
                    System.out.println("Server: " + mensajeDesdeServidor);
                    cliente.close();   
                } else {
                
                    int contadorEspacios = 0;
                    String[] palabras = mensajeDelUsuario.split(" ");

                    for(int i = 0; i < mensajeDelUsuario.length(); i++){
                        if(mensajeDelUsuario.charAt(i) == ' '){
                        contadorEspacios++;
                        }
                    }    
                    //System.out.println(contadorEspacios);

                    for(int i = 0; i < palabras.length; i++) {   
                        System.out.println("DEBUG: " + palabras[i]);
                        out.println("PROTOCOL_PSP_JUNIO#ASK_FOR#" + palabras[i] + "#BY#" + nombre);
                        System.out.println("Cliente: PROTOCOL_PSP_JUNIO#ASK_FOR#"+palabras[i] +"#BY#" + nombre);
                    }
                    for(int i  = 0; i < palabras.length; i++){
                        mensajeDesdeServidor = in.readLine();
                        System.out.println("Server" +mensajeDesdeServidor);
                    }

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
