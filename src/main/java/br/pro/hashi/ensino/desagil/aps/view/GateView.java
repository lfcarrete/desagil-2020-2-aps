package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GateView extends JPanel implements ActionListener {


    private final Gate gate;
    private final Switch switch1;
    private final Switch switch2;

    private final JCheckBox box1;
    private final JCheckBox box2;

    private final JCheckBox resultBox;

    public GateView(Gate gate) {
        this.gate = gate;

        switch1 = new Switch();
        switch2 = new Switch();

        box1 = new JCheckBox();
        box2 = new JCheckBox();
        resultBox = new JCheckBox();

        JLabel entradaLabel = new JLabel("Entrada:");

        add(entradaLabel);

        if (gate.getInputSize() > 1) {
            add(box1);
            add(box2);
        } else {
            add(box1);
        }


        JLabel resultLabel = new JLabel("Saida:");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(resultLabel);

        box1.addActionListener(this);
        box2.addActionListener(this);

        add(resultBox);
        resultBox.setEnabled(false);


        update();
    }

    private void update() {

        try {
            if (gate.getInputSize() > 1) {
                if (box1.isSelected()) {
                    switch1.turnOn();
                } else {
                    switch1.turnOff();
                }
                if (box2.isSelected()) {
                    switch2.turnOn();
                } else {
                    switch2.turnOff();
                }
                gate.connect(0, switch1);
                gate.connect(1, switch2);

            } else {
                if (box1.isSelected()) {
                    switch1.turnOn();
                } else {
                    switch1.turnOff();
                }
                gate.connect(0, switch1);
            }


        } catch (NumberFormatException exception) {

            resultBox.setSelected(false);
            return;
        }

        boolean result = gate.read();


        resultBox.setSelected(result);
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}
