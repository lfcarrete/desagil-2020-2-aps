package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;


public class GateView extends FixedPanel implements ActionListener, MouseListener {


    private final Gate gate;

    private final JCheckBox[] inputs;
    private final Switch[] switches;

    private final Light light;
    private final Image image;
    private Color color;

    public GateView(Gate gate) {
        super(500, 245);

        this.gate = gate;

        inputs = new JCheckBox[gate.getInputSize()];
        switches = new Switch[gate.getInputSize()];
        light = new Light(255, 0, 0);
        light.connect(0, gate);

        int i = 0;
        int pos = 67;
        if (gate.getInputSize() == 1) {
            inputs[0] = new JCheckBox();
            switches[0] = new Switch();
            add(inputs[0], 120, 92, 20, 20);
            gate.connect(i, switches[i]);
        } else {
            while (i < gate.getInputSize()) {
                inputs[i] = new JCheckBox();
                switches[i] = new Switch();
                add(inputs[i], 120, pos, 20, 20);
                gate.connect(i, switches[i]);
                pos += 55;
                i++;
            }
        }


        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        color = Color.BLACK;

        addMouseListener(this);


        for (JCheckBox box : inputs) {
            box.addActionListener(this);
        }


        update();
    }

    private void update() {

        try {

            int i = 0;
            while (i < gate.getInputSize()) {
                if (inputs[i].isSelected()) {
                    switches[i].turnOn();
                } else {
                    switches[i].turnOff();
                }

                i++;
            }


        } catch (NumberFormatException exception) {

            return;
        }
        color = light.getColor();
        repaint();

    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 100, -40, 300, 300, this);

        // Desenha um quadrado cheio.
        g.setColor(color);
        g.fillOval(320, 92, 25, 25);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // Descobre em qual posição o clique ocorreu.
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        double deltaX = x - 332.5;
        double deltaY = y - 104.5;
        double dX = Math.pow(deltaX, 2);
        double dY = Math.pow(deltaY, 2);

        double modulo = Math.pow(dY + dX, (0.5));

        // Se o clique foi dentro do quadrado colorido...
        if (modulo < 12.5) {

            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, color);
            light.setColor(color);

            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
