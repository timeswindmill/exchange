package db.dataLoader;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Map;

public class StaticDataLoaderTest {

    @Test
    public void testLoadInstruments() throws Exception {
        Map instruments = StaticDataLoader.loadInstruments();
        Assert.assertNotNull(instruments);
        Assert.assertFalse(instruments.isEmpty());


    }

    @Test
    public void testLoadClients() throws Exception {
        Map clients = StaticDataLoader.loadClients();
        Assert.assertNotNull(clients);
        Assert.assertFalse(clients.isEmpty());


    }


}
