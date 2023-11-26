import javax.swing.*;
import java.awt.*;

public class Cell extends JButton{
    private boolean status;
    Color alive = Color.BLACK;
    Color dead = Color.WHITE;
    
    

    public Cell(){
        status = false;
        setBackground(dead);
    }

    public void changeStatus(){
        status = !status;
        setBackground(status ? alive : dead);
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean st) {
        status = st;
        setBackground(status ? alive : dead);
    }
    /*private void toggleCellState(int x, int y) {
            cellState[x][y] = !cellState[x][y];
            cells[x][y].setBackground(cellState[x][y] ? Color.BLACK : Color.WHITE);
    }*/
}

