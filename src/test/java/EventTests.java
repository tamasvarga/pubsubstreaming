import Tools.Event;
import org.junit.Assert;
import org.junit.Test;

public class EventTests {
    @Test
    public void CanParseJson(){
        String json="{\n" +
                "  \"type\": \"Purchase\",\n" +
                "  \"properties\": [\n" +
                "    {\n" +
                "      \"key\": \"amount\",\n" +
                "      \"value\": \"8999\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"numberofItems\",\n" +
                "      \"value\": \"2\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Event e=new Event(json);
        Assert.assertEquals(e.getType(),"Purchase");
        Assert.assertEquals(e.GetProperty("amount"),"8999");
        Assert.assertEquals(e.GetProperty("numberofItems"),"2");

    }
}
