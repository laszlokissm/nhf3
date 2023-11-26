import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main extends JFrame {
    private String born="3";
    private String survive="2;3";
    private int size=50;
    Grid g;

    public Main(){
        setTitle("Sejtautomata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JPanel menu = new JPanel();
        JButton startButton = new JButton("Inditas");
        JButton exitButton = new JButton("Kilepes");
        JButton loadButton = new JButton("Betoltes");
        JTextField bornTextField = new JTextField(5);
        JTextField surviveTextField = new JTextField(5);
        JTextField sizeTextField = new JTextField(5);
        JLabel bornLabel = new JLabel("Szuleteshez szukseges");
        JLabel surviveLabel = new JLabel("Tuleleshez szukseges");
        JLabel sizeLabel = new JLabel("Meret");
        JPanel fields = new JPanel();
        JPanel botButtonsPanel = new JPanel(new BorderLayout());
        botButtonsPanel.add(loadButton,BorderLayout.NORTH);
        botButtonsPanel.add(exitButton,BorderLayout.SOUTH);
        fields.add(bornLabel);
        fields.add(bornTextField);
        fields.add(surviveLabel);
        fields.add(surviveTextField);
        fields.add(sizeLabel);
        fields.add(sizeTextField);
        add(startButton,BorderLayout.NORTH);
        add(fields,BorderLayout.CENTER);
        add(botButtonsPanel,BorderLayout.SOUTH);

        bornTextField.setText(born+"");
        surviveTextField.setText(survive+"");
        sizeTextField.setText(size+"");


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        /* 
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                File input=new File("a.ser");
		        ObjectInputStream o=null;
		        try {
			        o = new ObjectInputStream(new FileInputStream(input));
			        g = (Grid)o.readObject();
                    g.run();
                    o.close();
		        }catch(IOException | ClassNotFoundException err ) {throw new RuntimeException(err);}
		
            }
        });
        */
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setBorn(bornTextField.getText());
                setSurvive(surviveTextField.getText());
                if(sizeTextField.getText()!=null) setSize(Integer.parseInt(sizeTextField.getText()));
                
                g = new Grid(born, survive, size);
                

            }
        });




        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setBorn(String born) {
        this.born = born;
    }
    
    public void setSurvive(String survive) {
        this.survive = survive;
    }

    public String getSurvive() {
        return survive;
    }

    public String getBorn() {
        return born;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static void main(String[] args){
        new Main();
    }
}
