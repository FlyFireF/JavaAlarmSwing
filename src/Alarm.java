import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm extends Thread{
    public Alarm(File file, String time){
        this.file=file;
        this.time=time;
    }
    File file;
    String time;
    boolean enabled;
    @Override
    public void run() {
        while (true) {
            if(!enabled) {
                //System.out.println("disabled");
                return;
            }
            //System.out.println("enabled");
            Date current = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String curTime=sdf.format(current);
            //System.out.println(curTime);
            //System.out.println(time);
            if(curTime.equals(time)) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                sleep(900);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
