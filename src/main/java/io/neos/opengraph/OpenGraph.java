package io.neos.opengraph;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.avatar.Avatar;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;

import java.util.Map;

public class OpenGraph extends AbstractJiraContextProvider
{

    @Override
    public Map getContextMap(User user, JiraHelper jiraHelper) {
        Map contextMap = jiraHelper.getContextParams();
        Issue currentIssue = (Issue) contextMap.get("issue");
        if (currentIssue == null) {
            return contextMap;
        }

        String id = (String) currentIssue.getKey();
        String summary = (String) currentIssue.getSummary();
        String description = (String) currentIssue.getDescription();
        contextMap.put("id", id);
        contextMap.put("summary", summary);
        contextMap.put("description", description);

        Project currentProject = jiraHelper.getProjectObject();
        Long avatar = (Long) currentProject.getAvatar().getId();
        Long pid = (Long) currentProject.getId();
        contextMap.put("avatar", avatar);
        contextMap.put("pid", pid);

        StringBuffer requestUrl = (StringBuffer) jiraHelper.getRequest().getRequestURL();
	String baseurl = (String) requestUrl.substring(0, requestUrl.indexOf("/", 8));
        contextMap.put("baseurl", baseurl);

        return contextMap;
    }
}
