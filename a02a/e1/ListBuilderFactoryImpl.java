package a02a.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class ListBuilderFactoryImpl implements ListBuilderFactory {

    @Override
    public <T> ListBuilder<T> empty() {
        return fromList(List.of());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
       return fromList(List.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilder<T>() {
            private List<T> lista = new ArrayList<>(list); 
            @Override
            public ListBuilder<T> add(List<T> list) {
                return fromList(concatList(lista, list));
            }

            @Override
            public ListBuilder<T> concat(ListBuilder<T> lb) {
                return fromList(concatList(lista,lb.build()));
            }

            @Override
            public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
                var temp = new ArrayList<>(lb.build());
                return fromList(lista.stream().flatMap(x -> x.equals(t)?temp.stream():Stream.of(x)).toList());
            }

            @Override
            public ListBuilder<T> reverse() {
                var temp = new ArrayList<>(lista);
                Collections.reverse(temp);
                return fromList(temp);
            }

            @Override
            public List<T> build() {
                return new ArrayList<>(lista);
            }

            private List<T> concatList(final List<T> l1,final List<T> l2){
                return Stream.concat(l1.stream(), l2.stream()).toList();
            }
            
        };
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
       return fromList(appendToEnds(builderListToList(builderList), start, stop));
    }

    private <T> List<T> appendToEnds(List<T>lista, T first, T end){
        return Stream.concat(Stream.of(first),Stream.concat(lista.stream(), Stream.of(end))).toList();
    }
    private <T> List<T> builderListToList(List<ListBuilder<T>> builderList){
        return builderList.stream().flatMap(x -> x.build().stream()).toList();
    }

    
}
