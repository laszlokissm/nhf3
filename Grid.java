import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

public class Grid extends JFrame implements Serializable{
    //private static final long serialVersionUID = 6529685098267757690L;
    
    private int gridSize = 50;
    int time=100;
    private Cell[][] cells;
    private Timer timer;
    private ArrayList<Integer> neededToSurvive;
    private ArrayList<Integer> neededToBorn;
    private JPanel buttonPanel;

    /**
     * @param b szuleteshez szukseges elo szomszedok szamainak listaja Stringben ';'-al elvalaszva
     * @param s tuleleshez szukseges elo szomszedok szamainak listaja Stringben ';'-al elvalaszva
     * @param size a grid merete
     */
    public Grid(String b, String s,int size){
        //neededToBorn = new int[(b.length()+1)/2];
        neededToBorn = new ArrayList<Integer>((b.length()+1)/2);
        neededToSurvive = new ArrayList<Integer>((s.length()+1)/2);
        String[] tempb = b.split(";");
        for (int i = 0; i < neededToBorn.size(); i++) {
            neededToBorn.add(i, Integer.parseInt(tempb[i]));
        }

        String[] temps = s.split(";");
        for (int i = 0; i < neededToSurvive.size(); i++) {
            neededToBorn.add(i, Integer.parseInt(temps[i]));
        }

        /*
         * Check int arrays
         */
        /*for (int i = 0; i < neededToBorn.length; i++) {
            System.out.println(neededToBorn[i]);
        }
        for (int i = 0; i < neededToSurvive.length; i++) {
            System.out.println(neededToSurvive[i]);
        }*/

        timer= new Timer(time, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                updateGrid();
            }
        });
        
        gridSize=size;
        setTitle("Sejtautomata");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        cells = new Cell[gridSize][gridSize];
        JPanel g = new JPanel();
        g.setLayout(new GridLayout(gridSize, gridSize));
        
        //Grid inicializálása
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                cells[i][j]= new Cell();
                final int x = i;
                final int y = j;
                cells[x][y].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        cells[x][y].changeStatus();
                    }
                });
                g.add(cells[i][j]);
            }
        }
        
        add(g,BorderLayout.CENTER);
        
        final JTextField timeTextField = new JTextField(5);
        timeTextField.setText(time + "");

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save");
        
        final JTextField saveFilenameTextField = new JTextField(8);
        saveFilenameTextField.setText("filename");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTime(Integer.parseInt(timeTextField.getText() ));
                timeTextField.setEditable(false);
                timer.setDelay(time);
                start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
                timeTextField.setEditable(true);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save(saveFilenameTextField.getText());
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(timeTextField);
        buttonPanel.add(nextButton);
        buttonPanel.add(saveFilenameTextField);
        buttonPanel.add(saveButton);
        add(buttonPanel,BorderLayout.NORTH);


        setPreferredSize(new Dimension(16*gridSize,16*gridSize));
        setMinimumSize(new Dimension(400,400));
        setMaximumSize(new Dimension(800,800));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
        
    /**
     * @param t mennyi idokozonkent frissuljon a sejtautomata
     */
    public void setTime(int t) {
        time=t;
    }

    /**
     * sejtautomatat lepteto timer inditasa
     */
    public void start(){
        timer.start();
    }

    /**
     * egyet leptet a sejtautomatan
     */
    public void next(){
        updateGrid();
    }

    /**
     * kimenti a grid allasat egy fajlba
     * @param fname fajlnev menteshez
     */
    public void save(String fname){
        //Mentes
        File output=new File(fname+".ser");
		ObjectOutputStream o=null;
		try {
			o = new ObjectOutputStream(new FileOutputStream(output));
			o.writeObject(this);
            o.close();
        }catch(IOException e) {throw new RuntimeException(e);}
    }
    
    /**
     * a sejtautomata osszes cellajat veletlenszeru allapotba allitja
     */
    public void random(){
        Random rand = new Random();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if(rand.nextDouble(1)<0.3) cells[i][j].changeStatus();
            }
        }
    }

    /**
     * a betoltes utan ez a fuggveny ujra hozzaadja a Gridhez a funkciokat
     */
    public void run(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                final int x = i;
                final int y = j;
                cells[x][y].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        cells[x][y].changeStatus();
                    }
                });
            }
        }
        
        
        
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save");
        final JTextField timeTextField = new JTextField(5);
        timeTextField.setText(time + "");
        final JTextField saveFilenameTextField = new JTextField(8);
        saveFilenameTextField.setText("filename");
        
        buttonPanel.removeAll();
        pack();
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(timeTextField);
        buttonPanel.add(nextButton);
        buttonPanel.add(saveFilenameTextField);
        buttonPanel.add(saveButton);
        add(buttonPanel,BorderLayout.NORTH);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTime(Integer.parseInt(timeTextField.getText() ));
                timeTextField.setEditable(false);
                timer.setDelay(time);
                start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
                timeTextField.setEditable(true);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save(saveFilenameTextField.getText());
            }
        });

        timer= new Timer(time, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                updateGrid();
            }
        });

        pack();
        setVisible(true);
    } 
    
    public void stop(){
        if(timer != null && timer.isRunning()) timer.stop();
    }

    public void updateGrid(){
        boolean[][] newStatus = new boolean[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int n = neighborhood(i, j);

                if(cells[i][j].getStatus()){
                    boolean doesItSurvive=false;
                    for (int k = 0; k < neededToSurvive.size(); k++) {
                        if (n==neededToSurvive.get(k)) {
                            doesItSurvive=true;
                        }
                    }
                    newStatus[i][j]=doesItSurvive;
                }else{
                    boolean isItBorn=false;
                    for (int k = 0; k < neededToBorn.size(); k++) {
                        if (n==neededToBorn.get(k)) {
                            isItBorn=true;
                        }
                    }
                    newStatus[i][j]=isItBorn;
                }
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                cells[i][j].setStatus(newStatus[i][j]);
            }
        }
    }

    /**
     * @param x egy cella x koordinataja
     * @param y egy cella y koordinataja
     * @return az (x,y) helyen talalhato cella elo szomszedainak szama
     */
    public int neighborhood(int x, int y){
        int c=0;
        for(int i = x-1;i<=x+1;i++){
            for (int j = y-1; j <= y+1; j++) {
                if (i >= 0 && i < gridSize && j >= 0 && j < gridSize && !(i == x && j == y)) {
                    if (cells[i][j].getStatus()==true) c++;
                }
            }
        }
        return c;
    }
}
