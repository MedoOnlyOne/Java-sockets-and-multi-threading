import java.io.*;
import java.net.*;

public class serversMobileAgent {
    public static void main(String[] args){
        try
        {
            // Run the server.
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server is running...");
            while (true)
            {
                // Connect with the computer.
                Socket computerSocket = server.accept();
                System.out.println("Computer is connected......");
                
                DataInputStream computerSocketInput = new DataInputStream(computerSocket.getInputStream());
                DataOutputStream computerSocketOutput = new DataOutputStream(computerSocket.getOutputStream());

                while (true)
                {
                    // Get the reading from the computer
                    String data = computerSocketInput.readUTF();
                    System.out.println(data + " has been received from the computer.");
                    // if the reading is exit, close the connection.
                    if (data.equals("exit"))
                    {
                        System.out.println("Computer is disconnected.");
                        System.out.println("Server is down.");
                        break;
                    } else {
                        /*
                            Process the reading.
                            Make the recommendations.
                         */
                        
                        // Send the recommendations to the computer.
                        computerSocketOutput.writeUTF("Recommendations...");
                        computerSocketOutput.flush();
                        System.out.println("Recommendations has been sent to the computer.");
                    }
                }
                // Close the connection with the computer.
                computerSocketInput.close();
                computerSocketOutput.close();
                computerSocket.close();
                break;
            }
            // close the server.
            server.close();
        } 
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
