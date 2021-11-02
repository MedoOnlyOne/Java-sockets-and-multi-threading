import java.io.*;
import java.net.*;
import java.util.*;

public class sensorsMobileAgent {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        try
        {
            // Connect to the computer.
            Socket computerSocket = new Socket("127.0.0.1", 3000);
            
            DataInputStream computerSocketInput = new DataInputStream(computerSocket.getInputStream());
            DataOutputStream computerSocketOutput = new DataOutputStream(computerSocket.getOutputStream());

            System.out.println("Sensor is running.....");
            
            while (true)
            {
                // Get the reading.
                System.out.print("Get sensor's reading: ");
                String data = sc.next();
                // Send the reading to the computer.
                computerSocketOutput.writeUTF(data);
                computerSocketOutput.flush();
                System.out.println(data + " has been sent to the computer.");
                // if the reading is exit, close the connection.
                if(data.equals("exit")){
                    System.out.println("We are finished with the readings.");
                    break;
                }
            }
            // Close the connection with the computer.
            computerSocketInput.close();
            computerSocketOutput.close();
            computerSocket.close();
        } 
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        sc.close();
    }
}
