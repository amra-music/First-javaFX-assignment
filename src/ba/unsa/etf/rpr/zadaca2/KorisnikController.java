package ba.unsa.etf.rpr.zadaca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Button btnObrisi;
    public Button btnDodaj;
    public Button btnKraj;
    public Slider sliderGodinaRodjenja;
    private Image ikonaObrisi = new Image(getClass().getResourceAsStream("/images/edit-delete.png"));
    private Image ikonaDodaj = new Image(getClass().getResourceAsStream("/images/list-add.png"));
    private Image ikonaKraj = new Image(getClass().getResourceAsStream("/images/application-exit.png"));

    private KorisniciModel model;


    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();
        });

        btnObrisi.setGraphic(new ImageView(ikonaObrisi));
        btnDodaj.setGraphic(new ImageView(ikonaDodaj));
        btnKraj.setGraphic(new ImageView(ikonaKraj));


        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty());
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty());
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty());
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty());
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty());
                fldPasswordRepeat.textProperty().unbindBidirectional(oldKorisnik.passwordRepeatProperty());
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldKorisnik.godinaRodjenjaProperty());
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                fldPasswordRepeat.setText("");
                sliderGodinaRodjenja.setValue(2000);
            } else {
                fldIme.textProperty().bindBidirectional(newKorisnik.imeProperty());
                fldPrezime.textProperty().bindBidirectional(newKorisnik.prezimeProperty());
                fldEmail.textProperty().bindBidirectional(newKorisnik.emailProperty());
                fldUsername.textProperty().bindBidirectional(newKorisnik.usernameProperty());
                fldPassword.textProperty().bindBidirectional(newKorisnik.passwordProperty());
                sliderGodinaRodjenja.valueProperty().bindBidirectional(newKorisnik.godinaRodjenjaProperty());
                fldPasswordRepeat.setText(fldPassword.getText());
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (newIme.length() > 2 && newIme.matches("[a-zA-Z- ]*")) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldPrezime, newPrezime) -> {
            if (newPrezime.length() > 2 && newPrezime.matches("[a-zA-Z- ]*")) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (newIme.matches("[a-zA-Z0-9.]*[a-zA-Z]@[a-zA-Z][a-zA-Z0-9.]*")) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (newIme.length() < 17 && newIme.matches("[a-zA-Z_$][a-zA-Z0-9_]*")) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if(model.getTrenutniKorisnik()==null)return;
            model.getTrenutniKorisnik().setPassword(newIme);
            if (!newIme.isEmpty() && newIme.equals(fldPasswordRepeat.getText()) && model.getTrenutniKorisnik().checkPassword()) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPasswordRepeat.textProperty().addListener((obs, oldIme, newIme) -> {
            if(model.getTrenutniKorisnik()==null)return;
            if (!newIme.isEmpty() && newIme.equals(fldPassword.getText()) && model.getTrenutniKorisnik().checkPassword()) {
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }

        });
    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void obrisiAction(ActionEvent actionEvent) {
        model.getKorisnici().remove(listKorisnici.getSelectionModel().getSelectedIndex());
    }

    public void generisiAction(ActionEvent actionEvent) {
        String generisanaLozinka = PasswordGenerator.generisi();
        fldPassword.setText(generisanaLozinka);
        fldPasswordRepeat.setText(generisanaLozinka);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vaša lozinka glasi " + generisanaLozinka);
        alert.showAndWait();
        if (model.getTrenutniKorisnik() == null || fldIme.getText().equals("") || fldPrezime.getText().equals("")) return;
        String prvoSlovo = String.valueOf(fldIme.getText().charAt(0)).toLowerCase();
        String prezime = fldPrezime.getText().toLowerCase();
        prvoSlovo = prvoSlovo.replace('č', 'c');
        prvoSlovo = prvoSlovo.replace('ć', 'c');
        prvoSlovo = prvoSlovo.replace('š', 's');
        prvoSlovo = prvoSlovo.replace('ž', 'z');
        prvoSlovo = prvoSlovo.replace('đ', 'd');
        prezime = prezime.replace('č', 'c');
        prezime = prezime.replace('ć', 'c');
        prezime = prezime.replace('š', 's');
        prezime = prezime.replace('ž', 'z');
        prezime = prezime.replace('đ', 'd');
        fldUsername.setText(prvoSlovo + prezime);
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
