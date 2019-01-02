import Tools.DataStoreConnector;
import org.junit.Assert;
import org.junit.Test;
import java.util.UUID;

public class DataStoreConnectorTest {

    @Test
    public void CanReadUAC(){
        DataStoreConnector connector = new DataStoreConnector();
        Assert.assertEquals("112", connector.ReadUserData(112,"age"));
    }

    @Test
    public void CanWriteDecision(){
        DataStoreConnector connector = new DataStoreConnector();
        String guid = UUID.randomUUID().toString();
        String id=connector.SaveEventDecision(guid,"Email sent");
        Assert.assertEquals(guid,id);
    }
}
