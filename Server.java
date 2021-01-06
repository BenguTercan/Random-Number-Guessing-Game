package guessingGame;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server extends Thread
{
	private Socket th_soket = null;	
	
	public Server(Socket socket)
	{
		th_soket = socket;
	}

	public static void main(String[] args) throws Exception
	{
		ServerSocket soket = new ServerSocket(1729);	
		System.out.println("Server is connected successfully!");
		boolean connection = true;
		
		while (connection == true)
		{
			new Server(soket.accept()).start();
		}	
		
		soket.close();	
	}
		
	public void run()
	{	
		String input;			// user input
		int guessing;			// user guessing
		int count = 1;			// 3 chance for guessing
		
		System.err.println("Client is connected successfully !");			
		int number =(int) ((Math.random() * 9) +1); // generate random number between 1 and 10
		
		try
		{
			Scanner soketin = new Scanner(th_soket.getInputStream());			
			PrintWriter soketout = new PrintWriter(th_soket.getOutputStream(), true);
			
			while (count <= 3)
			{
				input = soketin.nextLine();
				guessing = Integer.parseInt(input);	// casting from string to integer
				
				if (guessing == number)
				{
					soketout.println("Congratulations! Your guess " + guessing + " is correct!" );
					break;
				}
				
				else if (guessing < number)
				{
					if( count == 3)
					{
						soketout.println("Wrong! The correct number is " + number + " !");
						break;
					}
					
					else
						soketout.println("Not "+ guessing + ". Go Higher !");
					count ++;	
				}
				
				else if (guessing > number)	
				{
					if( count == 3)
					{
						soketout.println("Wrong! The correct number is " + number + " !");
						break;
					}
					
					else
						soketout.println("Not "+ guessing + ". Go Lower !");
					count ++;	
				}				
			}

			soketout.close();			
			soketin.close();
			th_soket.close();
		}
		
		catch (Exception e)
		{
			System.err.println("Client is disconnected from the server!");
		}
	}
}
