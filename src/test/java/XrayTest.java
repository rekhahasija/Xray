import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
/*import com.atlassian.jira.rest.client.api.domain.UserPermissions;*/
import java.net.URI;
import java.net.URISyntaxException;


public class XrayTest {
    WebDriver driver;
    private JiraRestClient jira;

    @BeforeTest
    public void firstTestSetUp() throws URISyntaxException {
        try {
            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            URI uri = new URI("https://mayank8377890466.atlassian.net");
            //jira = factory.createWithBasicHttpAuthentication(uri, "BD5309E6EJEA46E0822CB786E2A3BAFC", "d2753b8b811f8c93554c8db6cb7e5c8f8ee8a0ee08596e9b4e4659ac676eaf7d");
            jira = factory.createWithBasicHttpAuthentication(uri, "mayank8377890466@gmail.com", "ATATT3xFfGF0kRZtsQ2FTvoU-k7U3b6hJXN1Z1dSprOPdWy5q11nULt2sWqCC_96YLIZ_PGarnaWgCd-ljNY05_dnsuH-OVPzktcCD-YuFpLPv8qMtGU-nlNpn_vkxSFUpGkkfEWMgRxkhJKQ2jeeQlZQJXVdMjF84PqtZzNio5Xf8ccG69pxUk=3FA137A8");
            String issueKey = "SEL-2";
            Issue issue = jira.getIssueClient().getIssue(issueKey).claim();
            System.out.println("Issue Summary: " + issue.getSummary());
        }catch (Throwable e){
            if (e.getMessage().contains("400")){
                System.out.println("Hey! Issue does not exist or you do not have permission to see it.");
                Assert.fail();
            } else {
                // Handle other exceptions or errors
                e.printStackTrace();
                Assert.fail(e.getMessage());
            }
        }
    }


    @Test
    public void FirstTest() {
        System.out.println("Hello 1");
        updateTestResult("SEL-3", "Pass");
    }




    private void updateTestResult(String testCaseId, String result) {
        //String jql = "project = Selenium AND id = "+testCaseId+"";
        String jql = "project = Selenium AND key = '"+testCaseId+"'";
        //UserPermissions userPermissions = jira.getUser("username").getPermissions();
        SearchResult searchResult = jira.getSearchClient().searchJql(jql).claim();
        Iterable<Issue> issues = searchResult.getIssues();
        Issue testCase = issues.iterator().next();
        Iterable<Transition> transitions = jira.getIssueClient().getTransitions(testCase.getTransitionsUri()).claim();
        for (Transition t : transitions) {
            if (t.getName().equals(result)) {
                TransitionInput transitionInput = new TransitionInput(t.getId());
                System.out.println("id is : "+jira.getIssueClient());
                jira.getIssueClient().transition(testCase.getTransitionsUri(), transitionInput).claim();
                break;
            }
        }
    }


}

