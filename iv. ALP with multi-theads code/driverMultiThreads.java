import java.io.*;
import java.net.*;
import java.util.*;

public class driverMultiThreads {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        try
        {
            // Connect to the computer.
            Socket computerSocket = new Socket("127.0.0.1", 3000);
            
            DataInputStream computerSocketInput = new DataInputStream(computerSocket.getInputStream());
            DataOutputStream computerSocketOutput = new DataOutputStream(computerSocket.getOutputStream());

            System.out.println("Driver is connected to a computer.....");
            
            while (true)
            {
                boolean exit = false;
                // Get driver's current location.
                System.out.print("Your current location: ");
                String a = sc.next();
                // Get driver's destination.
                System.out.print("Your destination: ");
                String b = sc.next();
                // Construct driver's message
                String data = "Get the best route from " + a + " to " + b;
                // Check if the reading is exit.
                if(data.contains("exit")){
                    exit = true;
                }
                // Send the message to the computer.
                computerSocketOutput.writeUTF(data);
                computerSocketOutput.flush();
                
                // if the reading is exit, close the connection.
                if(exit){
                    System.out.println("Driver is disconnected from the computer.....");
                    break;
                }
                System.out.println(data + " has been sent to the computer.");
                // Receive the recommendation from the computer.
                String recommendations = computerSocketInput.readUTF();
                System.out.println(recommendations + " has been received from the computer");
            }
            // Close the connection with the computer.
            computerSocketInput.close();
            computerSocketOutput.close();
            computerSocket.close();
        } 
        catch (IOException e)
        {
            System.err.println(e);
        }
        sc.close();
    }
}
