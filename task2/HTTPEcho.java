import java.net.*;
import java.io.*;

public class HTTPEcho {

    public static void main(String[] args) throws IOException {
    String temp;
    ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));

    try {
    while(true) {
    Socket connectionSocket = welcomeSocket.accept();
    connectionSocket.setSoTimeout(2000);
    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream());
    
    outToClient.print("HTTP/1.1 200 OK\r\n");
    outToClient.print("\r\n");
    
        while((temp = inFromClient.readLine()) != null && temp.length() != 0) {
            outToClient.print(temp + "\r\n");
            }
            outToClient.close(); 
            inFromClient.close();
            connectionSocket.close(); 
        }
    
    }
    catch(IOException x){
        System.out.println("Pepehands");
        }
    }
}


