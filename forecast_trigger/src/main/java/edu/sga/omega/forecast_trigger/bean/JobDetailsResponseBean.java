/*************************************************
 * Title: 		Aurora-client
 * Author: 		Gourav Shenoy
 * Date : 		12/9/2016
 * Code Version: 0.0.17
 * Original Code Location: https://github.com/gouravshenoy/airavata/tree/aurora-thrift-client/modules/cloud/aurora-client/
 */


package edu.sga.omega.forecast_trigger.bean;

import edu.sga.omega.forecast_trigger.sdk.ScheduledTask;

import java.util.List;

/**
 * The Class JobDetailsResponseBean.
 */
public class JobDetailsResponseBean extends ResponseBean {

    /**
     * The tasks.
     */
    private List<ScheduledTask> tasks;

    /**
     * Instantiates a new job details response bean.
     *
     * @param responseBean the response bean
     */
    public JobDetailsResponseBean(ResponseBean responseBean) {
        this.setResponseCode(responseBean.getResponseCode());
        this.setServerInfo(responseBean.getServerInfo());
    }

    /**
     * Gets the tasks.
     *
     * @return the tasks
     */
    public List<ScheduledTask> getTasks() {
        return tasks;
    }

    /**
     * Sets the tasks.
     *
     * @param tasks the new tasks
     */
    public void setTasks(List<ScheduledTask> tasks) {
        this.tasks = tasks;
    }

    /* (non-Javadoc)
     * @see org.apache.airavata.cloud.aurora.client.bean.ResponseBean#toString()
     */
    @Override
    public String toString() {
        return "JobDetailsResponseBean [tasks=" + tasks + "]";
    }
}