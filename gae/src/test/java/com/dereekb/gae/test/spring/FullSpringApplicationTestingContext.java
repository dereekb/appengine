package com.dereekb.gae.test.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(name = "testing", locations = { CoreServiceTestingContext.GAE_TESTING_XML_PATH }) })
public class FullSpringApplicationTestingContext {

}
