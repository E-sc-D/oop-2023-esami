package a01a.e1;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TimetableFactoryImpl implements TimetableFactory{

    @Override
    public Timetable empty() {
       return new TimetableImpl();
    }

    @Override
    public Timetable single(String activity, String day) {
        return new TimetableImpl(Map.of(new Pair<>(activity,day),1));
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
       return new TimetableImpl(new TimetableImpl(table1),new TimetableImpl(table2));
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        return new Timetable() {
            

            @Override
            public Timetable addHour(String activity, String day) {
                return table.addHour(activity, day);
            }

            @Override
            public Set<String> activities() {
                return table.activities();
            }

            @Override
            public Set<String> days() {
                return table.days();
            }

            @Override
            public int getSingleData(String activity, String day) {
                int limit = bounds.apply(activity, day);
                int value = table.getSingleData(activity, day);

                if(value < limit){return value;} else {return limit;}
            }

            @Override
            public int sums(Set<String> activities, Set<String> days) {
                int sum = 0;
                int value;
                int limit;
                for (String day : days) {
                    for (String act : activities) {
                       value = table.getSingleData(act, day);
                       limit = bounds.apply(act, day);
                       if(value > limit){
                            sum += limit;
                       } else { sum += value; }
                    }
                }
                return sum;
            }
            
        };
    }

    private class TimetableImpl implements Timetable{

        Map<Pair<String,String>,Integer> table;

        public TimetableImpl(Timetable timetable){
            var activities = timetable.activities();
            var days = timetable.days();
            Map<Pair<String,String>,Integer> tab = new HashMap<>();

            for (String act : activities) {
                for (String day : days) {
                    tab.put(new Pair<>(act,day), timetable.getSingleData(act, day));
                }
            }
            this.table = tab;
            
        }
        public TimetableImpl(Map<Pair<String,String>,Integer> table){
            this.table = table;
        }
        public TimetableImpl(){
            this.table = new Hashtable<>();
        }
        public TimetableImpl(Map<Pair<String,String>,Integer> t1,Map<Pair<String,String>,Integer> t2){
            this.table = mergeTables(t1, t2);
        }
        public TimetableImpl(TimetableImpl t1, TimetableImpl t2){
            this.table = mergeTables(t1.getTable(), t2.getTable());
        }

        @Override
        public Timetable addHour(String activity, String day) {
            var key = new Pair<>(activity, day);
            var mappa = Map.of(key,1);
            return new TimetableImpl(mergeTables(this.table, mappa));
        }

        @Override
        public Set<String> activities() {
            return this.table.keySet().stream().map(x ->x.get1()).collect(Collectors.toSet());
        }

        @Override
        public Set<String> days() {
            return this.table.keySet().stream().map(x ->x.get2()).collect(Collectors.toSet());
        }

        @Override
        public int getSingleData(String activity, String day) {
            if(this.table.containsKey(new Pair<>(activity,day))){
                return this.table.get(new Pair<>(activity,day));
            }
            return 0;
           
        }

        public Map<Pair<String,String>,Integer> getTable(){
            return new HashMap<>(this.table);
        }

        @Override
        public int sums(Set<String> activities, Set<String> days) {
            int sum = 0;
            for (String day : days) {
                for (String act : activities) {
                    if(table.containsKey(new Pair<>(act, day))){
                        sum += table.get(new Pair<>(act, day));
                    }
                }
            }
            return sum;
        }

        private Map<Pair<String,String>,Integer> mergeTables(Map<Pair<String,String>,Integer> t1
            ,Map<Pair<String,String>,Integer> t2){

            var table = new Hashtable<>(t1);
            t2.forEach((key, value) -> table.merge(key, value, Integer::sum));
            return table;
        }

    }
    
}
