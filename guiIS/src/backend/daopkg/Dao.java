package backend.daopkg;


import java.util.List;

//https://www.baeldung.com/java-dao-pattern
public interface Dao<T> {

    //Optional<T> get(Map keys); TOBEIMPLEMENTED

    List<T> getAll();

    void save(T t);

    void update(T oldt,T newt);

    void delete(T t);
}