package ui;

import java.util.ArrayList;

public class FileData {
    private String packageName;
    private ArrayList<String> arrLibrary;
    private String className;
    private ArrayList<String> arrMethod;
    private ArrayList<String> arrField;

    public FileData() {
    }

    public FileData(String packageName, String className, ArrayList<String> arrLibrary, ArrayList<String> arrMethod, ArrayList<String> arrField) {
        this.packageName = packageName;
        this.arrLibrary = arrLibrary;
        this.className = className;
        this.arrMethod = arrMethod;
        this.arrField = arrField;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public ArrayList<String> getArrLibrary() {
        return arrLibrary;
    }

    public void setArrLibrary(ArrayList<String> arrLibrary) {
        this.arrLibrary = arrLibrary;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getArrMethod() {
        return arrMethod;
    }

    public void setArrMethod(ArrayList<String> arrMethod) {
        this.arrMethod = arrMethod;
    }

    public ArrayList<String> getArrField() {
        return arrField;
    }

    public void setArrField(ArrayList<String> arrField) {
        this.arrField = arrField;
    }

    @Override
    public String toString() {
        String data = className.trim() + "\n" + packageName.trim() + "\n";
        String line = "__________________________________________________\n";
        data = data + line;
        System.out.println(className.trim() + "\n");
        System.out.println(packageName.trim() + "\n");
        for (int i = 0; i < arrLibrary.size(); i++) {
            data = data + arrLibrary.get(i).trim() + "\n";
            System.out.println(arrLibrary.get(i).trim() + "\n");
        }
        data = data + line;
        for (int i = 0; i < arrField.size(); i++) {
            data = data + arrField.get(i).trim() + "\n";
            System.out.println(arrField.get(i).trim() + "\n");
        }
        data = data + line;
        for (int i = 0; i < arrMethod.size(); i++) {
            data = data + arrMethod.get(i).trim() + "\n";
            System.out.println(arrMethod.get(i).trim() + "\n");
        }
        data = data + line;
        System.out.println("______________________________________________________________________________________________");
        return data;
    }
}
