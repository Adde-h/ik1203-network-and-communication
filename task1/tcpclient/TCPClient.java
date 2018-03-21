package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        if (ToServer == null)
            return(askServer(hostname, port));
        String temp;
        int counter = 0;
        StringBuilder sb = new StringBuilder();

        Socket clientSocket = new Socket(hostname, port);
        clientSocket.setSoTimeout(1000);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeBytes(ToServer + '\n');

        try {
            while ((temp = inFromServer.readLine()) != null && !temp.equals("\n") && counter != 10000) {
                sb.append(temp + '\n');
                counter++; }

            clientSocket.close();
            return sb.toString(); }

        catch(IOException x){
            clientSocket.close();
            return sb.toString();
        }
    }

    private static String askServer(String hostname, int port) throws IOException {
        String temp;
        StringBuilder sb = new StringBuilder();
        int counter = 0;


        Socket clientSocket = new Socket(hostname, port);
        clientSocket.setSoTimeout(1000);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while((temp = inFromServer.readLine()) != null && !temp.equals("\n") && counter!= 10000){
            sb.append(temp + '\n');
            counter ++;
        }
        clientSocket.close();
        return sb.toString();
    }
}