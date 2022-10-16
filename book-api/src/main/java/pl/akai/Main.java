package pl.akai;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    /*
        Twoim zadaniem jest napisanie prostego programu do pobierania i transformowania danych
        udostępnianych przez API. Dokumentacje API możesz znależć pod poniższym linkiem:
        https://akai-recruitment.herokuapp.com/documentation.html

        Całe API zawiera jeden endpoint: https://akai-recruitment.herokuapp.com/book
        Endpoint ten zwraca liste książek zawierajacch informację takie jak:
        - id
        - tytuł
        - autor
        - ocena

        Twoim zadaniem jest:
        1. Stworzenie odpowiedniej klasy do przechowywania informacji o książce
        2. Sparsowanie danych udostępnianych przez endpoint. Aby ułatwić to zadanie,
           do projektu są dołaczone 3 najpopularniejsze biblioteki do parsowania JSONów
           do obiektów Javy - Gson, Org.Json, Jackson. Możesz wykorzystać dowolną z nich
        3. Po sparsowaniu JSONu do obiektów Javy, uzupełnij program o funkcję wypisującą 3 autorów z
           najwyższą średnią ocen. Na przykład, gdy osoba X jest autorem książki A z oceną 9 i B z oceną 8,
           to powinna zostać wyświetlona informacja: X - 8.5

       Projekt został utworzony przy użyciu najnowszej Javy 17,
       jednakże nic nie stoi na przeszkodzie użycia innej wersji jeśli chcesz
     */

    public static void main(String[] args) {

        try {
            //mapping
            ObjectMapper mapper = new ObjectMapper();
            List<Book> books = mapper.readValue(new URL("https://akai-recruitment.herokuapp.com/book"), new TypeReference<List<Book>>() {
            });

            System.out.println("Wszystkie ksiazki: " + books);

            //tworzymy hashmape <autor, rating>
            HashMap<String, Double> autorIOcena = new HashMap<String, Double>();
            //tworzymy liste autorow
            List<String> authors = new ArrayList<>();


           //dodajemy rating do autora
            for (int i = 0; i < books.size(); i++) {
                //System.out.println(books.get(i).toString());
                if(autorIOcena.containsKey(books.get(i).getAuthor())){
                    autorIOcena.put(books.get(i).getAuthor(), (autorIOcena.get(books.get(i).getAuthor())+books.get(i).getRating()));
                }else{
                autorIOcena.put(books.get(i).getAuthor(),books.get(i).getRating());}

                authors.add(books.get(i).getAuthor());
            }


            //dzielimy rating przez ilosc wystapienia autora
            for (String key : autorIOcena.keySet()) {

                //sprawdza ile razy dany autor się pojawił w wszystkich obiektach
                double dzielnik = authors.stream().filter(author ->key.equals(author)).count();

                //zmodyfikowanie value i zaokraglenie
                autorIOcena.put(key, (double) Math.round(autorIOcena.get(key)*100/dzielnik)/100);
            };
            System.out.println("autor i ocena: " + autorIOcena);

            //wyciagamy 3 najlepszych
            List<String> keys = autorIOcena.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());

            System.out.println("3 najlepszych autorów: ");
            for(String key : keys){
                System.out.println(key + " - " + autorIOcena.get(key));
            }


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




}


}
