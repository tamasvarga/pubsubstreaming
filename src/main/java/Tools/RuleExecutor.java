package Tools;

import org.kie.api.runtime.StatelessKieSession;

import java.io.Serializable;
import java.util.Iterator;

public class RuleExecutor implements Serializable {

    public static void Evulate(Iterator<Event> events) {
        StatelessKieSession kieSession = KieFactory.getNewKieSession("rules.drl");
        kieSession.setGlobal("activator",new ActionActivator());
        events.forEachRemaining(kieSession::execute);

    }

    public static void Evulate(Event e) {
        StatelessKieSession kieSession = KieFactory.getNewKieSession("rules.drl");
        kieSession.setGlobal("activator",new ActionActivator());
        kieSession.execute(e);

    }
}
