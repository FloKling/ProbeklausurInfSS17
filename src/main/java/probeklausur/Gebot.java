package probeklausur;

/**
 * Created by floriankling on 20.07.17.
 */
public class Gebot {

    private double betrag;
    private Bieter bieter;

    public Gebot(double betrag, Bieter bieter) {
        this.betrag = betrag;
        this.bieter = bieter;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public Bieter getBieter() {
        return bieter;
    }

    public void setBieter(Bieter bieter) {
        this.bieter = bieter;
    }
}
