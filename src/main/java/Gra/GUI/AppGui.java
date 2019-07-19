package Gra.GUI;

import Gra.Gra;
import Gra.Swiat.Swiat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AppGui extends JFrame implements ActionListener {

    private JButton start;
    private JButton koniec;
    private Gra gra;

    private JPanel panel = new JPanel();
//    private final String iconImage = this.getClass().getClassLoader().getResource("ico.png").toString();

    public AppGui(Gra game) {
        super ("Wirtualny Swiat");
        this.gra = game;
        createWindow();
        initComponents();
    }

    public AppGui(Swiat swiat, Gra game) {
        super ("Wirtualny Swiat");
        createWindow();
        gameComponents();

    }

    private void createWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize((int) screenSize.getWidth(), (int) screenSize.getHeight() - 50);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }

    private void startGame() {
        new AppGui(new Swiat(this.gra), this.gra);
    }

    private void exitApp() {
        System.exit(0);
    }

    private void drawBgInit(GridBagConstraints c) {
        ImageIcon image = new ImageIcon("ico.png");
        JLabel background = new JLabel("", image, JLabel.CENTER);
        background.setBounds(0,0, image.getIconWidth(), image.getIconHeight());
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(50,0,0,20);
        add(background, c);
        JLabel text = new JLabel("Wirtualny Swiat by Paweł Mikos");
        text.setBounds(0,0,0,50);
        c.gridx = 1;
        c.gridy = 0;
        add(text,c);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        drawBgInit(c);

        start = new JButton("Start Gry");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(150,0,0,0);


        add(start, c);
        start.addActionListener(this);

        koniec = new JButton("Wyjście");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(250,0,0,0);


        add(koniec, c);
        koniec.addActionListener(this);
    }



    private void gameComponents() {

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == start) {
            this.dispose();
            startGame();
        } else if (source == koniec) {
            exitApp();
        }
    }
}
