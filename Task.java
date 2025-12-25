import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Task{
    private static int idC=1;
    private int id;
    private String desc;
    private boolean isCompl;
    private LocalDateTime createDate;
    private String title;

    public Task(String title, String desc ){
        this.id =idC++;
        this.desc=desc;
        this.isCompl=false;
        this.createDate=LocalDateTime.now();
        this.title=title;
    }

    public int getId(){
        return id;
    }
    public String getDesc(){
        return desc;
    }
    public boolean isCompl(){
        return isCompl;
    }
    public LocalDateTime getCreateDate(){
        return createDate;
    }
    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setCompl(boolean compl){
        this.isCompl = compl;
    }
    @Override
    public String toString(){
        DateTimeFormatter form= DateTimeFormatter.ofPattern("dd.MM.yyyy hh.mm");
        String status= isCompl ? "Выполнена" : "Выполняется";
        String des=(desc != null && desc.isEmpty())?"\n Описание:"+desc:"";
        return String.format("ID: %d %s %s Создано: %s %s",id,title,des,createDate.format(form),status);
    }
}
