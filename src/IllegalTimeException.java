import javax.swing.*;

public class IllegalTimeException extends Exception{
    public IllegalTimeException(){
        JDialog d= new JDialog();
        JOptionPane.showMessageDialog(d,"时间不合法！","提示", JOptionPane.ERROR_MESSAGE);
    }
}
