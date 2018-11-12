package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.service.PaymentService;
import com.nobbyknox.absa.util.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.doAnswer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PaymentReceiverTests {

    @MockBean
    private PaymentService service;

    @Autowired
    private PaymentReceiver receiver;

    @Test
    public void shouldAutowireReceiver() {
        assertNotNull(receiver);
    }

    @Test
    public void shouldReceiveByteArray() {
        try {
            doAnswer((Answer<Void>) invocation -> null).when(this.service).validateMT101Message(null);
            doAnswer((Answer<Void>) invocation -> null).when(this.service).progressFlow(null);
            receiver.receiveMessage(TestHelper.getMT101Bytes());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

}
