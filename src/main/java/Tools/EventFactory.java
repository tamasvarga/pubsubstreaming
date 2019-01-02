package Tools;

public class EventFactory {
    public static Event Create(String json){
        try{
            Event e=new Event(json);
            return e;
        }
        catch (Exception e){
            return null;
        }

    }
}
