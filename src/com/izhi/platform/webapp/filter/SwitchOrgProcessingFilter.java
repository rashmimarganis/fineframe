package com.izhi.platform.webapp.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationCredentialsNotFoundException;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.LockedException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.event.authentication.AuthenticationSwitchUserEvent;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AuthenticationDetailsSource;
import org.acegisecurity.ui.AuthenticationDetailsSourceImpl;
import org.acegisecurity.ui.switchuser.SwitchUserGrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

import com.izhi.platform.model.Org;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IOrgService;
import com.izhi.platform.service.IUserService;

public class SwitchOrgProcessingFilter implements Filter, InitializingBean,
		MessageSourceAware, ApplicationEventPublisherAware {
	private static final Logger logger = LoggerFactory
			.getLogger(SwitchOrgProcessingFilter.class);
	public static final String ACEGI_SECURITY_SWITCH_ORG_KEY = "j_org_id";
	private ApplicationEventPublisher eventPublisher;
	private AuthenticationDetailsSource authenticationDetailsSource = new AuthenticationDetailsSourceImpl();
	protected MessageSourceAccessor messages = AcegiMessageSource.getAccessor();
	private String exitOrgUrl = "/j_exit_org";
	private String switchOrgUrl = "/j_switch_org";
	private String targetUrl;
	private IUserService userService;
	private IOrgService orgService;

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	protected Authentication attemptExitUser(HttpServletRequest request)
			throws AuthenticationCredentialsNotFoundException {
		Authentication current = SecurityContextHolder.getContext()
				.getAuthentication();

		if (null == current) {
			throw new AuthenticationCredentialsNotFoundException(messages
					.getMessage("SwitchUserProcessingFilter.noCurrentUser",
							"No current user associated with this request"));
		}
		Authentication original = getSourceAuthentication(current);

		if (original == null) {
			logger.error("Could not find original user Authentication object!");
			throw new AuthenticationCredentialsNotFoundException(
					messages
							.getMessage(
									"SwitchUserProcessingFilter.noOriginalAuthentication",
									"Could not find original Authentication object"));
		}
		UserDetails originalUser = null;
		Object obj = original.getPrincipal();

		if ((obj != null) && obj instanceof UserDetails) {
			originalUser = (UserDetails) obj;
		}
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new AuthenticationSwitchUserEvent(
					current, originalUser));
		}

		return original;
	}

	protected Authentication attemptSwitchUser(HttpServletRequest request,
			int orgId) throws AuthenticationException {
		UsernamePasswordAuthenticationToken targetUserRequest = null;

		String username = SecurityUser.getCurrentUser().getUsername();

		if (username == null) {
			username = "";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempt to switch to user [" + username + "]");
		}

		UserDetails targetUser = this.userService.findUserByName(username,
				orgId);

		if (targetUser == null) {
			throw new UsernameNotFoundException(messages.getMessage(
					"SwitchUserProcessingFilter.usernameNotFound",
					new Object[] { username }, "Username {0} not found"));
		}

		if (!targetUser.isAccountNonLocked()) {
			throw new LockedException(messages.getMessage(
					"SwitchUserProcessingFilter.locked",
					"User account is locked"));
		}

		if (!targetUser.isEnabled()) {
			throw new DisabledException(messages.getMessage(
					"SwitchUserProcessingFilter.disabled", "User is disabled"));
		}

		if (!targetUser.isAccountNonExpired()) {
			throw new AccountExpiredException(messages.getMessage(
					"SwitchUserProcessingFilter.expired",
					"User account has expired"));
		}

		if (!targetUser.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(messages.getMessage(
					"SwitchUserProcessingFilter.credentialsExpired",
					"User credentials have expired"));
		}

		targetUserRequest = createSwitchUserToken(request, username, targetUser);

		if (logger.isDebugEnabled()) {
			logger.debug("Switch User Token [" + targetUserRequest + "]");
		}

		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new AuthenticationSwitchUserEvent(
					SecurityContextHolder.getContext().getAuthentication(),
					targetUser));
		}
		return targetUserRequest;
	}

	private UsernamePasswordAuthenticationToken createSwitchUserToken(
			HttpServletRequest request, String username, UserDetails targetUser) {
		UsernamePasswordAuthenticationToken targetUserRequest;
		GrantedAuthority[] authorities = targetUser.getAuthorities();

		targetUserRequest = new UsernamePasswordAuthenticationToken(targetUser,
				targetUser.getPassword(), authorities);

		targetUserRequest.setDetails(authenticationDetailsSource
				.buildDetails((HttpServletRequest) request));

		return targetUserRequest;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!SecurityUser.isAnonymous()) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			if (requiresSwitchUser(httpRequest)) {
				int orgId = 0;
				try {
					String o=request
					.getParameter(ACEGI_SECURITY_SWITCH_ORG_KEY);
					if(o!=null||o.trim().length()>0){
						orgId = Integer.parseInt(o);
					}
				} catch (Exception e) {
					e.printStackTrace();
					orgId=0;
				}
				Org o=new Org();
				if (orgId == 0) {
					o=SecurityUser.getCurrentUser().getPerson().getOrg();
					if(o!=null){
						orgId =o.getId();
					}
				}
				Org o1=orgService.findById(new Integer(orgId));
				o.setId(orgId);
				o.setName(o1.getName());
				o.setTitle(o1.getTitle());
				o.setType(o1.getType());
				o.setParent(o1.getParent());
				o.setOrgPath(o1.getOrgPath());
				o.setDepth(o1.getDepth());
				//SecurityUser.setCurrentOrg(o);
				Authentication targetUser = attemptSwitchUser(httpRequest,
						orgId);

				SecurityContextHolder.getContext()
						.setAuthentication(targetUser);
				
				PrintWriter pw=httpResponse.getWriter();
				pw.write("{success:true}");
				return;
			} else if (requiresExitUser(httpRequest)) {
				Authentication originalUser = attemptExitUser(httpRequest);

				SecurityContextHolder.getContext().setAuthentication(
						originalUser);

				httpResponse.sendRedirect(httpResponse
						.encodeRedirectURL(httpRequest.getContextPath()
								+ targetUrl));

				return;
			}
		}
		chain.doFilter(request, response);
	}

	private Authentication getSourceAuthentication(Authentication current) {
		Authentication original = null;

		GrantedAuthority[] authorities = current.getAuthorities();

		for (int i = 0; i < authorities.length; i++) {
			if (authorities[i] instanceof SwitchUserGrantedAuthority) {
				original = ((SwitchUserGrantedAuthority) authorities[i])
						.getSource();
				logger.debug("Found original switch user granted authority ["
						+ original + "]");
			}
		}
		return original;
	}

	protected boolean requiresExitUser(HttpServletRequest request) {
		String uri = stripUri(request);
		return uri.endsWith(request.getContextPath() + exitOrgUrl);
	}

	protected boolean requiresSwitchUser(HttpServletRequest request) {
		String uri = stripUri(request);
		return uri.endsWith(request.getContextPath() + switchOrgUrl);
	}

	public void setApplicationEventPublisher(
			ApplicationEventPublisher eventPublisher) throws BeansException {
		this.eventPublisher = eventPublisher;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource,
				"AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	private static String stripUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int idx = uri.indexOf(';');

		if (idx > 0) {
			uri = uri.substring(0, idx);
		}

		return uri;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getExitOrgUrl() {
		return exitOrgUrl;
	}

	public void setExitOrgUrl(String exitOrgUrl) {
		this.exitOrgUrl = exitOrgUrl;
	}

	public String getSwitchOrgUrl() {
		return switchOrgUrl;
	}

	public void setSwitchOrgUrl(String switchOrgUrl) {
		this.switchOrgUrl = switchOrgUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public IOrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}
}
