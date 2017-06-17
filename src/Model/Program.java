package Model;

public class Program {

    private String name;
    private String language;
    private int files;
    private int lines;
    private boolean multisort;

    public Program(String name, String language, int files, int lines, boolean multisort)
    {
        this.name = name;
        this.language = language;
        this.files = files;
        this.lines = lines;
        this.multisort = multisort;
    }

    public Program(String fromString)
    {
        String[] parts = fromString.split(",");
        if (parts.length == 5) {
            this.name = parts[0];
            this.language = parts[1];
            this.files = Integer.parseInt(parts[2]);
            this.lines = Integer.parseInt(parts[3]);
            this.multisort = Boolean.parseBoolean(parts[4]);
        }
        else {
            this.name = "INVALID";
            this.language = "INVALID";
            this.files = 0;
            this.lines = 0;
            this.multisort = false;
        }
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

    public String getName() { return name; }

    public String getLanguage() {
        return language;
    }

    public int getFiles() {
        return files;
    }

    public int getLines() {
        return lines;
    }

    public boolean getMultisort() {
        return multisort;
    }

    public boolean isValid() { return (name != "INVALID") && (language != "INVALID"); }

    @Override
    public String toString() {
        return  name + ',' +
                language + ',' +
                Integer.toString(files)+ ',' +
                Integer.toString(lines)+ ',' +
                Boolean.toString(multisort);
    }
}
