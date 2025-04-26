//package com.tarun.userService.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.MDC;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//@Order(1)  // run before JwtAuthFilter
//public class CorrelationIdFilter extends OncePerRequestFilter {
//    public static final String CORR_ID = "X‑Correlation‑Id";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//                                    HttpServletResponse res,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//        // propagate or generate
//        String corrId = req.getHeader(CORR_ID);
//        if (corrId == null) {
//            corrId = UUID.randomUUID().toString();
//        }
//        MDC.put(CORR_ID, corrId);
//        res.setHeader(CORR_ID, corrId);
//
//        try {
//            chain.doFilter(req, res);
//        } finally {
//            MDC.clear();
//        }
//    }
//}
