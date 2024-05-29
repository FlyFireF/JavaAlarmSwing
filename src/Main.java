import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

public class Main {
    static{
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //添加控件
    static JFrame f = new JFrame("Alarm in Java");
    //两个panel
    static JPanel p1 = new JPanel(new FlowLayout());
    static JPanel p2 = new JPanel(new FlowLayout());
    //标签
    static JLabel text = new JLabel("请设定时间：");
    static JLabel colon1 = new JLabel(":");
    static JLabel colon2 = new JLabel(":");
    //文本框 时分秒
    static JTextField h = new JTextField();
    static JTextField m = new JTextField();
    static JTextField s = new JTextField();
    //按钮 选择音乐 设定闹钟
    static JButton musicButton = new JButton("选择音乐");
    static JToggleButton setButton = new JToggleButton("设定闹钟");
    static File file;//音乐文件
    static Alarm alarm;//闹钟
    public static void main(String[] args) {
        setWindow();//窗体
        //选择音乐功能实现
        musicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = chooseMusic();
                setButton.setEnabled(true);
            }
        });
        setButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String time;
                try{
                //设置闹钟
                if(setButton.isSelected()){
                    time =chooseTime();
                    alarm = new Alarm(file,time);
                    alarm.enabled=true;
                    alarm.start();
                    h.setEnabled(false);
                    m.setEnabled(false);
                    s.setEnabled(false);
                    musicButton.setEnabled(false);
                }
                else {
                    if(alarm!=null) {
                        alarm.enabled=false;
                        h.setEnabled(true);
                        m.setEnabled(true);
                        s.setEnabled(true);
                        musicButton.setEnabled(true);
                    }
                }
                }catch (IllegalTimeException err){
                    if(setButton.isSelected()){
                        setButton.setSelected(false);
                        //System.out.println(err);
                        //showTimeErrMsg();
                    }
                }
            }
        });
    }
    private static void setWindow(){
        //控件归位
        f.add(p1); f.add(p2);
        p1.add(text); p1.add(h); p1.add(colon1); p1.add(m); p1.add(colon2); p1.add(s);
        p2.add(musicButton); p2.add(setButton);
        setButton.setEnabled(false);
        //设定界面布局
        f.setLayout(new GridLayout(2,1));
        f.setSize(280,120);
        h.setPreferredSize(new Dimension (30,20));
        m.setPreferredSize(new Dimension (30,20));
        s.setPreferredSize(new Dimension (30,20));
        f.setMinimumSize(new Dimension(280,120));
        //点X结束程序
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗口
        f.setVisible(true);
    }
    private static File chooseMusic(){
        FileDialog fd = new FileDialog(f);//文件选择对话框
        fd.setVisible(true);
        String path=fd.getDirectory()+fd.getFile();//音乐路径
        return new File(path);//返回音乐文件
    }
    private static String chooseTime()throws IllegalTimeException{
        //将输入的时分秒转换为00:00:00格式的字符串
        //时分秒判断一位还是两位
        String hour,minute,second;
        hour=adjustTime(h);
        minute=adjustTime(m);
        second=adjustTime(s);
        //拼接字符串
        StringBuffer B= new StringBuffer();
        B.append(hour); B.append(":"); B.append(minute); B.append(":"); B.append(second);
        return B.toString();
    }
    private static String adjustTime(JTextField T)throws IllegalTimeException{
        //判断时间是否合法
        isLegal(T);
        StringBuffer B= new StringBuffer();
        if(1==T.getText().length()){
            B.append("0");
            B.append(T.getText());
            //System.out.println(T.getText());
        }else{
            B.append(T.getText());
        }
        return B.toString();
    }
    private static void isLegal(JTextField T) throws IllegalTimeException{
        try{
        int num=Integer.parseInt(T.getText());//不是数字，报错
        if(num<0||num>=60) {
            System.out.println("小于0或大于等于60");
            throw new IllegalTimeException();//小于0或大于等于60，报错
        }
        if(T.equals(h)&&num>=24){//小时大于24，报错
            System.out.println("小时大于24");
            throw new IllegalTimeException();
        }
        }catch (NumberFormatException e){
            System.out.println("err");
            throw new IllegalTimeException();
        }
    }
    /*private static void showTimeErrMsg(){
        JDialog d= new JDialog();
        JLabel errText = new JLabel("时间不合法！");
        d.add(errText);
        d.setSize(100,50);
        d.setLocation(f.getX()+f.getWidth()/2-d.getWidth()/2,f.getY()+f.getHeight()/2-d.getHeight()/2);
        d.setVisible(true);
    }*/
}