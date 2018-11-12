package com.nobbyknox.absa.service;

import com.nobbyknox.absa.util.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PaymentServiceTests {

    @Autowired
    private PaymentService service;

    @Test
    public void shouldAcceptValidMT101Message() {
        try {
            service.validateMT101Message(TestHelper.getMT101Bytes());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    public void shouldFailOnNullMT101Message() {
        try {
            service.validateMT101Message(null);
            fail("Expected to fail on null message");
        } catch (Exception e) {
            // do nothing
        }
    }
}
