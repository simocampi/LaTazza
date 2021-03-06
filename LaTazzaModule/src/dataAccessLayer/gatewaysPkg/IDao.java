package dataAccessLayer.gatewaysPkg;
import dataAccessLayer.rowdatapkg.AbstractEntryDB;
import java.util.List;

/**
 * Questa interfaccia viene implementata( ed è pensata in coppia con) la classe DaoInvoker.
 * Questa interfaccia si pone, in una architettura multi-tier, come il confine tra il DataLayer e il BusinessLogicLayer
 * e cerca di fare Information Hiding rispetto alla gestione del database e le sue eccezioni.
 * Implementa quindi insieme all'interfaccie Memento e AbstractEntry il DataAccessLayer.
 * Cerca di rendere semplice la gestione della coerenza tra oggetti in Ram e oggetti salvati nel DB.
 * I metodi (getAll,save...) hanno due possibili risultati: true quindi tutto ok, o false se qualcosa non va a buon fine.
 * I metodi non restituiranno MAI un eccezione (neanche i nullP. exc).
 * Se un metodo di questa interfaccia fallisce viene aggiornato un file di log dove viene
 * riportata data-ora-causa dell'eccezione.
 * @apiNote
 * ESEMPIO:
 *  public static void main(String[] args) {
 *           Connection conn=.... in qualche modo definita;
 *           IDao dao=new DaoInvoker(conn);
 *           List<Personale> listaPersonale=dao.getAll(Personale.class);//ottengo la lista del personale nel DB.
 *           Personale newPers=new Personale("George","Hotz",true);
 *           if(dao.save(newPers)){//se va tutto ok
 *               listaPersonale.add(newPers);
 *               //a questo punto abbiamo consistenza tra Db e oggetti in ram.
 *           }else{
 *               System.err.println("Impossibile aggiornare il database!");
 *           }
 *           //ora modifico il personale appena inserito "George Hotz" con "andrea straforini"
 *           //lo rimuovo dalla lista così non ho 2 riferimenti...
 *           listaPersonale.remove(newPers);
 *           newPers.setNome("andrea");
 *           newPers.setCognome("straforini");
 *           if(dao.update(newPers)){
 *               listaPersonale.add(newPers);//lo riaggiungo alla lista
 *           }else{
 *               System.out.println("Update fallito ripristino stato originale");
 *               newPers.undoChanges();
 *           }
 *
 *
 *           //eseguo operazioni multiple in una unica transazione
 *           listaPersonale=dao.getAll(Personale.class);//ottengo la lista del personale nel DB.
 *           int oldLength=listaPersonale.size();
 *           dao.startTransaction()
 *              dao.save(new Personale("Richard","https://tinyurl.com/yxqfq9zj");
 *              dao.save(new Personale("rick_rolled","https://tinyurl.com/2fcpre6");
 *              dao.delete("babbo","natale");
 *           dao.endTransaction();
 *           listaPersonale=dao.getAll(Personale.class);//ottengo la lista del personale nel DB.
 *
 *           if(dao.getTransactionStatus()){//se la transazione è andata a buon fine allora il personale è aumentato di 1
 *             assert(listaPersonale.size()==oldLength+1);
 *           }else{//altrimenti non è cambiato niente
 *             assert(listaPersonale.size()==oldLength);
 *           }
 *     }
 */
public interface IDao {


    /**
     * Questo metodo permette di ottenere la lista di tutti gli oggetti di classe t.
     * @param t il tipo della classe. es. passare Personale.class se si vuole la lista del personale
     * @return la lista  se operazione andata a buon fine, null altrimenti.
     */
    <T extends AbstractEntryDB> List<T> getAll(Class<T> t);

    /**
     * Questo metodo permette di ripristinare la coerenza tra oggetti salvati nel database e oggetti in Ram.
     * @param t l'oggetto da salvare nel database.
     * @return true se operazione andata a buon fine, false altrimenti.
     */
    <T extends AbstractEntryDB> boolean save(T t);

    /**
     * Questo metodo permette di aggiornare l'oggetto passato. t infatti ( implementando AbstractEntry) implementa
     * il pattern Memento.
     * Il DaoInvoker quindi utilizza quest'ultimo per risalire allo stato originale dell'oggetto.
     * @param t l'oggetto modificato da rendere coerente con la sua "versione" salvata nel DB.
     * @return true se operazione andata a buon fine, false altrimenti.
     * Nota:se l'update va a buon fine il memento viene eliminato e rimane solo lo stato attuale.
     */
    <T extends AbstractEntryDB> boolean update(T t);

    /**
     * Questo metodo permette di eliminare dal database l'oggetto "salvato" corrispondente all'oggetto passato.
     * @param t l'oggetto da eliminare all'interno del DB.
     * @return true se operazione andata a buon fine, false altrimenti.
     */
    <T extends AbstractEntryDB> boolean delete(T t);

    /**
     * Il seguente metodo permette di iniziare una transazione per il database.Leggere commento endTransaction.
     */
    void startTransaction();

    /**
     * Il seguente metodo chiude la transazione iniziata da startTransaction().
     * Se tutte le operazioni (getAll;save;update;delete) eseguite dopo il metodo startTransaction()
     * sono andate a buon fine la transazione è valida e viene eseguito il commit.
     * Se la transazione ha fallito per una delle operazini viene eseguito il rollBack.
     */
    void endTransaction();

    /**
     * Il metodo ritorna lo stato dell'ultima transazione effettuata o lo stato della transazione corrente.
     * @return il corrente stato della transazione o lo stato finale dell'ultima transazione effettuata
     * (se il metodo viene chiamato dopo endTransaction() )
     */
    boolean getTransactionStatus();

    /**
     * Setta lo status della transazione corrente.
     */
    void setTransactionStatus(boolean newStat);
}
