package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButtonMigliore> cells = new ArrayList<>();
    private final Logic logic = new LogicImpl();
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = ((JButtonMigliore)e.getSource());
           
            if(!allowed(jb.getCoord().getX(), jb.getCoord().getY())){
                if(!logic.press(jb.getCoord())){
                    System.exit(1);
                }
                jb.setText(logic.getValue(jb.getCoord()));
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                String text;
                if(allowed(j,i)){text = "*";} else { text = " ";}
            	var pos = new Pair<>(j,i);
                final JButtonMigliore jb = new JButtonMigliore(text,pos);
                this.cells.add(jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private Boolean allowed(int x, int y){
        if(x%2 == 0){
            if(y%2 == 0){
                return false;
            } 
        } 
        return true;
    }

    private class JButtonMigliore extends JButton{
        private final Pair<Integer,Integer> coord;

        public JButtonMigliore(String text,int x, int y){
            super(text);
            coord = new Pair<Integer,Integer>(x, y);
        }
        public JButtonMigliore(String text,Pair<Integer,Integer> coord){
            super(text);
            this.coord = coord;
        }
        public Pair<Integer,Integer> getCoord(){
            return this.coord;
        }
    }
    
}
