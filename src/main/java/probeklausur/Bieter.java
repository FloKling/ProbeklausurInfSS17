package probeklausur;

/**
 * Created by floriankling on 20.07.17.
 */
public class Bieter {

    private String vorname;
    private String nachname;

    public Bieter(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getFullName() {
        return vorname + " " + nachname;
    }
}
