package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
            Message message = new Message();
//            message.setA1(true);
            message.setA4(true);
            kSession.insert(message);
            kSession.fireAllRules();
            System.out.println("[推理结束]的状态："+message.toString());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
