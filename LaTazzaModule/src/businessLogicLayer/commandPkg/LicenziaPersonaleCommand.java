package businessLogicLayer.commandPkg;

import businessLogicLayer.BackEndInvoker;

public class LicenziaPersonaleCommand implements  Command{
    private String nome, cognome;
    private BackEndInvoker backEndInvoker;

    public LicenziaPersonaleCommand(String nome, String cognome, BackEndInvoker backEndInvoker){
        this.nome=nome;
        this.cognome=cognome;
        this.backEndInvoker=backEndInvoker;
    }

    @Override
    public LaTazzaErrno execute() throws Exception {
        return backEndInvoker.getControllerPersonale().licenziaPersonale(nome,cognome);
    }
}

