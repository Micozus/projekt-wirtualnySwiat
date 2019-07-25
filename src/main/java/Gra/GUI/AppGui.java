package Gra.GUI;


import Gra.Gra;
import Gra.Logi;
import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Organizm.Zwierzeta.Gatunki.Czlowiek;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;


import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class AppGui extends JFrame implements ActionListener {

    private JButton start;
    private JButton koniec;
    private Gra gra;
    private Swiat swiat;
    private Set<EventTrigger> setTriggerow = new LinkedHashSet<>();
    private boolean startGame = true;

    private JButton goDown;
    private JButton goUp;
    private JButton goRight;
    private JButton goLeft;
    private JButton niesmiertelnoscButton;
    private JButton magicznyEliksirButton;
    private JButton calopalenieButton;
    private JButton tarczaAlzuraButton;
    private JButton szybkoscAntylopy;
    private JButton nastepnyOrganizm;
    private JButton actionListenButton;
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
    private JLayeredPane gameMapGrid;
    private JLabel gameMapBG;
    private Timer timer;
    private Organizm tempOrganizm;
    private TypAnimacji tempTypAnimacji;

    public Gra getGra() {
        return gra;
    }

//    public AppGui(Gra game) {
//        super("Wirtualny Swiat");
//        this.gra = game;
//        createWindow();
//        initComponents();
//
//    }
    //    private void drawBgInit(GridBagConstraints c) {
//        ImageIcon image = new ImageIcon("pic/ico.png");
//        JLabel background = new JLabel("", image, JLabel.CENTER);
//        background.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
//        c.fill = GridBagConstraints.CENTER;
//        c.insets = new Insets(50, 0, 0, 20);
//        add(background, c);
//        JLabel text = new JLabel("Wirtualny Swiat by Paweł Mikos");
//        text.setBounds(0, 0, 0, 50);
//        c.gridx = 1;
//        c.gridy = 0;
//        add(text, c);
//    }

//    private void initComponents() {
//        setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        drawBgInit(c);
//
//        start = new JButton("Start Gry");
//        c.gridx = 1;
//        c.gridy = 0;
//        c.insets = new Insets(150, 0, 0, 0);
//
//        add(start, c);
//        start.addActionListener(this);
//
//        koniec = new JButton("Wyjście");
//        c.gridx = 1;
//        c.gridy = 0;
//        c.insets = new Insets(250, 0, 0, 0);
//
//        add(koniec, c);
//        koniec.addActionListener(this);
//    }

    public AppGui(Swiat swiat, Gra game) {
        super("Wirtualny Swiat");
        this.swiat = swiat;
        this.gra = game;
        createWindow();
        gameComponents();
    }

    public JLayeredPane getGameMapGrid() {
        return gameMapGrid;
    }

    private void createWindow() {
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setSize((int) screenSize.getWidth() /2, (int) (screenSize.getHeight() - 50) /3);
//        setSize(1104, 830);
        setBounds(50, 50, 1204, 830);
        setMinimumSize(new Dimension(1204, 830));
        setMaximumSize(new Dimension(1204, 830));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }

    public JButton getActionListenButton() {
        return actionListenButton;
    }

    private void startGame() {
        new AppGui(new Swiat(this.gra), this.gra);
    }

    private void exitApp() {
        this.dispose();
        System.exit(0);
    }

    private void populateInstanceImages() {
        Map<Organizm, InstanceImage> instancjeDoZaludnienia = this.gra.getMapaObrazow();
        for (Map.Entry<Organizm, InstanceImage> organizm : instancjeDoZaludnienia.entrySet()) {
            if (this.swiat.getMapaobiektow().containsKey(organizm.getKey().getPolozenie())) {
                organizm.getValue().newBounds(organizm.getKey().getPolozenie());
                gameMapGrid.add(organizm.getValue());
            }
        }

    }

    public void populateNewInstance(Organizm organizm) {
        Map<Organizm, InstanceImage> mapaObrazow = this.gra.getMapaObrazow();
        InstanceImage newInstance = mapaObrazow.get(organizm);
        newInstance.newBounds(organizm.getPolozenie());
        gameMapGrid.add(newInstance);
    }

    public void removeDeadInstance(Organizm organizm) {
        gameMapGrid.remove(organizm.getInstanceImage());
//        gra.getMapaObrazow().remove(organizm);
    }

    private void createGameWindowUI() {
        MainPane = new JPanel();
        MainPane.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        actionListenButton = new JButton();
        actionListenButton.setBounds(0, 0, 0, 0);

        MainPane.setEnabled(true);
        MainPane.setFocusable(false);
        MainPane.setInheritsPopupMenu(false);
        MainPane.setVisible(true);

        ImageIcon image = new ImageIcon("pic/mapa544x768.png");
        gameMapBG = new JLabel("", image, JLabel.CENTER);
        gameMapBG.setBounds(10, 10, 544, 768);


        gamePane = new JLayeredPane();

        gameMapGrid = new JLayeredPane();
        gameMapGrid.setBounds(10, 10, 544, 768);
        gamePane.add(gameMapGrid, JLayeredPane.DEFAULT_LAYER);
        gamePane.add(gameMapBG, JLayeredPane.DEFAULT_LAYER);
        gamePane.add(actionListenButton);

//        gameMapGrid.setLayout(new GridLayoutManager(24, 17, new Insets(0, 0, 0, 0), -1, -1));
        gameMapGrid.setBackground(new Color(0, 0, 0, 0));

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
        koniecGryButton.setIcon(new ImageIcon("pic/door.png"));
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
        magicznyEliksirButton.setIcon(new ImageIcon("pic/potion.png"));
        magicznyEliksirButton.setForeground(new Color(-65793));
        rightSteeringPane.add(magicznyEliksirButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        niesmiertelnoscButton = new JButton();
        niesmiertelnoscButton.setFocusable(false);
        niesmiertelnoscButton.setBackground(new Color(0x62735B));
        Font niesmiertelnoscButtonFont = this.$$$getFont$$$(-1, 11, niesmiertelnoscButton.getFont());
        if (niesmiertelnoscButtonFont != null) niesmiertelnoscButton.setFont(niesmiertelnoscButtonFont);
        niesmiertelnoscButton.setText("Nieśmiertelność");
        niesmiertelnoscButton.setIcon(new ImageIcon("pic/strength.png"));
        niesmiertelnoscButton.setForeground(new Color(-65793));
        rightSteeringPane.add(niesmiertelnoscButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        szybkoscAntylopy = new JButton();
        szybkoscAntylopy.setFocusable(false);
        szybkoscAntylopy.setBackground(new Color(0x62735B));
        Font szybkoscAntylopyButtonFont = this.$$$getFont$$$(-1, 11, szybkoscAntylopy.getFont());
        if (szybkoscAntylopyButtonFont != null) szybkoscAntylopy.setFont(szybkoscAntylopyButtonFont);
        szybkoscAntylopy.setText("Szybkość antylopy");
        szybkoscAntylopy.setIcon(new ImageIcon("pic/running.png"));
        szybkoscAntylopy.setForeground(new Color(-65793));
        rightSteeringPane.add(szybkoscAntylopy, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        calopalenieButton = new JButton();
        calopalenieButton.setFocusable(false);
        calopalenieButton.setBackground(new Color(0x62735B));
        Font calopalenieButtonFont = this.$$$getFont$$$(-1, 11, calopalenieButton.getFont());
        if (calopalenieButton != null) calopalenieButton.setFont(calopalenieButtonFont);
        calopalenieButton.setText("Całopalenie");
        calopalenieButton.setIcon(new ImageIcon("pic/fire.png"));
        calopalenieButton.setForeground(new Color(-65793));
        rightSteeringPane.add(this.calopalenieButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        tarczaAlzuraButton = new JButton();
        tarczaAlzuraButton.setBackground(new Color(0x62735B));
        Font tarczaAlzuraButtonFont = this.$$$getFont$$$(-1, 11, tarczaAlzuraButton.getFont());
        if (tarczaAlzuraButtonFont != null) tarczaAlzuraButton.setFont(tarczaAlzuraButtonFont);
        tarczaAlzuraButton.setText("Tarcza Alzura");
        tarczaAlzuraButton.setFocusable(false);
        tarczaAlzuraButton.setIcon(new ImageIcon("pic/shield.png"));
        tarczaAlzuraButton.setForeground(new Color(-65793));
        rightSteeringPane.add(tarczaAlzuraButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        nastepnyOrganizm = new JButton();
        nastepnyOrganizm.setFocusable(false);
        nastepnyOrganizm.setBackground(new Color(-9012176));
        nastepnyOrganizm.setBorderPainted(true);
        nastepnyOrganizm.setContentAreaFilled(true);
        Font koniecTuryButtonFont = this.$$$getFont$$$(-1, 12, nastepnyOrganizm.getFont());
        if (koniecTuryButtonFont != null) nastepnyOrganizm.setFont(koniecTuryButtonFont);
        nastepnyOrganizm.setText("Start");
        nastepnyOrganizm.setForeground(new Color(-65793));
        nastepnyOrganizm.setIcon(new ImageIcon("pic/next.png"));
        rightSteeringPane.add(nastepnyOrganizm, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

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
        goLeft.setIcon(new ImageIcon("pic/previous.png"));
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
        goRight.setIcon(new ImageIcon("pic/next-turn.png"));
        goRight.setVerticalTextPosition(SwingConstants.CENTER);
        rightSteeringPane.add(goRight, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        goDown = new JButton();
        goDown.setFocusable(false);
        goDown.setBackground(new Color(-12682101));
        Font goDownFont = this.$$$getFont$$$(Font.BOLD, 22, goDown.getFont());
        if (goDownFont != null) goDown.setFont(goDownFont);
        goDown.setHorizontalTextPosition(SwingConstants.CENTER);
        goDown.setIconTextGap(0);

        goDown.setIcon(new ImageIcon("pic/down-arrow.png"));
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
        goUp.setIcon(new ImageIcon("pic/up-arrow.png"));
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

    private void gameComponents() {
        createGameWindowUI();
        populateInstanceImages();
        addListenersGameUI();
        add(MainPane);
        populateStatistics();
        logsTextArea.append("START GRY\n");
        logsTextArea.append("-------------------\n");
    }

    private void addListenersGameUI() {
        koniecGryButton.addActionListener(this);
        nastepnyOrganizm.addActionListener(this);
        goDown.addActionListener(this);
        goUp.addActionListener(this);
        goLeft.addActionListener(this);
        goRight.addActionListener(this);
        tarczaAlzuraButton.addActionListener(this);
        calopalenieButton.addActionListener(this);
        niesmiertelnoscButton.addActionListener(this);
        szybkoscAntylopy.addActionListener(this);
        magicznyEliksirButton.addActionListener(this);
        actionListenButton.addActionListener(this);
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

    private ArrayList<Organizm> getSortedOrganismList() {
        return swiat.getMapaobiektow()
                .values()
                .stream()
                .sorted(Comparator.comparing(Organizm::getInicjatywa, Comparator.reverseOrder())
                        .thenComparing(Organizm::getWiek, Comparator.reverseOrder()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void wykonajTureDoCzlowieka() {
        nastepnyOrganizm.setEnabled(false);
        List<Organizm> listaOrganizmow = this.getSortedOrganismList();
        int kolejnoscCzlowieka = 0;
        for (int i = 0; i < listaOrganizmow.size(); i++) {
            if (listaOrganizmow.get(i).getClass().equals(Czlowiek.class)) {
                kolejnoscCzlowieka = i;
            }
        }
        for (int i = 0; i < kolejnoscCzlowieka; i++) {
            listaOrganizmow.get(i).checkAction();
        }
        actionListenButton.doClick();
    }

    private void wykonajTurePoCzlowieku() {
        nastepnyOrganizm.setEnabled(false);
        List<Organizm> listaOrganizmow = this.getSortedOrganismList();
        int kolejnoscCzlowieka = 0;
        for (int i = 0; i < listaOrganizmow.size(); i++) {
            if (listaOrganizmow.get(i).getClass().equals(Czlowiek.class)) {
                kolejnoscCzlowieka = i;
            }
        }
        for (int i = kolejnoscCzlowieka + 1; i < listaOrganizmow.size(); i++) {
            listaOrganizmow.get(i).checkAction();
        }
        this.gra.setTura(this.gra.getTura() + 1);
        actionListenButton.doClick();
        eventTura();
    }

    private void repaintWorldAndLogs() {
        gameMapGrid.repaint();
        swiat.iloscOrganizmow(this.gra);
        this.populateStatistics();
        Set<Logi> logiSet = gra.getLogSet();
        for (Logi logi : logiSet) {
            if (logi.getTura() == gra.getTura()) {
                logsTextArea.append("Tura " + logi.getTura() + ": " + logi.toString());
                logsTextArea.append("\n");
            }
        }
        if (swiat.czyOstatniaTura(gra)) {
            nastepnyOrganizm.setEnabled(false);
            lockSteering();
            logsTextArea.append("-------------------\n");
            logsTextArea.append("KONIEC GRY");
            return;
        }
    }

    public JButton getGoDown() {
        return goDown;
    }

    public JButton getGoUp() {
        return goUp;
    }

    public JButton getGoRight() {
        return goRight;
    }

    public JButton getGoLeft() {
        return goLeft;
    }

    private void eventTura() {
        this.wykonajTureDoCzlowieka();
        this.repaintWorldAndLogs();
        this.swiat.getHumanPlayer().checkAction();

    }

    public void addTriggerAnimation(TypAnimacji typAnimacji, Organizm organizm) {
        if (organizm.getClass().getSuperclass().equals(Zwierze.class)) {
            this.setTriggerow.add(new EventTrigger(this.gra.getTura(), new TriggerAnimation(getGra().getAppGui(), typAnimacji, organizm)));
        } else if ((typAnimacji.equals(TypAnimacji.FADEOUT) || (typAnimacji.equals(TypAnimacji.FADEIN)) && organizm.getClass().getSuperclass().equals(Roslina.class))) {
            this.setTriggerow.add(new EventTrigger(this.gra.getTura(), new TriggerAnimation(getGra().getAppGui(), typAnimacji, organizm)));
        }
    }

    public void addTriggerAnimation(TypAnimacji typAnimacji, Organizm organizm, Lokalizacja miejsceUcieczki) {
        this.setTriggerow.add(new EventTrigger(this.gra.getTura(), new TriggerAnimation(getGra().getAppGui(), typAnimacji, organizm, miejsceUcieczki)));
    }

    private void animateIcon(TypAnimacji typAnimacji, Component iconToAnimate) {
        switch (typAnimacji) {
            case FADEIN:
                iconShow(iconToAnimate);
                break;
            case FADEOUT:
                iconHide(iconToAnimate);
                break;
//            case MOVEUP:
//                moveIconUp(iconToAnimate);
//                break;
//            case MOVEDOWN:
//                moveIconDown(iconToAnimate);
//                break;
//            case MOVELEFT:
//                moveIconLeft(iconToAnimate);
//                break;
//            case MOVERIGHT:
//                moveIconRight(iconToAnimate);
//                break;
        }
        iconToAnimate.repaint();
    }

    private void animateIcon(TypAnimacji typAnimacji, Component iconToAnimate, Lokalizacja nowaLokalizacja) {
        switch (typAnimacji) {
            case MOVE:
                moveIcon((InstanceImage) iconToAnimate, nowaLokalizacja);
                break;
            case UCIECZKA:
                moveIconRunaway((InstanceImage) iconToAnimate, nowaLokalizacja);
                break;
        }
        iconToAnimate.repaint();
    }

    private void moveIcon(InstanceImage component, Lokalizacja nowaLokalizacja) {
        component.newBounds(nowaLokalizacja);
    }

    private void moveIconRunaway(InstanceImage component, Lokalizacja miejsceUcieczki) {
        component.newBounds(miejsceUcieczki);
    }

    private void iconHide(Component component) {
        component.setVisible(false);
    }

    private void iconShow(Component component) {
        component.setVisible(true);
    }


    private Component findHumanComponent() {
        for (int i = 0; i < gameMapGrid.getComponents().length; i++) {
            if (gameMapGrid.getComponents()[i].equals(this.swiat.getHumanPlayer().getInstanceImage())) {
                return gameMapGrid.getComponents()[i];
            }
        }
        return null;
    }

    private void lockSteering() {
        goDown.setEnabled(false);
        goUp.setEnabled(false);
        goLeft.setEnabled(false);
        goRight.setEnabled(false);
        magicznyEliksirButton.setEnabled(false);
        niesmiertelnoscButton.setEnabled(false);
        tarczaAlzuraButton.setEnabled(false);
        calopalenieButton.setEnabled(false);
        szybkoscAntylopy.setEnabled(false);
    }

    public void unlockSpecialAbbility() {
        magicznyEliksirButton.setEnabled(true);
        magicznyEliksirButton.setText("Magiczny Eliksir");
        niesmiertelnoscButton.setEnabled(true);
        niesmiertelnoscButton.setText("Nieśmiertelność");
        tarczaAlzuraButton.setEnabled(true);
        tarczaAlzuraButton.setText("Tarcza Alzura");
        calopalenieButton.setEnabled(true);
        calopalenieButton.setText("Całopalenie");
        szybkoscAntylopy.setEnabled(true);
        szybkoscAntylopy.setText("Szybkość Antylopy");
    }

    public void lockSpecialAbbility(int cooldown) {
        magicznyEliksirButton.setEnabled(false);
        magicznyEliksirButton.setText("Cooldown " + cooldown);
        niesmiertelnoscButton.setEnabled(false);
        niesmiertelnoscButton.setText("Cooldown " + cooldown);
        tarczaAlzuraButton.setEnabled(false);
        tarczaAlzuraButton.setText("Cooldown " + cooldown);
        calopalenieButton.setEnabled(false);
        calopalenieButton.setText("Cooldown " + cooldown);
        szybkoscAntylopy.setEnabled(false);
        szybkoscAntylopy.setText("Cooldown " + cooldown);
    }

//    public void unlockSteering() {
//        goDown.setEnabled(true);
//        goUp.setEnabled(true);
//        goLeft.setEnabled(true);
//        goRight.setEnabled(true);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == start) {
            startGame();
        } else if (source == koniec || source == koniecGryButton) {
            exitApp();
        } else if (source == nastepnyOrganizm) {
            if (this.startGame == true) {
                lockSteering();
                nastepnyOrganizm.setText("Następny");
                this.startGame = false;
                eventTura();
            } else {
                lockSteering();
                wykonajTurePoCzlowieku();
            }
        } else if (source == goDown) {
            lockSteering();
//            moveIconDown(findHumanComponent());
            nastepnyOrganizm.setEnabled(true);
        } else if (source == goLeft) {
            lockSteering();
//            moveIconLeft(findHumanComponent());
            nastepnyOrganizm.setEnabled(true);
        } else if (source == goRight) {
            lockSteering();
//            moveIconRight(findHumanComponent());
            nastepnyOrganizm.setEnabled(true);
        } else if (source == goUp) {
            lockSteering();
//            moveIconUp(findHumanComponent());
            nastepnyOrganizm.setEnabled(true);
        } else if (source == actionListenButton) {
            for (EventTrigger eventTrigger : this.setTriggerow) {
                if (eventTrigger.getTura() == this.gra.getTura()) {
                    if (eventTrigger.getTriggerAnimation().getIconToAnimate() == null) {
                        InstanceImage backupIcon = eventTrigger.getTriggerAnimation().getOrganizm().getInstanceImage();
                        if (eventTrigger.getTriggerAnimation().getTypAnimacji().equals(TypAnimacji.UCIECZKA) || eventTrigger.getTriggerAnimation().getTypAnimacji().equals(TypAnimacji.MOVE)) {
                            animateIcon(eventTrigger.getTriggerAnimation().getTypAnimacji(), backupIcon, eventTrigger.getTriggerAnimation().getMiejsceUcieczki());
                        } else {
                            animateIcon(eventTrigger.getTriggerAnimation().getTypAnimacji(), backupIcon);
                            if (eventTrigger.getTriggerAnimation().getTypAnimacji().equals(TypAnimacji.FADEOUT)) {
                                removeDeadInstance(eventTrigger.getTriggerAnimation().getOrganizm());
                            }
                        }
                    } else {
                        if (eventTrigger.getTriggerAnimation().getTypAnimacji().equals(TypAnimacji.UCIECZKA) || eventTrigger.getTriggerAnimation().getTypAnimacji().equals(TypAnimacji.MOVE)) {
                            animateIcon(eventTrigger.getTriggerAnimation().getTypAnimacji(), eventTrigger.getTriggerAnimation().getIconToAnimate(), eventTrigger.getTriggerAnimation().getMiejsceUcieczki());
                        } else {
                            animateIcon(eventTrigger.getTriggerAnimation().getTypAnimacji(), eventTrigger.getTriggerAnimation().getIconToAnimate());
                            if (eventTrigger.getTriggerAnimation().getTypAnimacji().equals(TypAnimacji.FADEOUT)) {
                                removeDeadInstance(eventTrigger.getTriggerAnimation().getOrganizm());
                            }
                        }
                    }
                }
            }
            setTriggerow.clear();
        } else if (source == calopalenieButton) {
            this.swiat.getHumanPlayer().activateSpecialAbbility(SpecialAbbility.CALOPALENIE);
        } else if (source == niesmiertelnoscButton) {
            this.swiat.getHumanPlayer().activateSpecialAbbility(SpecialAbbility.NIESMIERTELNOSC);
        } else if (source == tarczaAlzuraButton) {
            this.swiat.getHumanPlayer().activateSpecialAbbility(SpecialAbbility.TARCZA_ALZURA);
        } else if (source == szybkoscAntylopy) {
            this.swiat.getHumanPlayer().activateSpecialAbbility(SpecialAbbility.SZYBKOSC_ANTYLOPY);
        } else if (source == magicznyEliksirButton) {
            this.swiat.getHumanPlayer().activateSpecialAbbility(SpecialAbbility.MAGICZNY_ELIKSIR);
        }
    }

}
