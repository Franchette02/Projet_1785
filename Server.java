import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.plaf.DimensionUIResource;


public class Server extends JFrame{

    JPanel panelServer = new JPanel();
    JPanel pan2 = new JPanel();
    JLabel labelServer = new JLabel();
    JLabel label2 = new JLabel();
    JButton bouton = new JButton();
    JTextField textfield = new JTextField("");
    JTextArea textArea = new JTextArea();
    Socket socket;
    ServerSocket serverSocket;
    ObjectOutputStream dataOutput;
    ObjectInputStream dataInput;
    BufferedReader pen ;
    String message="";
    

    public Server()
    {
        swing_server();
        this.setContentPane(panelServer );
        this.setVisible(true);
        labelServer.setVisible(true);
        
    }
    public void swing_server()
    {

        textfield.setPreferredSize(new DimensionUIResource(250, 30));;
        textfield.setBounds(100, 100, 270, 30);
        bouton.setBounds(310, 50, 80, 30);
        textArea.setColumns(30);
        textArea.setRows(15);
        textArea.setBackground(Color.CYAN);
        panelServer.add(labelServer);
        panelServer.add(textfield);
        panelServer.add(bouton);
        labelServer.setForeground(new java.awt.Color(255, 7, 207));
        labelServer.setText("Write your text here");
        labelServer.setBounds(200, 500, 150, 20);
        panelServer.add(textArea);
        this.setTitle("Server");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    
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
    }

    //envoyer message
    public void Envoyer_Message(String message)
    {
    
    try
    {
        System.out.println("cc"+ dataOutput);
        dataOutput.writeObject("Server :" + message);
        System.out.println("mety");
        dataOutput.flush();
        textArea.append("\nMe : "+message);
        
    }
    catch(Exception e)
    {
        System.out.println(e);
        textArea.append("\n Unable to Send Message");
    }

}    

    //run
    public void Run()
    {
    try
    {
        serverSocket=new ServerSocket( 6789, 100);
        
   
            try
            {
                labelServer.setText(" Waiting for a client...");
                socket=serverSocket.accept();
                labelServer.setText(" Now Connected to "+socket.getInetAddress().getHostName());
                dataOutput = new ObjectOutputStream(socket.getOutputStream());
                // dataOut.flush();
                dataInput = new ObjectInputStream(socket.getInputStream());
            while (true) {
                ReadChat();
            }


            }catch(EOFException eofException)
            {
            }
        
    }
    catch(IOException ioException)
    {
            ioException.printStackTrace();
    }
}
public void ReadChat() throws IOException{

    String message="";    
    textfield.setEditable(true);
     
    do{

        try{

            message = (String) dataInput.readObject();
            textArea.append("\n"+message);
        }
        catch(Exception e){

        }
    } while(!message.equals("Client - END"));

}
    
}





