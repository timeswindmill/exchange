package exchange;

import junit.framework.Assert;
import org.junit.Test;

public class ExchangeConfigTest {


    @Test
    public void testCreateExchangeConfigsFromDb() throws Exception {
        //TODO
    }

    @Test
    public void testCreateExchangeConfigsFromString() throws Exception {
        String configString = "x1,/home/temp,/home/report,AAX,BTX,CDX";
        ExchangeConfig config = ExchangeConfig.createExchangeConfigsFromString(configString);
        Assert.assertNotNull(config);
        Assert.assertNotNull(config.getExchangeName());
        Assert.assertNotNull(config.getInputFileName());
        Assert.assertNotNull(config.getPriceReportDirectory());
        Assert.assertEquals(3, config.getBooks().length);

    }

    @Test
    public void testCreateTestExchangeConfigs() throws Exception {
        //TODO
    }
}
