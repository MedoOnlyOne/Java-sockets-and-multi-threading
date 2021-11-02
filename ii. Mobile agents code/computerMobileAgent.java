import java.io.*;
import java.net.*;

public class computerMobileAgent {
    public static void main(String[] args){
        try
        {
            // Run the computer as a server to the driver.
            ServerSocket computer = new ServerSocket(3000);
            System.out.println("Computer is running......");
            
            // Connect the computer to the server.
            Socket serverSocket = new Socket("127.0.0.1", 5000);
            
            DataInputStream serverSocketInput = new DataInputStream(serverSocket.getInputStream());
            DataOutputStream serverSocketOutput = new DataOutputStream(serverSocket.getOutputStream());
            
            while (true)
            {
                // Connect with the driver.
                Socket driverSocket = computer.accept();
                System.out.println("Driver is connected......");

                DataInputStream driverSocketInput = new DataInputStream(driverSocket.getInputStream());
                DataOutputStream driverSocketOutput = new DataOutputStream(driverSocket.getOutputStream());

                while (true)
                {
                    boolean exit = false;
                    // Get the message from the driver.
                    String driverData = driverSocketInput.readUTF();
                    // Check if the reading is exit.
                    if(driverData.contains("exit")){
                        exit = true;
                    }
                    // Send the message to the server
                    serverSocketOutput.writeUTF(driverData);
                    serverSocketOutput.flush();
                    // if the message has an exit word, close the dirver's connection.
                    if(exit){
                        System.out.println("Driver is disconnected.");
                        break;
                    } else {
                        System.out.println(driverData + " has been received from the driver." );
                        System.out.println(driverData + " has been sent to the server." );

                        // Get the recommendations from the srever.
                        String recommendations = serverSocketInput.readUTF();
                        System.out.println(recommendations + " has been received from the server.");
                        
                        /*
                            Send the recommendations to the drivers.
                         */
                        driverSocketOutput.writeUTF(recommendations);
                        driverSocketOutput.flush();
                        System.out.println("Recommendations has been sent to the drivers.");
                    }
                }
                // Close the connection with the sensor.
                driverSocketInput.close();
                driverSocketOutput.close();
                driverSocket.close();
            }
        } 
        catch (IOException e)
        {
            System.err.println(e);
        }
    }    
}
