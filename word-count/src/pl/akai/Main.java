package pl.akai;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static String[] sentences = {
            "Taki mamy klimat",
            "Wszędzie dobrze ale w domu najlepiej",
            "Wyskoczył jak Filip z konopii",
            "Gdzie kucharek sześć tam nie ma co jeść",
            "Nie ma to jak w domu",
            "Konduktorze łaskawy zabierz nas do Warszawy",
            "Jeżeli nie zjesz obiadu to nie dostaniesz deseru",
            "Bez pracy nie ma kołaczy",
            "Kto sieje wiatr ten zbiera burzę",
            "Być szybkim jak wiatr",
            "Kopać pod kimś dołki",
            "Gdzie raki zimują",
            "Gdzie pieprz rośnie",
            "Swoją drogą to gdzie rośnie pieprz?",
            "Mam nadzieję, że poradzisz sobie z tym zadaniem bez problemu",
            "Nie powinno sprawić żadnego problemu, bo Google jest dozwolony"
    };

    public static void main(String[] args) {
        /* TODO Twoim zadaniem jest wypisanie na konsoli trzech najczęściej występujących słów
                w tablicy 'sentences' wraz z ilością ich wystąpień..

                Przykładowy wynik:
                1. "mam" - 12
                2. "tak" - 5
                3. "z" - 2
        */
        List<String> wordList = new ArrayList<String>();

        //rozdziela slowa i wrzuca do arraylisty
        for (String sentence : sentences){

            String[] wordsInSentence = sentence.split(" ");

            for(String word : wordsInSentence){
                wordList.add(word);
            }
        }

        System.out.println("lista slow: " + wordList);

        HashMap<String, Integer> words = new HashMap<String, Integer>();

        //liczy wczystkie wystąpienia i wrzuca do hashmapy
        for (String word : wordList){
            if(words.containsKey(word.toLowerCase())){
                words.put(word.toLowerCase(), words.get(word.toLowerCase())+1);
            }else{
                words.put(word.toLowerCase(),1);
            }
        }
        //sortuje malejaco
        List<String> keys = words.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());

        System.out.println("3 najczesciej wystepujace: ");


        //printujemy najwyzsze wartosci
        for(int i = 0; i <3; i++){
            String key = keys.get(0);
            int occurence = words.get(key);
            System.out.println(String.format("%s. \"%s\" - %s ",(i+1),key,occurence));

            keys.remove(0);

            //sprawdzamy czy jest ex aequo
            while(words.get(keys.get(0)) == occurence){
                key = keys.get(0);
                System.out.println(String.format("%s. \"%s\" - %s ",(i+1),key,occurence));
                keys.remove(0);
            }


        }


    }

}