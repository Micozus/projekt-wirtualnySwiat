package Gra.GUI;


import Gra.Gra;
import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Swiat;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class AppGui extends JFrame implements ActionListener, PropertyChangeListener {

    private JButton start;
    private JButton koniec;
    private Gra gra;
    private Swiat swiat;

    private JButton goDown;
    private JButton goUp;
    private JButton goRight;
    private JButton goLeft;
    private JButton niesmiertelnoscButton;
    private JButton magicznyEliksirButton;
    private JButton calopalenieButton;
    private JButton tarczaAlzuraButton;
    private JButton szybkoscAntylopy;
    private JButton koniecTuryButton;
    private JTextField zwierzetaCountField;
    private JTextField roslinyCountField;
    private JTextField turaCountField;
    private JTextArea logsTextArea;
    private JScrollPane logsScrollArea;
    private JButton koniecGryButton;
    private JPanel rightSide;
    private JLayeredPane gamePane;
    private JPanel rightStatisticsPane;
    private JLabel turaCountLabel;
    private JLabel zwierzetaCountLabel;
    private JLabel roslinyCountLabel;
    private JPanel MainPane;
    private JPanel rightTopPane;
    private JPanel rightSteeringPane;
    private JPanel spacerPane;
    private JPanel midSpacerPane;
    private JPanel gameMapGrid;
    private JLabel gameMapBG;


    public AppGui(Gra game) {
        super("Wirtualny Swiat");
        this.gra = game;
        createWindow();
        initComponents();

    }

    public AppGui(Swiat swiat, Gra game) {
        super("Wirtualny Swiat");
        this.swiat = swiat;
        this.gra = game;
        createWindow();
        gameComponents();

    }

    private void createWindow() {
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setSize((int) screenSize.getWidth() /2, (int) (screenSize.getHeight() - 50) /3);
//        setSize(1104, 830);
        setBounds(100,100,1204,830);
        setMinimumSize(new Dimension(1204, 830));
        setMaximumSize(new Dimension(1204, 830));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }

    private void startGame() {

        new AppGui(new Swiat(this.gra), this.gra);
    }

    private void exitApp() {
        this.dispose();
        System.exit(0);
    }

    private void drawBgInit(GridBagConstraints c) {
        ImageIcon image = new ImageIcon("ico.png");

        JLabel background = new JLabel("", image, JLabel.CENTER);
        background.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(50, 0, 0, 20);
        add(background, c);
        JLabel text = new JLabel("Wirtualny Swiat by Paweł Mikos");
        text.setBounds(0, 0, 0, 50);
        c.gridx = 1;
        c.gridy = 0;
        add(text, c);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        drawBgInit(c);

        start = new JButton("Start Gry");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(150, 0, 0, 0);

        add(start, c);
        start.addActionListener(this);

        koniec = new JButton("Wyjście");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(250, 0, 0, 0);

        add(koniec, c);
        koniec.addActionListener(this);
    }

    private void createGameWindowUI() {
        MainPane = new JPanel();
        MainPane.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

        MainPane.setEnabled(true);
        MainPane.setFocusable(false);
        MainPane.setInheritsPopupMenu(false);
        MainPane.setVisible(true);

        ImageIcon image = new ImageIcon("mapa544x768.png");
        gameMapBG = new JLabel("", image, JLabel.CENTER);
        gameMapBG.setBounds(10, 10, 544, 768);


        gamePane = new JLayeredPane();



        gameMapGrid = new JPanel();
        gameMapGrid.setBounds(10, 10, 544, 768);
        gamePane.add(gameMapGrid);

        gameMapGrid.setLayout(new GridLayoutManager(24, 17, new Insets(0, 0, 0, 0), -1, -1));
        gameMapGrid.setBackground(new Color(0,0,0,0));
        gamePane.add(gameMapBG);
        MainPane.add(gamePane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));


        rightSide = new JPanel();
        rightSide.setBorder(new EmptyBorder(20, 0, 0, 30));
        rightSide.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        MainPane.add(rightSide, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

        // Logi Top Panel

        rightTopPane = new JPanel();
        rightTopPane.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        rightSide.add(rightTopPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        logsTextArea = new JTextArea(5, 20);
        logsTextArea.setEditable(false);
        logsScrollArea = new JScrollPane(logsTextArea);


        rightTopPane.add(logsScrollArea, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 200), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Logi");
        rightTopPane.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        koniecGryButton = new JButton();
        koniecGryButton.setFocusable(false);
        koniecGryButton.setBackground(new Color(0xA06600));
        koniecGryButton.setIcon(new ImageIcon("door.png"));
        rightTopPane.add(koniecGryButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        // Statystyki - Liczniki

        rightStatisticsPane = new JPanel();
        rightStatisticsPane.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        rightSide.add(rightStatisticsPane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK, new Dimension(-1, 150), null, null, 0, false));
        zwierzetaCountField = new JTextField();
        zwierzetaCountField.setBackground(new Color(-65793));
        zwierzetaCountField.setEditable(false);
        rightStatisticsPane.add(zwierzetaCountField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        roslinyCountField = new JTextField();
        roslinyCountField.setBackground(new Color(-65793));
        roslinyCountField.setEditable(false);
        rightStatisticsPane.add(roslinyCountField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        turaCountField = new JTextField();
        turaCountField.setEditable(false);
        turaCountField.setBackground(new Color(-65793));
        rightStatisticsPane.add(turaCountField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        turaCountLabel = new JLabel();
        turaCountLabel.setAlignmentX(0.0f);
        turaCountLabel.setText("Tura");
        turaCountLabel.setVisible(true);
        rightStatisticsPane.add(turaCountLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 5, false));

        zwierzetaCountLabel = new JLabel();
        zwierzetaCountLabel.setAlignmentX(0.0f);
        zwierzetaCountLabel.setText("Zwierzęta");
        zwierzetaCountLabel.setVisible(true);
        rightStatisticsPane.add(zwierzetaCountLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 5, false));

        roslinyCountLabel = new JLabel();
        roslinyCountLabel.setAlignmentX(0.0f);
        roslinyCountLabel.setText("Rośliny");
        roslinyCountLabel.setVisible(true);
        rightStatisticsPane.add(roslinyCountLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 5, false));

        // Panel Sterowania - Umiejetnosci

        rightSteeringPane = new JPanel();
        rightSteeringPane.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        rightSide.add(rightSteeringPane, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));

        magicznyEliksirButton = new JButton();
        magicznyEliksirButton.setFocusable(false);
        magicznyEliksirButton.setBackground(new Color(0x62735B));
        Font magicznyEliksirButtonFont = this.$$$getFont$$$(-1, 11, magicznyEliksirButton.getFont());
        if (magicznyEliksirButtonFont != null) magicznyEliksirButton.setFont(magicznyEliksirButtonFont);
        magicznyEliksirButton.setText("Magiczny Eliksir");
        magicznyEliksirButton.setIcon(new ImageIcon("potion.png"));
        magicznyEliksirButton.setForeground(new Color(-65793));
        rightSteeringPane.add(magicznyEliksirButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        niesmiertelnoscButton = new JButton();
        niesmiertelnoscButton.setFocusable(false);
        niesmiertelnoscButton.setBackground(new Color(0x62735B));
        Font niesmiertelnoscButtonFont = this.$$$getFont$$$(-1, 11, niesmiertelnoscButton.getFont());
        if (niesmiertelnoscButtonFont != null) niesmiertelnoscButton.setFont(niesmiertelnoscButtonFont);
        niesmiertelnoscButton.setText("Nieśmiertelność");
        niesmiertelnoscButton.setIcon(new ImageIcon("strength.png"));
        niesmiertelnoscButton.setForeground(new Color(-65793));
        rightSteeringPane.add(niesmiertelnoscButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        szybkoscAntylopy = new JButton();
        szybkoscAntylopy.setFocusable(false);
        szybkoscAntylopy.setBackground(new Color(0x62735B));
        Font szybkoscAntylopyButtonFont = this.$$$getFont$$$(-1, 11, szybkoscAntylopy.getFont());
        if (szybkoscAntylopyButtonFont != null) szybkoscAntylopy.setFont(szybkoscAntylopyButtonFont);
        szybkoscAntylopy.setText("Szybkość antylopy");
        szybkoscAntylopy.setIcon(new ImageIcon("running.png"));
        szybkoscAntylopy.setForeground(new Color(-65793));
        rightSteeringPane.add(szybkoscAntylopy, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        calopalenieButton = new JButton();
        calopalenieButton.setFocusable(false);
        calopalenieButton.setBackground(new Color(0x62735B));
        Font calopalenieButtonFont = this.$$$getFont$$$(-1, 11, calopalenieButton.getFont());
        if (calopalenieButton != null) calopalenieButton.setFont(calopalenieButtonFont);
        calopalenieButton.setText("Całopalenie");
        calopalenieButton.setIcon(new ImageIcon("fire.png"));
        calopalenieButton.setForeground(new Color(-65793));
        rightSteeringPane.add(this.calopalenieButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        tarczaAlzuraButton = new JButton();
        tarczaAlzuraButton.setBackground(new Color(0x62735B));
        Font tarczaAlzuraButtonFont = this.$$$getFont$$$(-1, 11, tarczaAlzuraButton.getFont());
        if (tarczaAlzuraButtonFont != null) tarczaAlzuraButton.setFont(tarczaAlzuraButtonFont);
        tarczaAlzuraButton.setText("Tarcza Alzura");
        tarczaAlzuraButton.setFocusable(false);
        tarczaAlzuraButton.setIcon(new ImageIcon("shield.png"));
        tarczaAlzuraButton.setForeground(new Color(-65793));
        rightSteeringPane.add(tarczaAlzuraButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        koniecTuryButton = new JButton();
        koniecTuryButton.setFocusable(false);
        koniecTuryButton.setBackground(new Color(-9012176));
        koniecTuryButton.setBorderPainted(true);
        koniecTuryButton.setContentAreaFilled(true);
        Font koniecTuryButtonFont = this.$$$getFont$$$(-1, 12, koniecTuryButton.getFont());
        if (koniecTuryButtonFont != null) koniecTuryButton.setFont(koniecTuryButtonFont);
        koniecTuryButton.setText("Następna Tura");
        koniecTuryButton.setForeground(new Color(-65793));
        koniecTuryButton.setIcon(new ImageIcon("next.png"));
        rightSteeringPane.add(koniecTuryButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        // Panel Sterowania

        goLeft = new JButton();
        goLeft.setFocusable(false);
        goLeft.setBackground(new Color(-12682101));
        Font goLeftFont = this.$$$getFont$$$(Font.BOLD, 22, goLeft.getFont());
        if (goLeftFont != null) goLeft.setFont(goLeftFont);
        goLeft.setHorizontalTextPosition(SwingConstants.CENTER);
        goLeft.setIconTextGap(0);
        goLeft.setOpaque(true);
        goLeft.setRequestFocusEnabled(true);
        goLeft.setIcon(new ImageIcon("previous.png"));
        goLeft.setVerticalTextPosition(SwingConstants.CENTER);
        rightSteeringPane.add(goLeft, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        goRight = new JButton();
        goRight.setFocusable(false);
        goRight.setBackground(new Color(-12682101));
        Font goRightFont = this.$$$getFont$$$(Font.BOLD, 22, goRight.getFont());
        if (goRightFont != null) goRight.setFont(goRightFont);
        goRight.setHorizontalTextPosition(SwingConstants.CENTER);
        goRight.setIconTextGap(0);
        goRight.setOpaque(true);
        goRight.setRequestFocusEnabled(true);
        goRight.setIcon(new ImageIcon("next-turn.png"));
        goRight.setVerticalTextPosition(SwingConstants.CENTER);
        rightSteeringPane.add(goRight, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        goDown = new JButton();
        goDown.setFocusable(false);
        goDown.setBackground(new Color(-12682101));
        Font goDownFont = this.$$$getFont$$$(Font.BOLD, 22, goDown.getFont());
        if (goDownFont != null) goDown.setFont(goDownFont);
        goDown.setHorizontalTextPosition(SwingConstants.CENTER);
        goDown.setIconTextGap(0);
        goDown.setOpaque(true);
        goDown.setRequestFocusEnabled(true);
        goDown.setIcon(new ImageIcon("down-arrow.png"));
        goDown.setVerticalTextPosition(SwingConstants.CENTER);
        rightSteeringPane.add(goDown, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final Spacer spacer2 = new Spacer();
        rightSteeringPane.add(spacer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        goUp = new JButton();
        goUp.setFocusable(false);
        goUp.setBackground(new Color(-12682101));
        Font goUpFont = this.$$$getFont$$$(Font.BOLD, 22, goUp.getFont());
        if (goUpFont != null) goUp.setFont(goUpFont);
        goUp.setHorizontalTextPosition(SwingConstants.CENTER);
        goUp.setIconTextGap(0);
        goUp.setOpaque(true);
        goUp.setRequestFocusEnabled(true);
        goUp.setIcon(new ImageIcon("up-arrow.png"));
        goUp.setVerticalTextPosition(SwingConstants.CENTER);
        rightSteeringPane.add(goUp, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        // Space'ry

        final Spacer spacer3 = new Spacer();
        rightSteeringPane.add(spacer3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        final Spacer spacer4 = new Spacer();
        rightSteeringPane.add(spacer4, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        spacerPane = new JPanel();
        spacerPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rightSteeringPane.add(spacerPane, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 50), null, null, 0, false));

        midSpacerPane = new JPanel();
        midSpacerPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rightSteeringPane.add(midSpacerPane, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 50), null, null, 0, false));

        final Spacer spacer5 = new Spacer();
        rightSide.add(spacer5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        rightSide.add(spacer6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

    }

    private void addListenersGameUI() {
        koniecGryButton.addActionListener(this);
        koniecTuryButton.addActionListener(this);
    }

    private void gameComponents() {
        createGameWindowUI();
        addListenersGameUI();
        this.add(MainPane);
        populateStatistics();
        logsTextArea.append("START GRY\n");
        logsTextArea.append("-------------------\n");
    }


    private Font $$$getFont$$$(int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        Font testFont = new Font("Consolas", Font.PLAIN, 10);
        if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
            resultName = "Consolas";
        } else {
            resultName = currentFont.getName();
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    public JComponent $$$getRootComponent$$$() {
        return MainPane;
    }

    private void populateStatistics() {
        roslinyCountField.setText(String.valueOf(gra.getIloscOrganizmowRoslinnych()));
        zwierzetaCountField.setText(String.valueOf(gra.getIloscOrganizmowZwierzecych()));
        turaCountField.setText(String.valueOf(gra.getTura()));
    }

    private void wykonajTure() {
        List<Organizm> listaOrganizmow = swiat.getMapaobiektow()
                .values()
                .stream()
                .sorted(Comparator.comparing(Organizm::getInicjatywa, Comparator.reverseOrder())
                        .thenComparing(Organizm::getWiek, Comparator.reverseOrder())
                )
                .collect(Collectors.toCollection(LinkedList::new));
        for (int i = 0; i < listaOrganizmow.size(); i++) {
            listaOrganizmow.get(i).checkAction();
        }
        swiat.iloscOrganizmow(this.gra);
        this.gra.setTura(this.gra.getTura() + 1);
    }

    private void eventTura() {
        this.wykonajTure();
        populateStatistics();
        Set<Logi> logiSet = gra.getLogSet();
        for (Logi logi : logiSet) {
            if (logi.getTura() == gra.getTura() - 1) {
                logsTextArea.append("Tura " + logi.getTura() + ": " + logi.toString());
                logsTextArea.append("\n");
            }
        }
        if (swiat.czyOstatniaTura(gra)) {
            koniecTuryButton.setEnabled(false);
            logsTextArea.append("-------------------\n");
            logsTextArea.append("KONIEC GRY");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == start) {
            this.dispose();
            startGame();
        } else if (source == koniec || source == koniecGryButton) {
            exitApp();
        } else if (source == koniecTuryButton) {
            eventTura();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
