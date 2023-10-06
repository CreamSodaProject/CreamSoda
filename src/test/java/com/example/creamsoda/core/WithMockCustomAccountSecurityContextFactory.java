package com.example.creamsoda.core;

import com.example.creamsoda.auth.MyUserDetails;
import com.example.creamsoda.module.user.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomAccountSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
		User user = User.builder()
				.id(mockCustomUser.id())
				.email(mockCustomUser.email())
				.password("1234")
				.name("David")
				.build();
		MyUserDetails myUserDetails = new MyUserDetails(user);

		var securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(
				new UsernamePasswordAuthenticationToken(
						myUserDetails,
						myUserDetails.getPassword(),
						myUserDetails.getAuthorities()
				)
		);

		return securityContext;
	}
}