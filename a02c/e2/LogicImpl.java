package a02c.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic{
    private int n = 2;
    private Pair<Integer,Integer> fixedPoint = null;
    private List<Pair<Integer,Integer>> cells = new ArrayList<>();
    @Override
    public boolean press(Pair<Integer, Integer> coord) {
       if(fixedPoint == null)
       { fixedPoint = new Pair<Integer,Integer>(coord.getX() - 1, coord.getY() - 1);} else { n++; }
        return false;
    }

    @Override
    public String getAt(Pair<Integer, Integer> coord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAt'");
    }
    
}
