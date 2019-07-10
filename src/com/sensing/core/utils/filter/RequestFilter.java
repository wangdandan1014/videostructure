package com.sensing.core.utils.filter;


import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        /* wrap the request in order to read the inputstream multiple times */
        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);

        /* here I read the inputstream and do my thing with it; when I pass the
         * wrapped request through the filter chain, the rest of the filters, and
         * request handlers may read the cached inputstream
         */
        boolean isMultipart = ServletFileUpload.isMultipartContent(multiReadRequest);
        if (!isMultipart && multiReadRequest.getMethod().toUpperCase().equals("POST")) {
            String c_request_body = getRequestPayload(multiReadRequest);
            multiReadRequest.setAttribute("_request_body", c_request_body);
        }
        multiReadRequest.rebuildParams();
        chain.doFilter(multiReadRequest, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        if (arg0 == null) {

        }
    }
    public static String getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
