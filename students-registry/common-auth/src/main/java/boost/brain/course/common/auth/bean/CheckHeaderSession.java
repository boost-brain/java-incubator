package boost.brain.course.common.auth.bean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface CheckHeaderSession {

    void check(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain);
}
