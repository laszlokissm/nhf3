import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main extends JFrame {
    private String born="3";
    private String survive="2;3";
    private int size=50;
    Grid g;

    /**
     * Main osztaly konstruktora. Letrehozza a fomenut, es funkciot ad a gombokhoz.
     */
    public Main(){
        setTitle("Sejtautomata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Inditas");
        JButton exitButton = new JButton("Kilepes");
        JButton loadButton = new JButton("Betoltes");
        JButton randomButton = new JButton("Inditas veletlenszeru alaphelyzettel");
        JTextField bornTextField = new JTextField(5);
        JTextField surviveTextField = new JTextField(5);
        JTextField sizeTextField = new JTextField(5);
        JTextField loadFilenameTextField = new JTextField(8);
        JLabel bornLabel = new JLabel("Szuleteshez szukseges");
        JLabel surviveLabel = new JLabel("Tuleleshez szukseges");
        JLabel sizeLabel = new JLabel("Meret");
        JLabel loadLabel = new JLabel("Fajlnev betolteshez");
        JPanel fields = new JPanel();
        JPanel botButtonsPanel = new JPanel(new BorderLayout());
        JPanel topButtonsPanel = new JPanel(new BorderLayout());
        JPanel loadPanel = new JPanel();
        loadPanel.add(loadLabel);
        loadPanel.add(loadFilenameTextField);
        botButtonsPanel.add(loadPanel,BorderLayout.NORTH);
        botButtonsPanel.add(loadButton,BorderLayout.CENTER);
        botButtonsPanel.add(exitButton,BorderLayout.SOUTH);
        topButtonsPanel.add(randomButton,BorderLayout.SOUTH);
        topButtonsPanel.add(startButton,BorderLayout.NORTH);
        fields.add(bornLabel);
        fields.add(bornTextField);
        fields.add(surviveLabel);
        fields.add(surviveTextField);
        fields.add(sizeLabel);
        fields.add(sizeTextField);
        add(topButtonsPanel,BorderLayout.NORTH);
        add(fields,BorderLayout.CENTER);
        add(botButtonsPanel,BorderLayout.SOUTH);

        bornTextField.setText(born+"");
        surviveTextField.setText(survive+"");
        sizeTextField.setText(size+"");
        loadFilenameTextField.setText("filename");


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                File input=new File(loadFilenameTextField.getText()+".ser");
		        ObjectInputStream o=null;
		        try {
			        o = new ObjectInputStream(new FileInputStream(input));
			        g = (Grid)o.readObject();
                    g.run();
                    o.close();
		        }catch(IOException | ClassNotFoundException err ) {throw new RuntimeException(err);}
                
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setBorn(bornTextField.getText());
                setSurvive(surviveTextField.getText());
                if(sizeTextField.getText()!=null) setSize(Integer.parseInt(sizeTextField.getText()));
                
                g = new Grid(born, survive, size);
                

            }
        });

        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setBorn(bornTextField.getText());
                setSurvive(surviveTextField.getText());
                if(sizeTextField.getText()!=null) setSize(Integer.parseInt(sizeTextField.getText()));
                
                g = new Grid(born, survive, size);
                g.random();
                

            }
        });


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Setterek es Getterek

    public void setBorn(String born) {
        this.born = born;
    }
    
    public void setSurvive(String survive) {
        this.survive = survive;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSurvive() {
        return survive;
    }

    public String getBorn() {
        return born;
    }

    public static void main(String[] args){
        new Main();
    }
}
