import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.plaf.DimensionUIResource;

public class Client extends JFrame{

        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JButton bouton = new JButton();
        JTextField field = new JTextField("");
        JTextArea chatArea = new JTextArea();
        Socket socket;
        ObjectOutputStream dataOut;
        ObjectInputStream dataIn;
        BufferedReader pen ;
        String message="";
        String serverIP;
        int port = 6789;

       public Client(String s){

        initComponents();
        
        this.setTitle("Client");
        this.setVisible(true);
        label1.setVisible(true);
        serverIP = s;

       }

       public void initComponents(){

        field.setPreferredSize(new DimensionUIResource(200, 30));;
        field.setBounds(100, 100, 270, 30);
        this.setTitle("Client");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        bouton.setBounds(310, 50, 80, 30);
        chatArea.setColumns(30);
        chatArea.setRows(10);
        pan1.add(field);
        pan1.add(bouton);
        label1.setForeground(new java.awt.Color(255, 7, 207));
        label1.setText("Write your text here");
        label1.setBounds(30, 30, 150, 20);
        pan1.add(label1);
        pan1.add(chatArea);


        bouton.setText("send");
        bouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(field.getText());
	            field.setText("");
            }
        });
        
       
        field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(field.getText());
	            field.setText("");
            }
        });


        this.setContentPane(pan1);
        this.setVisible(true);
        
    }


    public void startRunning(){
       try {

        label2.setText("Attemping connection...");
          try {
            socket = new Socket(InetAddress.getByName(serverIP),port);
          } catch (Exception e) {
            // TODO: handle exception
          }
          label2.setText("Connect to:" +socket.getInetAddress().getHostName());
          dataOut = new ObjectOutputStream(socket.getOutputStream());
          dataOut.flush();
          dataIn = new ObjectInputStream(socket.getInputStream());

          whileChatting();
       } catch (Exception e) {
        // TODO: handle exception
       }    
    }

    public void whileChatting() throws IOException{
        field.setEditable(true);
         
        do{

            try{

                message = (String) dataIn.readObject();
                chatArea.append("\n"+message);
            }
            catch(Exception e){
               
            }
        } while(!message.equals("Client - END"));
    
    }


    
    public void sendMessage(String message)
    {
        
        try
        {
            dataOut.writeObject("client - " + message);
            dataOut.flush();
            chatArea.append("\nclient - "+message);

        }
        catch(Exception e)
        {
            chatArea.append("\n Unable to Send Message");
        }
   
    }    
}


    

