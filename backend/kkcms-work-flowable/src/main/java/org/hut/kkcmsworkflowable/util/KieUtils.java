package org.hut.kkcmsworkflowable.util;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by hutwanghui on 2018/12/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
public class KieUtils {
    private static KieContainer kieContainer;

    private static KieSession kieSession;

    public static KieContainer getKieContainer() {
        return kieContainer;
    }

    public static void setKieContainer(KieContainer kieContainer) {
        KieUtils.kieContainer = kieContainer;
        kieSession = kieContainer.newKieSession();
    }

    public static KieSession getKieSession() {
        return kieSession;
    }

    public static void setKieSession(KieSession kieSession) {
        KieUtils.kieSession = kieSession;
    }
}
