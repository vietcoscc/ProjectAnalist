package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    public static final String PACKAGE = "package";
    public static final String IMPORT = "import";
    public static final String CLASS = "class";
    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";

    private JTextField tfProjectPath;
    private Button btnBrowse;

    public MainPanel() {
        setLayout(null);
        initComponent();
    }

    private void initComponent() {

        tfProjectPath = new JTextField();
        tfProjectPath.setSize(500, 25);
        tfProjectPath.setLocation(100, 100);
        tfProjectPath.setText("C:\\Users\\Ominext\\IdeaProjects\\ProjectAnalist");
        add(tfProjectPath);
        btnBrowse = new Button();
        btnBrowse.setBackground(Color.lightGray);
        btnBrowse.setSize(100, 25);
        btnBrowse.setLocation(tfProjectPath.getX() + tfProjectPath.getWidth() + 50, tfProjectPath.getY());
        btnBrowse.setLabel("Browse");
        btnBrowse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File("C:\\Users\\Ominext\\IdeaProjects\\ProjectAnalist"));
                int result = fileChooser.showOpenDialog(null);
                File file = fileChooser.getSelectedFile();
                if (tfProjectPath != null && file != null) {
                    tfProjectPath.setText(file.getPath());
                    collectFile(file);
                }
            }
        });
        add(btnBrowse);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void collectFile(File file) {
        if (file == null) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                String name = file.getName();
//                System.out.println(name);
                if (name.endsWith(".java")) {
                    try {
                        FileReader fileReader = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fileReader);
                        StringBuffer stringBuffer = new StringBuffer();
                        String s = reader.readLine();
                        while (s != null) {
                            stringBuffer.append(s + "\n");
                            s = reader.readLine();
                        }
                        FileData fileData = analyse(stringBuffer.toString(), name);
                        fileData.toString();
                        JOptionPane.showMessageDialog(null, fileData.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (file.isDirectory()) {
                    File arrFile[] = file.listFiles();
                    for (int i = 0; i < arrFile.length; i++) {
                        File tmpFile = arrFile[i];
                        collectFile(tmpFile);
                    }
                }
            }
        }.start();

    }

    private FileData analyse(String data, String fileName) {
        String packageName = findPackageName(data);
        ArrayList<String> arrLibrary = findArrLibrary(data);
        ArrayList<String> arrFeild = findArrFeild(data);
        ArrayList<String> arrMethod = findAllMethod(data);
        return new FileData(packageName, fileName, arrLibrary, arrMethod, arrFeild);
    }

    private String findPackageName(String data) {
        String packageName;
        int packageIndex = data.indexOf(PACKAGE + " ");
        int endLineIndex = data.indexOf(";", packageIndex);
        if (packageIndex != -1) {
            packageName = data.substring(packageIndex, endLineIndex);
        } else {
            packageName = "";
        }
        return packageName.trim();
    }

    private ArrayList<String> findArrLibrary(String data) {
        ArrayList<String> arrLibrary = new ArrayList<>();
        int importIndex = data.indexOf(IMPORT + " ");
        while (importIndex != -1) {
            int endLineIndex = data.indexOf(";", importIndex);
            arrLibrary.add(data.substring(importIndex, endLineIndex).trim());
            importIndex = data.indexOf(IMPORT + " ", endLineIndex);
        }
        return arrLibrary;
    }

    private ArrayList<String> findArrFeild(String data) {
        ArrayList<String> arrField = new ArrayList<>();

        String arr[] = data.split(";");
        for (int j = 0; j < arr.length; j++) {
            String content = arr[j];
            if (!content.contains(CLASS + " ") && !content.contains(PACKAGE + " ") && !content.contains(IMPORT + " ")
                    && !content.contains("+") && !content.contains("-") && !content.contains("*") && !content.contains("}")
                    && !content.contains("(") && !content.contains(")") && !content.contains("{") && !content.contains("if")
                    && !content.contains("else") && !content.contains(".") && !content.contains("!=") && !content.contains("return ") && !content.contains("__")) {
                if (!arrField.contains(content.trim())) {
                    arrField.add(content.trim());
                }

            }
        }
        int startIndex = data.indexOf("{");
        int endIndex = data.indexOf(";", startIndex);
        String firstField = data.substring(startIndex + 1, endIndex).trim();
        if (!firstField.contains("main") && !firstField.contains("args")) {
            arrField.add(0, firstField);
        }
        return arrField;
    }

    private ArrayList<String> findAllMethod(String data) {
        ArrayList<String> arrMethod = new ArrayList<>();
        String[] arrLine = data.split("\n");
        for (int i = 0; i < arrLine.length; i++) {
            String line = arrLine[i];
            if (line.contains("(") && line.contains(")") && line.contains("{") && !line.contains(";") && !line.contains(" if")
                    && !line.contains(" for") && !line.contains(" while") && !line.contains(" catch") && !line.contains("catch ") && !line.contains(" catch ")
                    && !line.contains("&&") && !line.contains("=") && !line.contains("!") && !line.contains("||") && !line.contains(".") && !line.contains("new ")) {
                arrMethod.add(line);
            }
        }
        return arrMethod;
    }
}
