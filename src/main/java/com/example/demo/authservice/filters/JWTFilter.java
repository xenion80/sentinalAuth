package com.example.demo.authservice.filters;

import com.example.demo.authservice.Entities.User;
import com.example.demo.authservice.repositories.RoleEntityRepository;
import com.example.demo.authservice.repositories.UserRepository;
import com.example.demo.authservice.services.JwtAuthService;
import com.example.demo.authservice.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JwtAuthService jwtAuthService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleEntityRepository roleEntityRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//        String path = request.getServletPath();
//
//        if (path.equals("/auth/login") ||
//                path.equals("/auth/register")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
            try {


            String header=request.getHeader("Authorization");

            if(header==null||!header.startsWith("Bearer ")){
               filterChain.doFilter(request,response);
               return;
            }
            String token=header.substring(7);
            if(!jwtAuthService.validToken(token)){
                filterChain.doFilter(request,response);
                return;
            }
            Long userId =jwtAuthService.extractUserId(token);
            User user=userRepository.findById(userId).orElse(null);
            if (user!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(user,null, user.getRoles().stream().map(
                        role->new SimpleGrantedAuthority(role.getName().name())
                ).collect(Collectors.toSet()));
                SecurityContextHolder.getContext().setAuthentication(auth);


            }
        } catch (Exception e) {
            logger.error("Error occured at {}", e);

        }
        filterChain.doFilter(request,response);
    }




}
