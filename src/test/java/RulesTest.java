import Tools.Event;
import Tools.RuleExecutor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RulesTest {
    @Test
    public void CanFireRules(){
        List<Event> events=new ArrayList<>();
        events.add(new Event("{\n" +
                "  \"type\": \"Purchase\",\n" +
                "  \"properties\": [\n" +
                "    {\n" +
                "      \"key\": \"amount\",\n" +
                "      \"value\":\"8999\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\":\"numberofItems\",\n" +
                "      \"value\":\"2\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"));
        RuleExecutor.Evulate(events.iterator());
        for (Event e:events) {
            Assert.assertNotNull(e.GetActions());
        }
    }
}
