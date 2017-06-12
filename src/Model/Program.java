package Model;

public class Program {

    private String name;
    private String language;
    private int files;
    private int lines;
    private boolean multisort;

    public Program(String name, String language, int files, int lines, boolean multisort) {
        this.name = name;
        this.language = language;
        this.files = files;
        this.lines = lines;
        this.multisort = multisort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void setMultisort(boolean multisort) {
        this.multisort = multisort;
    }

    public String getName() {

        return name;
    }

    public String getLanguage() {
        return language;
    }

    public int getFiles() {
        return files;
    }

    public int getLines() {
        return lines;
    }

    public boolean isMultisort() {
        return multisort;
    }

    @Override
    public String toString() {
        return  "Name: " + name + ',' +
                "Language: " + language + ',' +
                "Files: " + files + ',' +
                "Lines of code: " + lines + ',' +
                "Has multisort: " + multisort;
    }
}
