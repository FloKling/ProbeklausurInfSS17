package probeklausur;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by floriankling on 20.07.17.
 */
public class Auktion {

    public static double increment = 1.0;
    private Ware ware;
    private Gebot gebot;
    private double aktuellerPreis = 0.0;
    private Calendar zeit = Calendar.getInstance();
    private Auktionshaus auktionshaus;

    public Auktion(Ware ware, int dauer) {
        this.ware = ware;
        zeit.setTimeInMillis(System.currentTimeMillis() + dauer * 60000);
    }

    public boolean gebotAbgeben(Gebot g) {
        logGebot(g);

        if (gebot == null && g.getBetrag() > increment) {
            gebot = g;
            aktuellerPreis = increment;
            auktionshaus.updateBieterterminals();
            return true;
        }
        if (g.getBetrag() < (gebot.getBetrag() + increment)) {
            return false;
        }
        if (gebot == null) {
            aktuellerPreis = increment;
            gebot = g;
            auktionshaus.updateBieterterminals();
            return true;
        }
        if (g.getBieter().equals(gebot.getBieter())) {
            gebot.setBetrag(g.getBetrag());
            aktuellerPreis = g.getBetrag();
            auktionshaus.updateBieterterminals();
            return true;
        }
        if (g.getBetrag() >= aktuellerPreis + increment && g.getBetrag() < gebot.getBetrag() + increment) {
            aktuellerPreis = g.getBetrag() + increment;
            auktionshaus.updateBieterterminals();
            return true;
        }
        if (g.getBetrag() >= aktuellerPreis + increment && g.getBetrag() > aktuellerPreis) {
            gebot = g;
            aktuellerPreis = aktuellerPreis + increment;
            auktionshaus.updateBieterterminals();
            return true;
        }

        return false;
    }

    private void logGebot(Gebot g) {
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream("auktionen.txt", true))) {
            printWriter.append("[" + Calendar.getInstance().getTime().toString() + "]").append(" Gebot von " + g.getBieter().getFullName() + " für " + ware.getTitel() + ": " + g.getBetrag() + " Euro").append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ware getWare() {
        return ware;
    }

    public void setWare(Ware ware) {
        this.ware = ware;
    }

    public Gebot getGebot() {
        return gebot;
    }

    public void setGebot(Gebot gebot) {
        this.gebot = gebot;
    }

    public double getAktuellerPreis() {
        return aktuellerPreis;
    }

    public void setAktuellerPreis(double aktuellerPreis) {
        this.aktuellerPreis = aktuellerPreis;
    }

    public Calendar getZeit() {
        return zeit;
    }

    public void setZeit(Calendar zeit) {
        this.zeit = zeit;
    }

    public Auktionshaus getAuktionshaus() {
        return auktionshaus;
    }

    public void setAuktionshaus(Auktionshaus auktionshaus) {
        this.auktionshaus = auktionshaus;
    }
}


