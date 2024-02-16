package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import java.util.Iterator;

public class RulesEngineFactoryImpl implements RulesEngineFactory {

    @Override
    public <T> List<List<T>> applyRule(Pair<T, List<T>> rule, List<T> input) {
        List<List<T>> lista = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).equals(rule.get1())){
                lista.add(insertAtIndex(input, i, rule.get2()));
            }
        }
        return lista;
    }

    private <T> List<List<T>> applyRuleGlob(Pair<T, List<T>> rule, List<T> input){
        return List.of(input.stream().flatMap(x -> x.equals(rule.get1())? rule.get2().stream() : Stream.of(x)).toList());
    }

    private <T> List<T> applyConfRulesViaSequence(Pair<T, List<T>> rule1,
        Pair<T, List<T>> rule2, List<T> input,Iterator<Integer>sequence){
            List<T> output = new ArrayList<>();
        while (sequence.hasNext()) {
            int choice = sequence.next();
            for (T t : input) {
                if(input.equals(rule1.get1())){}
            }
        }
        return null;
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(Pair<T, List<T>> rule) {
       return new RulesEngine<T>() {
            List<List<T>> solutions;
            Iterator<List<T>> iterator;

            @Override
            public void resetInput(List<T> input) {
                solutions = applyRuleGlob(rule, input);
                iterator = solutions.iterator();
            }

            @Override
            public boolean hasOtherSolutions() {
                return iterator.hasNext();
            }

            @Override
            public List<T> nextSolution() {
                return iterator.next();
            }
        
       };
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(Pair<T, List<T>> baseRule, Pair<T, List<T>> cascadeRule) {
        return new RulesEngine<T>() {
            RulesEngine<T> engine = singleRuleEngine(baseRule);
            @Override
            public void resetInput(List<T> input) {
                engine.resetInput(input);
            }

            @Override
            public boolean hasOtherSolutions() {
                return engine.hasOtherSolutions();
            }

            @Override
            public List<T> nextSolution() {
                return applyRuleGlob(cascadeRule,engine.nextSolution()).get(0);
            }
            
        };
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conflictingRulesEngine'");
    }

    private <T> List<T> insertAtIndex(List<T> list,int index,List<T> elms){
        return Stream.concat(list.subList(0, index).stream(), 
            Stream.concat(elms.stream(), list.subList(index + 1, list.size()).stream())).toList();
    }

    private List<Iterator<Integer>> binaryCombinations(int size,List<Integer> number){
		if(number.size() == size){return List.of(number.iterator());}
		return Stream.concat(
			binaryCombinations(size, Stream.concat(number.stream(),Stream.of(0)).toList()).stream(),
			binaryCombinations(size, Stream.concat(number.stream(),Stream.of(1)).toList()).stream()).toList();
	}
    
}
