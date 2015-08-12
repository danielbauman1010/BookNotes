package booknotes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class BookNotes {
        
    //Daniel Bauman's book notes - java 8 in action
     
    public static void main(String[] args) {
        System.out.println("************");
        System.out.println("*Book Notes*");
        System.out.println("************");
  
        //lambdas:                                      pure pleasure
        //brief example:
        ArrayList<String> names = new ArrayList();
        names.add(0, "Tom");
        names.add("Jhon");
        System.out.println("Before removal:");
        System.out.println(names.toString());
        names.removeIf((String str) -> str.startsWith("T")); //Ta da - 
        System.out.println("After removal:");
        System.out.println(names.toString());
        names.add(0,"Daniel");
        System.out.println("first list:");
        System.out.println(names.toString());
        ArrayList<String> longNames = new ArrayList();
        names.forEach((String s) -> {
            if(s.length()>=5)
                longNames.add(s);
        });
        System.out.println("filtered list:");
        System.out.println(longNames.toString());
        
        //functional interfaces:
        // predicate -> boolean
        Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
        System.out.println("1000 is an odd number? " + oddNumbers.test(1000));
        IntPredicate evenNumbers = (int i) -> i % 2 == 0; //IntPredicate = Predicate<Integer>
        System.out.println("1000 is an even number? " + evenNumbers.test(1000));
        
        //map of functional interfaces:
        Map<String,String> m = new HashMap();
        m.put("interface", "intput -> output");
        m.put("Predicate", "T -> boolean");
        m.put("Consumer", "T -> void");
        m.put("Function", "T -> R");
        m.put("Supplier", "() -> T");
        m.put("UnaryOperator", "T -> T");
        m.put("BinaryOperator", "(T, T) -> T");
        m.put("BiPredicate", "(L, R) -> boolean");
        m.put("BiConsumer", "(T, U) -> void");
        m.put("BiFunction", "(T, U) -> R");
         
        m.forEach((String s1,String s2) -> System.out.println("use " + s1 + " for "+ s2));
        //refferences instead of lambdas, even better  + streams - java 8 is in full action now
        Stream<String> s = names.stream();
        s.forEach(System.out::println);

        //using streams
        names.add("Michael");
        names.add("Ron");
        names.add("Ben");
        List<String> Longnames = names.stream().filter((String n) -> n.length()>5).collect(toList());
        System.out.println(Longnames.toString());    //Ta da- 
        //lets check this up - 
        List<Integer> lengths = names.stream().map(String::length).collect(toList());
        for(int i = 0;i<lengths.size();i++) {  // lengths.size()   -   LOL
            System.out.println(names.get(i) + " : " + lengths.get(i));
        } //success in it's finest moments
        //one more trick to go - 
        List<Integer> lengthsSizes = lengths.stream().distinct().collect(toList()); 
        // lengthsSizes - is there any better name for a list than that?
        lengthsSizes.forEach(System.out::print); 
        System.out.println("");
        //done. Do i really have any more to learn? oh right... 2 more chapters
        //lets get all the letters used in names, too easy... let's get 'em all without the same letter twice!
        List<String> characters = names.stream().map((String w) -> w.split(""))
                .flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        //that's one long line... it's like dealing with python all over again
        //hopefully java 9 would make this look alittle bit better
        
        characters.forEach(System.out::print);
        //Quiz 5.2
        //list of numbers to list of squares of each number
        System.out.println("");
        List<Integer> squareOfLengths = lengths.stream().map(n -> n*n).collect(toList());
        System.out.println(lengths.toString());
        System.out.println(squareOfLengths.toString());
        System.out.println("");
        //making all posible pairs
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int []> pairs = numbers1.stream().flatMap(i -> numbers2.stream()
                .map(j -> new int[]{i,j})).collect(toList());
        //so beautifull
        pairs.forEach(p -> {
            System.out.print(" [ "+p[0]+" , "+p[1]+" ] ");
        });
        System.out.println("");
        //optionals and findany/findfirst
        Optional<int []> divBy3 = pairs.stream().filter(x -> (x[0]+x[1]) %3 == 0).findAny();
        //find a pair that it's sum could be devided by 3
        divBy3.ifPresent(num -> {
            System.out.println(" [ "+num[0]+" , "+num[1]+" ] "+" -> "+num[0]+"+"+num[1]+"="+(num[0]+num[1])+" -> %3 = 0");
        }); 
        
        int big10 = numbers1.stream().filter(n -> n >= 10).findAny().orElse(0); //are you bigger than 10?
        System.out.println(big10);
        Optional sum = numbers1.stream().reduce(Integer::sum); //efficientcy in it's finest
        sum.ifPresent(System.out::println); //sum of all numbers
        Optional min = numbers1.stream().reduce(Integer::min);
        min.ifPresent(System.out::println); //minimum value
        Optional max = numbers1.stream().reduce(Integer::max);
        max.ifPresent(System.out::println); //maximum value - done pretty easily
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
                .boxed().flatMap(a ->IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                        .mapToObj(b ->new int[]{a, b, (int)Math.sqrt(a * a + b * b)}));
        // Java.version(8).lines = VERY LONG;
        System.out.println("Pythagorien triples: (limit 5)");
        pythagoreanTriples.limit(5).forEach(t -> {
            System.out.println(t[0] + ", " + t[1] + ", " + t[2]);            
        });   //magic (:0
        System.out.println("Boxes with volume smaller than a hundred: (limit 5)");
        Stream<int[]> boxesWithVolumeSmallerThanAhundred = IntStream.rangeClosed(1, 100)
                .boxed().flatMap(l ->IntStream.rangeClosed(l, 100).boxed()
                        .flatMap(h ->IntStream.rangeClosed(l, 100)
                        .filter(w -> l*h*w <= 100).mapToObj(w -> new int[]{l,h,w,(l*h*w)})));
        boxesWithVolumeSmallerThanAhundred.limit(5).forEach(b -> {
            System.out.println(b[0]+", "+b[1]+", "+b[2]+","+b[3]);
        }); 
        System.out.println("Cubes with volume smaller than a hundred:");
        Stream<int[]> cubesWithVolumeSmallerThanAhundred = IntStream.rangeClosed(1, 100)
                .boxed().filter(l -> l*l*l <= 100)
                .map(l -> new int[]{l,l,l,(l*l*l)});
        cubesWithVolumeSmallerThanAhundred.forEach(c -> {
            System.out.println(c[0]+", "+c[1]+", "+c[2]+","+c[3]);
        }); //apernatly there are only four cubes with volumes smaller than a hundred
    
        //fibonacci serie:
        System.out.println("fibonacci serie first 10 numbers:");
        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1],t[0]+t[1]})
                .limit(10).map(t -> t[0])
                .forEach(n -> System.out.print(n+", "));
        //comparing and using MaxBy / MinBy
        System.out.println("");
        Comparator<String> compareLemgths = Comparator.comparingInt(String::length);
        Optional<String> longestName = names.stream().collect(maxBy(compareLemgths));
        System.out.println("longest name in the names list:");
        longestName.ifPresent(System.out::println);
        System.out.println("");
        //summing with summingInt
        System.out.println("total length of all names:");
        int sumOfLengths = names.stream().collect(summingInt(String::length));
        System.out.println(sumOfLengths);
    }
    
}
    
