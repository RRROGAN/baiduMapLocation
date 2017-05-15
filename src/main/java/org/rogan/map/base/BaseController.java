package org.rogan.map.base;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	
	protected void printParams(HttpServletRequest request, String remark){
		Enumeration enu=request.getParameterNames();
    	Map<String,Object> params = new HashMap<String, Object>();
        while(enu.hasMoreElements()) {
            String name=(String)enu.nextElement(); 
            params.put(name, request.getParameter(name));
        }
        logger.info(remark+",params="+JSON.toJSONString(params));
	}
	
}
