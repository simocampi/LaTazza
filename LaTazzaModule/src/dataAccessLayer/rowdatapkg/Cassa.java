package dataAccessLayer.rowdatapkg;

import dataAccessLayer.mementoPkg.Memento;
import utils.Euro;
import static java.util.Objects.requireNonNull;

public class Cassa extends AbstractEntryDB {

    private final static long saldoIniziale= 500;
    private Euro saldo;

    public Cassa(){
        saldo= new Euro(saldoIniziale);
    }
    public Cassa(Euro s){
        saldo=requireNonNull(s);
    }


    public Euro getCopySaldo(){
        return new Euro(saldo.getEuro(),saldo.getCentesimi());
    }

    public void incrementaSaldo(Euro euro)throws Euro.OverflowEuroException,NullPointerException {
        saldo.aggiungiImporto(euro);
    }

    public boolean decrementaSaldo(Euro euro) throws NullPointerException {
        try{
            saldo.sottraiImporto(euro);
        }catch (Euro.InsufficientFundsException e){
            return false;
        }
        return true;
    }


    @Override
    public Memento createMemento() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void undoChanges() {
        throw  new UnsupportedOperationException();
    }


}
