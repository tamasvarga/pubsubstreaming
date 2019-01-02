package Tools;

public class ActionActivator {
    public void SendEmail(String templateKey, Event e){
        e.AddAction("Email sent to user. Template name: "+templateKey);
    }

    public void ScheduleCall(String subject, Event e){
        e.AddAction("Call is scheduled to client. Subject"+subject);
    }
}
