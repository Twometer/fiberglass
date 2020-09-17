package de.twometer.fiberglass.photon;

public class JavaWriter {

    private final StringBuilder builder = new StringBuilder();

    public void openClass(String clazz, String iface) {
        writeLine("public class " + clazz + " implements " + iface + " {");
    }

    public void openFunction(String returnType, String name, String arglist) {
        writeLine("public " + returnType + " " + name + "(" + arglist + ") {");
    }

    public void closeBlock() {
        writeLine("}");
    }

    public void writeStatement(String s) {
        writeLine(s + ";");
    }

    public void writeLine(String s) {
        builder.append(s).append("\n");
    }

    @Override
    public String toString() {
        return builder.toString();
    }


}
