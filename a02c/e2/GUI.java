package a02c.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButtonMigliore> cells = new ArrayList<>();
    private final Logic logic = new LogicImpl();
    private final int size;
    public GUI(int size) {
        this.size = size;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButtonMigliore)e.getSource();
        	if(logic.press(jb.coord())){System.exit(1);}
            update();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButtonMigliore jb = new JButtonMigliore(pos.toString(),pos);
                this.cells.add(jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private class JButtonMigliore extends JButton{
        private final Pair<Integer,Integer> coord;
        public JButtonMigliore(String text, Pair<Integer,Integer> coord){
            super(text);
            this.coord = coord;
        }
        public Pair<Integer,Integer> coord(){return this.coord;}
    }
    private void update(){
        for (JButtonMigliore jButtonMigliore : cells) {
            jButtonMigliore.setText(logic.getAt(jButtonMigliore.coord()));
        }
    }
    
}
