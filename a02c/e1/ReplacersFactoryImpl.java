package a02c.e1;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReplacersFactoryImpl implements ReplacersFactory {

    @Override
    public <T> Replacer<T> noReplacement() {
        return (input,t) -> List.of();
    }

    @Override
    public <T> Replacer<T> duplicateFirst() {
       return (list,t) -> List.of(replaceAtI(
            IntStream.range(0, list.size()).filter(x ->list.get(x).equals(t)).findFirst().orElse(-1),
            list,
            List.of(t,t))).stream().filter(x -> !x.isEmpty()).toList();
    }

    @Override
    public <T> Replacer<T> translateLastWith(List<T> target) {
        return (list,t) -> List.of(replaceAtI(
            Stream.iterate(list.size() - 1, n -> n-1).limit(list.size()).filter(x ->list.get(x).equals(t)).findFirst().orElse(-1),
            list,
            target)).stream().filter(x -> !x.isEmpty()).toList();
    }

    @Override
    public <T> Replacer<T> removeEach() {
        return (list,t) -> IntStream.range(0, list.size())
            .filter(x -> list.get(x).equals(t))
            .mapToObj(x -> replaceAtI(x, list, List.of())).toList();
    }

    @Override
    public <T> Replacer<T> replaceEachFromSequence(List<T> sequence) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replaceEachFromSequence'");
    }

    private <T>List<T> replaceAtI(int index,List<T> list, List<T> replace){
        if(index == -1){return List.of();}
        return Stream.concat(
                list.subList(0, index).stream(), 
                Stream.concat(replace.stream(), 
                    list.subList(index + 1, list.size()).stream())).toList();
    }
    
}
