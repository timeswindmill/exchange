package dataStructures.jobExchanger;

import org.junit.Assert;
import org.junit.Test;

public class DataArrayTest {


    @Test
    public void testSetup() throws Exception {

        DataArray<String> array = new DataArray<>(20);
        Assert.assertNotNull(array);
//        System.out.println("Offset for element 3 is " + array.getOffset(3));
//        System.out.println("Offset for element 4 is " + array.getOffset(4));
//        System.out.println("Offset for element 5 is " + array.getOffset(5));
    }


    @Test
    public void testSetElement() throws Exception {

        DataArray array = new DataArray(20);

        Assert.assertNotNull(array);
        DataElement<String> element = new DataElement("test");
        boolean added = array.addElement(element, 4);
        Assert.assertTrue(added);

        // should fail this time
        added = array.addElement(element, 4);
        Assert.assertFalse(added);


    }


    @Test
    public void testGetElement() throws Exception {

        DataArray array = new DataArray(20);

        Assert.assertNotNull(array);
        DataElement<String> element = new DataElement("test");
        array.setElement(element, 4);

        DataElement element2 = array.consumeElement(4);
        Assert.assertEquals(element, element2);


    }


}
