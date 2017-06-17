package View;

import Model.ModelMain;
import Model.Program;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class ViewMain {

    private final JFrame frame;
    private JPanel gui;
    private Runnable setpane;
    private Runnable clearpane;
    private JList list;

    public ViewMain() {

        frame = new JFrame();
        frame.setSize(600, 400);

        gui = new JPanel(new GridLayout(1,2,5,5));
        gui.setBorder(new EmptyBorder(5,5,5,5));

        setupList();
        setupPane();
        setupMenu();

        frame.setContentPane(gui);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("JavaSchool");
    }

    private void setupPane()
    {
        JPanel panePanel = new JPanel();
        panePanel.setLayout(new BoxLayout(panePanel, BoxLayout.Y_AXIS));

        JLabel currName = new JLabel(" ");
        JLabel currLanguage = new JLabel(" ");
        JLabel currLines = new JLabel(" ");
        JLabel currFiles = new JLabel(" ");
        JLabel currMultisort = new JLabel(" ");

        setpane = () -> {
            int index = list.getSelectedIndex();
            if (index >= 0) {
                Program p = ModelMain.instance().getResource(index);
                String[] parts = p.toString().split(",");

                currName.setText(parts[0]);
                currLanguage.setText(parts[1]);
                currLines.setText(parts[2]);
                currFiles.setText(parts[3]);
                currMultisort.setText(parts[4]);
            }
            else {
                clearpane.run();
            }
        };

        clearpane = () -> {
            currName.setText(" ");
            currLanguage.setText(" ");
            currLines.setText(" ");
            currFiles.setText(" ");
            currMultisort.setText(" ");
        };

        panePanel.add(new JLabel("<html><h3>Name:</h3>"));
        panePanel.add(currName);
        panePanel.add(new JLabel("<html><h3>Programming language:</h>"));
        panePanel.add(currLanguage);
        panePanel.add(new JLabel("<html><h3>Number of lines:</h>"));
        panePanel.add(currLines);
        panePanel.add(new JLabel("<html><h3>Number of files:</h>"));
        panePanel.add(currFiles);
        panePanel.add(new JLabel("<html><h3>Has multisort:</h>"));
        panePanel.add(currMultisort);

        gui.add(panePanel);
    }

    private void setupList()
    {
        list = new JList(ModelMain.instance().getResourceNames());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.getSelectionModel().addListSelectionListener(
            (ListSelectionEvent event) -> setpane.run()
        );

        gui.add(list);
    }

    private void setupMenu()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        {
            JMenuItem newCommand = new JMenuItem("New");
            newCommand.addActionListener(
                (ActionEvent event) -> ModelMain.instance().newFile(frame)
            );
            fileMenu.add(newCommand);

            JMenuItem openCommand = new JMenuItem("Open");
            openCommand.addActionListener(
                (ActionEvent event) -> ModelMain.instance().openFile(frame)
            );
            fileMenu.add(openCommand);

            JMenuItem saveCommand = new JMenuItem("Save");
            saveCommand.addActionListener(
                (ActionEvent event) -> ModelMain.instance().saveFile(frame)
            );
            fileMenu.add(saveCommand);

            JMenuItem saveAsCommand = new JMenuItem("Save as");
            saveAsCommand.addActionListener(
                (ActionEvent event) -> ModelMain.instance().saveAsFile(frame)
            );
            fileMenu.add(saveAsCommand);

            JMenuItem deleteCommand = new JMenuItem("Delete");
            deleteCommand.addActionListener(
                (ActionEvent event) -> ModelMain.instance().deleteFile(frame)
            );
            fileMenu.add(deleteCommand);
        }
        menuBar.add(fileMenu);

        JMenu listMenu = new JMenu("List");
        {
            JMenuItem newEntryCommand = new JMenuItem("New entry");
            newEntryCommand.addActionListener(
                    (ActionEvent event) -> ModelMain.instance().addDialog(frame)
            );
            listMenu.add(newEntryCommand);

            JMenuItem editEntryCommand = new JMenuItem("Edit entry");
            editEntryCommand.addActionListener(
                (ActionEvent event) -> {
                    int index = list.getSelectedIndex();
                    if (index != -1) {
                        ModelMain.instance().editDialog(frame, index);
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "No entry selected!");
                    }
                }
            );
            listMenu.add(editEntryCommand);

            JMenuItem deleteEntryCommand = new JMenuItem("Delete entry");
            deleteEntryCommand.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    int index = list.getSelectedIndex();
                    if (index >= 0) {
                        ModelMain.instance().deleteResource(index);
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "No entry selected!");
                    }
                }
            });
            listMenu.add(deleteEntryCommand);
        }
        menuBar.add(listMenu);

        frame.setJMenuBar(menuBar);
    }
}
