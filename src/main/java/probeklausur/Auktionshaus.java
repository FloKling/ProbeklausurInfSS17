package probeklausur;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by floriankling on 20.07.17.
 */
public class Auktionshaus {

    private List<Auktion> auktionen;
    private List<BieterTerminal> bieterTerminals = new ArrayList<>();


    void addAuktion(Auktion a) {
        a.setAuktionshaus(this);
        if (auktionen == null)
            auktionen = new ArrayList<>();
        auktionen.add(a);
    }

    void removeAuktion(Auktion a) {
        if (auktionen.contains(a)) {
            auktionen.remove(a);
        }
    }

    public List<Auktion> getAuktionen() {
        return auktionen;
    }

    void register(BieterTerminal bt) {
        if (bieterTerminals == null)
            bieterTerminals = new ArrayList<>();
        this.bieterTerminals.add(bt);
    }

    void unregister(BieterTerminal bt) {
        this.bieterTerminals.remove(bt);
    }

    void updateBieterterminals() {
        for (BieterTerminal bt : bieterTerminals) {
            bt.repaint();
        }
    }

    public void addTerminal(BieterTerminal bieterTerminal) {
        bieterTerminals.add(bieterTerminal);
    }
}
