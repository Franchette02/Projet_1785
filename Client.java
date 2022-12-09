import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.plaf.DimensionUIResource;

public class Client extends JFrame{

        JPanel panelClient = new JPanel();
        JPanel panelClient2 = new JPanel();
        JLabel labelClient = new JLabel();
        JLabel label2 = new JLabel();
        JButton bouton = new JButton();
        JTextField textfield = new JTextField("");
        JTextArea textArea = new JTextArea();
        Socket socket;
        ObjectOutputStream dataOutput;
        ObjectInputStream dataInput;
        BufferedReader pen ;
        String message="";
        String serverIP;

       public Client(String s){

        swing_server();
        
        this.setTitle("Client");
        this.setVisible(true);
        labelClient.setVisible(true);
        serverIP = s;

       }

       public void swing_server(){

        textfield.setPreferredSize(new DimensionUIResource(200, 30));;
        textfield.setBounds(50, 100, 270, 30);
        this.setTitle("Client");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        bouton.setBounds(400, 50, 80, 30);
        textArea .setColumns(30);
        textArea .setRows(15);
        textArea.setBackground(Color.CYAN);
        panelClient .add(labelClient);
        panelClient .add(textfield);
        panelClient .add(bouton);
        labelClient.setForeground(new java.awt.Color(255, 7, 207));
        labelClient.setText("Write your text here");
        panelClient .add(textArea);
        
        


        bouton.setText("Envoyer");
        bouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Envoyer_Message(textfield.getText());
	            textfield.setText("");
            }
        });
        
       
        textfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Envoyer_Message(textfield.getText());
	            textfield.setText("");
            }
        });


        this.setContentPane(panelClient);
        this.setVisible(true);
        
    }
    //envoyer message
    public void Envoyer_Message(String message)
    {
        
        try
        {
            dataOutput.writeObject("Client : " + message);
            dataOutput.flush();
            textArea.append("\nMe: "+message);

        }
        catch(Exception e)
        {
            textArea.append("\n Unable to Send Message");
        }
   
    }  

    public void ReadChat() throws IOException{
        textfield.setEditable(true);
         
        do{

            try{

                message = (String) dataInput.readObject();
                textArea .append("\n"+message);
            }
            catch(Exception e){
               
            }
        } while(!message.equals("Client - END"));
    
    }


    public void Run(){
       try {

        label2.setText("Attemping connection...");
          try {
            socket = new Socket(InetAddress.getByName(serverIP),6789);
          } catch (Exception e) {
            // TODO: handle exception
          }
          label2.setText("Se connecter a:" +socket.getInetAddress().getHostName());
          dataOutput = new ObjectOutputStream(socket.getOutputStream());
          dataOutput.flush();
          dataInput = new ObjectInputStream(socket.getInputStream());

          ReadChat();
       } catch (Exception e) {
        // TODO: handle exception
       }    
    }

    

    
      
}


    

