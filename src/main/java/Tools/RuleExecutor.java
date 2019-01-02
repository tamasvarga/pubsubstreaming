package Tools;

import org.kie.api.runtime.StatelessKieSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuleExecutor implements Serializable {

    public static Iterator<Event> Evulate(Iterator<Event> events) {
        List<Event> result=new ArrayList<Event>();
        StatelessKieSession kieSession = KieFactory.getNewKieSession("rules.drl");
        kieSession.setGlobal("activator",new ActionActivator());
        events.forEachRemaining(e->{
            kieSession.execute(e);
            result.add(e);
        });
        return result.iterator();

    }
}
