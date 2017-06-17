package Model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelMain
{
    private static final ModelMain singleton = new ModelMain();
    private final List<Program> resources = new ArrayList();
    private final DefaultListModel resourceNames = new DefaultListModel();
    private boolean fileSet = false;
    private boolean noChanges = true;
    private File currentFile;

    private ModelMain()
    {
    }

    public static ModelMain instance()
    {
        return singleton;
    }

    public DefaultListModel getResourceNames()
    {
        return resourceNames;
    }

    public Program getResource(int index)
    {
        return resources.get(index);
    }

    public void appendResource(Program insert)
    {
        this.noChanges = false;
        resources.add(insert);
        resourceNames.addElement(insert.getName());
    }

    public void setResource(Program edited, int index)
    {
        this.noChanges = false;
        resources.set(index, edited);
        resourceNames.set(index, edited.getName());
    }

    public void deleteResource(int index)
    {
        this.noChanges = false;
        resources.remove(index);
        resourceNames.remove(index);
    }

    public void clearResources()
    {
        resources.clear();
        resourceNames.clear();
    }

    public void newFile(JFrame root)
    {
        if (noChanges || promptWarning(root, "Unsaved changes will be lost. Continue?")) {
            clearResources();
        }
    }

    public void openFile(JFrame root)
    {
        if (noChanges || promptWarning(root, "Unsaved changes will be lost. Continue?")) {
            JFileChooser fileChoose = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            fileChoose.setFileFilter(filter);
            if (fileChoose.showOpenDialog(root) == JFileChooser.APPROVE_OPTION) {
                clearResources();
                this.currentFile = fileChoose.getSelectedFile();
                this.fileSet = true;
                try (BufferedReader fr = new BufferedReader(new FileReader(currentFile))) {
                    String currentLine;
                    while ((currentLine = fr.readLine()) != null) {
                        appendResource(new Program(currentLine));
                    }
                    this.noChanges = true;
                }
                catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }
            }
        }
    }

    public void saveFile(JFrame root)
    {
        if (fileSet) {
            try (BufferedWriter fw = new BufferedWriter(new FileWriter(currentFile))) {
                for (Program p : resources) {
                    fw.write(p.toString());
                    fw.newLine();
                }
                this.noChanges = true;
            } catch (IOException exc) {
                System.out.println(exc.getMessage());
            }
        }
        else {
            saveAsFile(root);
        }
    }

    public void saveAsFile(JFrame root)
    {
        JFileChooser fileChoose = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChoose.setFileFilter(filter);
        if (fileChoose.showSaveDialog(root) == JFileChooser.APPROVE_OPTION) {
            this.currentFile = fileChoose.getSelectedFile();
            this.fileSet = true;
            saveFile(root);
        }
    }

    public void deleteFile(JFrame root)
    {
        if (promptWarning(root, "Are you sure?")) {
            clearResources();
            if (fileSet) {
                this.currentFile.delete();
                this.fileSet = false;
            }
            this.noChanges = true;
        }
    }

    private boolean promptWarning(JFrame root, String message)
    {
        return JOptionPane.showConfirmDialog (null, message,"Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public void addDialog(JFrame root)
    {
        Program toAdd = constructResourceDialog(root, "Add new entry", new Program(""));
        if (toAdd.isValid()) {
            appendResource(toAdd);
            this.noChanges = false;
        }
    }

    public void editDialog(JFrame root, int index)
    {
        Program toReplace = constructResourceDialog(root, "Edit entry", resources.get(index));
        if (toReplace.isValid()) {
            setResource(toReplace, index);
            this.noChanges = false;
        }
    }

    private Program constructResourceDialog(JFrame root, String title, Program toReplace)
    {
        JTextField name = new JTextField();
        JTextField language = new JTextField();
        JTextField lines = new JTextField();
        JTextField files = new JTextField();
        JCheckBox multisort = new JCheckBox();

        if (toReplace.isValid()) {
            name.setText(toReplace.getName());
            language.setText(toReplace.getLanguage());
            lines.setText(Integer.toString(toReplace.getLines()));
            files.setText(Integer.toString(toReplace.getFiles()));
            multisort.setSelected(toReplace.getMultisort());
        }

        JComponent[] inputs = new JComponent[] {
                new JLabel("Name"),
                name,
                new JLabel("Programming language"),
                language,
                new JLabel("Number of lines"),
                lines,
                new JLabel("Number of files"),
                files,
                new JLabel("Has multisort?"),
                multisort
        };

        if (JOptionPane.showConfirmDialog(null, inputs, title, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            String construct = name.getText() + "," + language.getText() + "," + lines.getText() + "," + files.getText() + "," + Boolean.toString(multisort.isSelected());
            return new Program(construct);
        } else {
            return new Program("INVALID", "INVALID", 0, 0, false);
        }
    }
}
