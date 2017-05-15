package org.rogan.map.util;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodTimeInterceptor
  implements MethodInterceptor
{
  private static final Logger log = LoggerFactory.getLogger(MethodTimeInterceptor.class);

  public Object invoke(MethodInvocation invocation) throws Throwable { StopWatch sw = new StopWatch();
    sw.start();
    Object result = invocation.proceed();
    sw.stop();

    Class[] params = invocation.getMethod().getParameterTypes();
    String[] strArr = new String[params.length];
    for (int i = 0; i < params.length; i++) {
      strArr[i] = params[i].getSimpleName();
    }
    log.debug("#LogInterceptor " + sw.getTime() + " ms [" + 
      invocation.getThis().getClass().getName() + "." + invocation.getMethod().getName() + 
      "(" + StringUtils.join(strArr, ",") + ")]");
    return result;
  }
}