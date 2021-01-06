package guessingGame;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args) throws IOException 
    {
        try 
        {
            Socket socket = new Socket("127.0.0.1", 1729);	// IP address of the server and port number (host number)            
            Scanner socketIn = new Scanner(socket.getInputStream());	// input from the socket           
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(),true);	// output from the socket
            
            Scanner keyboard = new Scanner(System.in);	// keyboard for user to get input
            System.out.println("Hello ! Guess a number between 1 and 10 : ");
            String serverResponse = "";
                   
            while (true) 
            {
                String userInput = keyboard.nextLine();	// get input from user
                socketOut.println(userInput);	// send input to the socket
                serverResponse = socketIn.nextLine();	// answer of socket
                System.out.println(serverResponse); 
                             
                if (serverResponse.equals("anything"))
                    break;
            }

            socketOut.close();
            keyboard.close();
            socketIn.close();
            socket.close();
        } 
        
        catch (ConnectException e)
        {
            System.err.println("Could not connect to the server. Ensure server is running.");
            System.exit(1);
        } 
        
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for connection.");
            System.exit(1);
        }
    }
}