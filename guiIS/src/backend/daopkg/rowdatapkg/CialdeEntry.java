package backend.daopkg.rowdatapkg;
import backend.Euro;
import backend.daopkg.gateways.CialdeDao;


public class CialdeEntry extends AbstractEntryDB   {
    private String tipo;
    private Euro prezzo;

    public CialdeEntry(String tipo, Euro prezzo) {
        this.tipo = tipo;
        this.prezzo = prezzo;
    }

    public CialdeEntry(String tipo){
        this.tipo=tipo;
    }

    public CialdeEntry(){}


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if(!mementoStateIsSet()){
            mementoState=new MementoCialde();
            ((MementoCialde)mementoState).setMementoState(this);
        }
        this.tipo = tipo;
    }

    public Euro getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Euro prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "CialdeEntry: prezzo:" + prezzo + " tipo:" + tipo;
    }


    @Override
    public Class<CialdeDao> getCorrespondigDaoClass() {
        return CialdeDao.class;
    }

    @Override
    public Memento createMemento() {
        return new MementoCialde();
    }


    private class MementoCialde implements Memento<CialdeEntry> {

        private String tipo;
        private Euro prezzo;

        @Override
        public void setMementoState(CialdeEntry originator) {
            this.tipo=originator.tipo;
            this.prezzo=originator.prezzo;
        }

        @Override
        public CialdeEntry getMementoState() {
            return null;
        }

    }


}
