package probeklausur;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by floriankling on 20.07.17.
 */
public class BieterTerminal extends JFrame implements Runnable {
    private Bieter bieter;
    private Auktionshaus auktionshaus;
    private JLabel timeLabel;

    public BieterTerminal(Bieter bieter, Auktionshaus auktionshaus) {
        auktionshaus.addTerminal(this);
        this.bieter = bieter;
        this.auktionshaus = auktionshaus;
        paint(null);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void paint(Graphics g) {

        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        JPanel timePanel = new JPanel();

        masterPanel.add(timePanel, BorderLayout.NORTH);
        masterPanel.add(panel, BorderLayout.CENTER);
        timePanel.setLayout(new GridLayout(1, 1));
        timeLabel = new JLabel(Calendar.getInstance().getTime().toString());
        timePanel.add(timeLabel);
        add(masterPanel);

        setTitle(bieter.getFullName());

        panel.setLayout(new GridLayout(auktionshaus.getAuktionen().size(), 5));

        for (Auktion auktion : auktionshaus.getAuktionen()) {
            panel.add(new JLabel(auktion.getWare().getTitel()));
            panel.add(new JLabel(String.valueOf(auktion.getAktuellerPreis())));
            panel.add(new JLabel(auktion.getGebot() == null ? "---" : auktion.getGebot().getBieter().getFullName()));
            panel.add(new JLabel(auktion.getZeit().getTime().toString()));

            JButton bietenButton = new JButton("Gebot");


            bietenButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addGebot(auktion, bieter);
                }
            });
            panel.add(bietenButton);
        }


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, auktionshaus.getAuktionen().size() * 35 + 50);
        setVisible(true);
        setResizable(true);
    }


    private void addGebot(Auktion auktion, Bieter bieter) {
        String message = "Bitte neues Gebot eingeben." + System.lineSeparator() + "Mindestens " + auktion.getAktuellerPreis() + " Euro";
        String inputValue = JOptionPane.showInputDialog(this, message, auktion.getAktuellerPreis());
        double gebot = 0.0;
        try {
            gebot = Double.parseDouble(inputValue);
        } catch (NumberFormatException e) {

        }

        Calendar calendar = GregorianCalendar.getInstance();

        if (calendar.getTime().after(auktion.getZeit().getTime())) {
            JOptionPane.showMessageDialog(this, "Die Auktion ist leider schon vorbei");
            return;
        }

        boolean valid = auktion.gebotAbgeben(new Gebot(gebot, bieter));
        System.out.println(valid);
        if (valid) {
            JOptionPane.showMessageDialog(this, "Sie sind höchstbietender");
        } else {
            JOptionPane.showMessageDialog(this, "Gebot zu gering");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeLabel.setText(Calendar.getInstance().getTime().toString());
        }
    }
}
