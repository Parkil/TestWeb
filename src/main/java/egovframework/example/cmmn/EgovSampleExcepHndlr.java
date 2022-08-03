package egovframework.example.cmmn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EgovSampleExcepHndlr implements ExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(EgovSampleExcepHndlr.class);
    
    public void occur(Exception ex, String packageName) {
        log.debug(" EgovServiceExceptionHandler run...............");

        try {
            log.debug(" EgovServiceExceptionHandler try ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
