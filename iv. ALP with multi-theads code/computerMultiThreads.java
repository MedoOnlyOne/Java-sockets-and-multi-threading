import java.io.*;
import java.net.*;

class ComputerThreads implements Runnable{
    private Socket serverSocket;
    private Socket driverSocket;
    private DataInputStream serverSocketInput;
    private DataOutputStream serverSocketOutput;
    private DataInputStream driverSocketInput;
    private DataOutputStream driverSocketOutput;

    public ComputerThreads(Socket s, Socket d){
        this.serverSocket = s;
        this.driverSocket = d;
    }

    @Override
    public void run(){
        try{

            serverSocketInput = new DataInputStream(serverSocket.getInputStream());
            serverSocketOutput = new DataOutputStream(serverSocket.getOutputStream());
            driverSocketInput = new DataInputStream(driverSocket.getInputStream());
            driverSocketOutput = new DataOutputStream(driverSocket.getOutputStream());
            
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
        catch (IOException e){
            System.out.println("thread error");
            System.err.println(e);
        }
    }
}

public class computerMultiThreads {
    public static void main(String[] args){
        try
        {
            // Run the computer as a server to the sensor.
            ServerSocket computer = new ServerSocket(3000);
            System.out.println("Computer is running......");
            
            // Connect the computer to the server.
            Socket serverSocket = new Socket("127.0.0.1", 5000);
            
            while (true)
            {
                // Connect with the sensor.
                Socket driverSocket = computer.accept();

                // Create a thread for each connection.
                ComputerThreads computerThread = new ComputerThreads(serverSocket, driverSocket);
                Thread thread = new Thread(computerThread);
                thread.start();
                System.out.println("Driver is connected by "+ thread.getName() + "......");
            }
        } 
        catch (IOException e)
        {
            System.err.println(e);
        }
    }    
}
