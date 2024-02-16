package a02a.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {
    List<Pair<Integer,Integer>> cells;

    public LogicImpl(){
        cells = new ArrayList<>();
    }

    @Override
    public Boolean press(Pair<Integer, Integer> coord) {
        if(cells.contains(coord)){
            cells.remove(coord);
        } else { cells.add(coord); }

        if(cells.size() > 3){return check();};
        return true;
    }

    @Override
    public String getValue(Pair<Integer, Integer> coord) {
       return cells.contains(coord)? "0":" ";
    }

    private Boolean check(){
        Boolean result = false;
        List<Pair<Integer,Integer>> elm = new ArrayList<>(cells.subList(cells.size() - 4, cells.size()));

        for (Pair<Integer,Integer> pair : elm) {
            for (Pair<Integer,Integer> pair2 : elm) {
                var dist = distance(pair,pair2);
                if(dist > 2){result = true; break;}
            }
            if(result == true){break;}
        }

        return result;
    }

    private int distance(Pair<Integer,Integer> c1,Pair<Integer,Integer> c2){
        return  Double.valueOf(Math.sqrt(Math.pow((c1.getX() - c2.getX()),2) + Math.pow(c1.getY() - c2.getY(),2))).intValue();
    }
    
}
