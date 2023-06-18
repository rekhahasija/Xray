import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import io.atlassian.util.concurrent.Promise;

import java.net.URI;


public class Xray2Test {
    public static void main(String[] args) {
        try {
            URI jiraServerUri = new URI("https://mayank8377890466.atlassian.net");
            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "mayank8377890466@gmail.com", "ATATT3xFfGF0YHKIeyJOLQsuVnLEkYwDJyyoerOUK6il1LAAmb37eUAv6NkJtb64U38zKn_Qcgp8WXLkBhDzlDKBODp5FkZb5lIV6kRUhLKbfS13mw1rR39jhcztVGji609r1epOnBPAiXBHTf8YsKLFcVlu4yrF8MOMCle1p8Mt32io7G5OZds=1A389D3E");
            String testCaseId = "SEL-2";
            String result = "Done";

            String jql = "key = '" + testCaseId + "'";
            Promise<SearchResult> searchResultPromise = (Promise<SearchResult>) restClient.getSearchClient().searchJql(jql);
            SearchResult searchResult = searchResultPromise.claim();
            Iterable<Issue> issues = searchResult.getIssues();
            Issue testCase = issues.iterator().next();
            URI transitionsUri = testCase.getTransitionsUri();

            Iterable<Transition> transitions = restClient.getIssueClient().getTransitions(transitionsUri).claim();
            for (Transition t : transitions) {
                System.out.println("for loop status : "+t.getName().toString());
                if (t.getName().equals(result)) {
                    TransitionInput transitionInput = new TransitionInput(t.getId());
                    Promise<Void> transitionPromise = (Promise<Void>) restClient.getIssueClient().transition(transitionsUri, transitionInput);
                    transitionPromise.claim();
                    break;
                }
                else{
                    System.out.println("for loop status : "+t.getName().toString());
                }
            }

            System.out.println("Test result updated successfully.");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }




}

