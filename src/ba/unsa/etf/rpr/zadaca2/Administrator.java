package ba.unsa.etf.rpr.zadaca2;

public class Administrator extends Korisnik {
    public Administrator(String ime, String prezime, String email, String username, String password) {
        super(ime, prezime, email, username, password);
        setDaLiJeAdmin(true);
    }

    public Administrator() {
        super();
        setDaLiJeAdmin(true);
    }

    @Override
    public boolean checkPassword() {
        return getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).*");
    }
}
