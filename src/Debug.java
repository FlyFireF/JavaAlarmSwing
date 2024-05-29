import javax.swing.*;

public class Debug {
    public static void main(String[] args) {
        JDialog d= new JDialog();
        JOptionPane op = new JOptionPane();
        JOptionPane.showConfirmDialog(d,"时间不合法！","提示",JOptionPane.YES_OPTION);
    }
}
