import java.net.*;
import java.io.*;
import tcpclient.*;

public class HTTPAsk{
    public static void main( String[] args) throws IOException{
    ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));    
    try{
        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream());
            String temp;
            
            System.out.println(temp = inFromClient.readLine());

            if (!urlCheck(temp)){
                outToClient.print("HTTP/1.1 400 Bad Request\r\n");
                outToClient.print("\r\n");
                outToClient.close();
                inFromClient.close();
                connectionSocket.close();
            }

            else{
            String [] url = urlHandler(temp);
            try{
                if (url[0] == null || url[1] == null || url[0].equals("")|| url[1].equals("")) throw new IOException("Bad Gateway");
                System.out.println("Setting up TCP connection");
                String print = TCPClient.askServer(url[0], Integer.parseInt(url[1]), url[2]);
                System.out.print("200 OK");
                outToClient.print("HTTP/1.1 200 OK\r\n");
                outToClient.print("\r\n");  
                outToClient.print(print);
                outToClient.close();
                inFromClient.close();
                connectionSocket.close();}

            catch(IOException x){
                
                if((x.getMessage()).equals("Bad Gateway")) outToClient.print("HTTP/1.1 400 Bad Request\r\n");
                else outToClient.print("HTTP/1.1 404 Not Found\r\n");
                outToClient.print("\r\n");
                outToClient.close();
                inFromClient.close();
                connectionSocket.close();
                }
            }
        }
    }
    catch(IOException x){
    System.out.println("Pepehands");
        }
}

    public static String[] urlHandler(String url){
        String[] temp = url.split("[?&= ]");
        for (String i : temp) System.out.println(i);
        String[] rvalues = new String[3];
        for(int i = 0; i < rvalues.length-1; i++) rvalues[i] = null;
        for(int i = 0; i < temp.length -1; i++)
            {
                switch (temp[i]) {
                    case "hostname": rvalues[0] = temp[i+1];
                    break;
                    case "port": rvalues[1] = temp[i+1];
                    break;
                    case "string": rvalues[2] = temp[i+1];
                    break;
                    default: break;
                }
            }    
        return rvalues;
    }
    public static boolean urlCheck(String s){
        if(s == null) return false;
        if(s.contains("GET") && s.contains("/ask")) return true;
        return false;
    } 
}

