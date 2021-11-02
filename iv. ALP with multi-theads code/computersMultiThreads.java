import java.io.*;
import java.net.*;

class ComputerThreads implements Runnable{
    private Socket ServerSocket;
    private Socket sensorSocket;
    private DataInputStream ServerSocketInput;
    private DataOutputStream ServerSocketOutput;
    private DataInputStream sensorSocketInput;
    private DataOutputStream sensorSocketOutput;

    public ComputerThreads(Socket ser, Socket sen){
        this.ServerSocket = ser;
        this.sensorSocket = sen;
    }

    @Override
    public void run(){
        try{

            ServerSocketInput = new DataInputStream(ServerSocket.getInputStream());
            ServerSocketOutput = new DataOutputStream(ServerSocket.getOutputStream());
            sensorSocketInput = new DataInputStream(sensorSocket.getInputStream());
            sensorSocketOutput = new DataOutputStream(sensorSocket.getOutputStream());
            
            while (true)
                {
                    // Get the reading from the sensor.
                    String sensorData = sensorSocketInput.readUTF();
                    System.out.println(sensorData + " has been received from the sensor." );
                    // Send the readings to the server
                    ServerSocketOutput.writeUTF(sensorData);
                    ServerSocketOutput.flush();
                    System.out.println(sensorData + " has been sent to the server." );
                    // if the reading is exit, close the connection.
                    if(sensorData.equals("exit")){
                        System.out.println("Sensor is disconnected.");
                        System.out.println("Computer is down.");
                        break;
                    } else {
                        // Get the recommendations from the srever.
                        String recommendations = ServerSocketInput.readUTF();
                        System.out.println(recommendations + " has been received from the server.");
                        
                        /*
                            Send the recommendations to the drivers.
                         */
                        System.out.println("Recommendations has been sent to the drivers.");
                    }
                    
                    
                }
                // Close the connection with the sensor.
                sensorSocketInput.close();
                sensorSocketOutput.close();
                sensorSocket.close();
                // Close the connection with the server.
                ServerSocketInput.close();
                ServerSocketOutput.close();
                ServerSocket.close();
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
}

public class computersMultiThreads {
    public static void main(String[] args){
        try
        {
            // Run the computer as a server to the sensor.
            ServerSocket computer = new ServerSocket(3000);
            System.out.println("Computer is running......");
            
            // Connect the computer to the server.
            Socket ServerSocket = new Socket("127.0.0.1", 5000);
            
            while (true)
            {
                // Connect with the sensor.
                Socket sensorSocket = computer.accept();

                // Create a thread for each connection.
                ComputerThreads computerThread = new ComputerThreads(ServerSocket, sensorSocket);
                Thread thread = new Thread(computerThread);
                thread.start();
                System.out.println("Sensor is connected by "+ thread.getName() + "......");
            }
        } 
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }    
}
