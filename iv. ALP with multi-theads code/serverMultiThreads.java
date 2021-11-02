import java.io.*;
import java.net.*;

class ServerThreads implements Runnable{
    private Socket computerSocket;
    private DataInputStream computerSocketInput; 
    private DataOutputStream computerSocketOutput;

    public ServerThreads(Socket s){
        this.computerSocket = s;
    }

    @Override
    public void run(){
        try{
            
            computerSocketInput = new DataInputStream(computerSocket.getInputStream());
            computerSocketOutput = new DataOutputStream(computerSocket.getOutputStream());

            while (true)
                {
                    boolean exit = false;
                    // Get the message from the computer
                    String data = computerSocketInput.readUTF();
                    // Check if the reading is exit.
                    if(data.contains("exit")){
                        exit = true;
                    }
                    
                    // if the message has an exit word, close the driver's connection.
                    if (exit)
                    {
                        continue;
                    } else {
                        System.out.println(data + " has been received from the computer.");
                        /*
                            Request sensor's reading.
                            Process the reading.
                            Make the recommendations.
                        */
                        System.out.println("Request readings from sensors......");
                        System.out.println("Analysis the readings to make the recommendation.......");
                        // Send the recommendations to the computer.
                        computerSocketOutput.writeUTF("Recommendation...");
                        computerSocketOutput.flush();
                        System.out.println("Recommendation has been sent to the computer.");
                    }
                }
        }
        catch (IOException e){
            System.out.println("thread error");
            System.err.println(e);
        }
    }
}

public class serverMultiThreads {
    public static void main(String[] args){
        try
        {
            // Run the server.
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server is running......");

            while (true)
            {
                // Connect with the computer.
                Socket computerSocket = server.accept();

                // Create a thread with each connection
                ServerThreads serverThread = new ServerThreads(computerSocket);
                Thread thread = new Thread(serverThread);
                thread.start();
                System.out.println("Computer is connected  by "+ thread.getName() + "......");
            }
        } 
        catch (IOException e)
        {
            System.err.println(e);
        }
    }
}
