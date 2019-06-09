package com.acceval.msexample.multitenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.acceval.core.security.CurrentUser;
import com.acceval.core.security.PrincipalUtil;


@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

    	CurrentUser currentUser = PrincipalUtil.getCurrentUser();
    	
    	String tenantId = null;
    	
    	if (currentUser != null) {
    		tenantId = currentUser.getCompanyCode();
    	} else {
    		tenantId = request.getHeader(PrincipalUtil.HDRKEY_COMPANYCODE);
    	}
    	
        TenantContext.setCurrentTenant(tenantId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
    		ModelAndView modelAndView) throws Exception {
    	
        TenantContext.clear();
    }
}
