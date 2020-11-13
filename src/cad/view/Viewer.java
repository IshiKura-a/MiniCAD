package cad.view;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import cad.controller.*;

public class Viewer extends JFrame {
    private ArrayList<JMenu> menus;
    private ArrayList<JMenuItem> menuItems;
    private JPanel body;
    private JPanel canvus;
    private JPanel leftToolBar;
    private String helpString;

    JButton lineButton;
    JButton rectButton;
    JButton circleButton;
    JButton textButton;
    JButton paletteButton;

    public Viewer() {
        super();
        menus = new ArrayList<>();
        menuItems = new ArrayList<>();
        setJMenuBar(new JMenuBar());
        body = new JPanel();
        body.setLayout(new BorderLayout());
        leftToolBar = new JPanel();
        leftToolBar.setLayout(new GridLayout(5, 1));
        File f = new File("./blob/Help.html");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f))) {
            helpString = new String(bis.readAllBytes());
            helpString = helpString.replace('\n', '\0');
        } catch (IOException ioe) {
            System.out.println("Meet io exception.");
            helpString = "";
        }

        menus.add(new JMenu("File"));
        menus.add(new JMenu("Help"));

        menuItems.add(new JMenuItem("Open"));
        menuItems.add(new JMenuItem("Save"));
        menuItems.add(new JMenuItem("Exit"));

        menuItems.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("data file(.dat)", "dat");
                chooser.setFileFilter(filter);
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                int res = chooser.showOpenDialog(new JPanel());
                if (res == JFileChooser.APPROVE_OPTION) {
                    Controller.raiseOpenFileEvent(chooser.getSelectedFile());
                }
            }
        });
        menuItems.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser chooser = new JFileChooser() {
                    @Override
                    public void approveSelection() {
                        File savedFile = getSelectedFile();

                        if (!savedFile.getName().matches("(.dat)$")) {
                            savedFile = new File(savedFile.getAbsolutePath() + ".dat");
                            setSelectedFile(savedFile);
                        }

                        if (savedFile.exists()) {
                            int overwriteSelect = JOptionPane.showConfirmDialog(this,
                                    "<html><font size=3>文件" + savedFile.getName() + "已存在，是否覆盖?</font><html>", "是否覆盖?",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (overwriteSelect != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }
                        super.approveSelection();
                    }
                };
                FileNameExtensionFilter filter = new FileNameExtensionFilter("data file(.dat)", "dat");
                chooser.setFileFilter(filter);
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                int res = chooser.showSaveDialog(new JPanel());
                if (res == JFileChooser.APPROVE_OPTION) {
                    Controller.raiseSaveFileEvent(chooser.getSelectedFile());
                }
            }
        });
        menuItems.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        JMenuItem docItem = new JMenuItem("Document");
        menus.get(1).add(docItem);
        docItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showConfirmDialog(null, (Object) helpString, "Help", JOptionPane.CLOSED_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        for (JMenuItem jmi : menuItems) {
            menus.get(0).add(jmi);
        }

        for (JMenu jm : menus) {
            getJMenuBar().add(jm);
        }

        canvus = new JPanel() {
            @Override
            public void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                Controller.paintItems((Graphics2D) gr);
            }

            @Override
            public void paint(Graphics gr) {
                paintComponent(gr);
            }
        };

        // !!! JFrame must be focusable to enable KeyListener.
        setFocusable(true);
        canvus.setFocusable(true);
        canvus.addKeyListener(new CADKeyListener());
        canvus.addMouseListener(new CADMouseListener());
        canvus.addMouseMotionListener(new CADMouseMotionListener());

        lineButton = new JButton("Line");
        lineButton.addMouseListener(new CADButtonListener(CADButtonListener.LINE_BUTTON));

        rectButton = new JButton("Rectangle");
        rectButton.addMouseListener(new CADButtonListener(CADButtonListener.RECT_BUTTON));

        circleButton = new JButton("Circle");
        circleButton.addMouseListener(new CADButtonListener(CADButtonListener.CIRCLE_BUTTON));

        textButton = new JButton("Text");
        textButton.addMouseListener(new CADButtonListener(CADButtonListener.TEXT_BUTTON));

        paletteButton = new JButton();
        paletteButton.setBackground(Color.BLACK);
        paletteButton.addMouseListener(new CADButtonListener(CADButtonListener.PALETTE_BUTTON));

        leftToolBar.add(lineButton);
        leftToolBar.add(rectButton);
        leftToolBar.add(circleButton);
        leftToolBar.add(textButton);
        leftToolBar.add(paletteButton);

        body.add(leftToolBar, BorderLayout.WEST);
        body.add(canvus, BorderLayout.CENTER);
        setContentPane(body);
        setDefault();
        setVisible(true);
    }

    void setDefault() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        setSize(960 * scrnsize.width / 1920, 640 * scrnsize.height / 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    public void repaintCanvus() {
        canvus.repaint();
        canvus.grabFocus();
    }

    public void updateDefaultColor(Color c) {
        paletteButton.setBackground(c);
        paletteButton.repaint();
    }
}
