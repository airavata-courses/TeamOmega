/*************************************************
 * Title: 		Aurora-client
 * Author: 		Gourav Shenoy
 * Date : 		12/9/2016
 * Code Version: 0.0.17
 * Original Code Location: https://github.com/gouravshenoy/airavata/tree/aurora-thrift-client/modules/cloud/aurora-client/
 */


package edu.sga.omega.forecast_trigger;


import edu.sga.omega.forecast_trigger.bean.*;
import edu.sga.omega.forecast_trigger.sample.AuroraClientSample;
import edu.sga.omega.forecast_trigger.sdk.*;
import edu.sga.omega.forecast_trigger.util.AuroraThriftClientUtil;
import edu.sga.omega.forecast_trigger.util.Constants;
import edu.sga.omega.forecast_trigger.util.ResponseResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class ThriftClient {
    /**
     * The Constant logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(ThriftClient.class);

    /**
     * The properties.
     */
    private static Properties properties = new Properties();
    /**
     * The thrift client.
     */
    private static ThriftClient thriftClient = null;
    /**
     * The read only scheduler client.
     */
    private ReadOnlyScheduler.Client readOnlySchedulerClient = null;
    /**
     * The aurora scheduler manager client.
     */
    private AuroraSchedulerManager.Client auroraSchedulerManagerClient = null;

    /**
     * Instantiates a new aurora thrift client.
     */
    private ThriftClient() {
    }

    /**
     * Gets the aurora thrift client.
     *
     * @param auroraSchedulerPropFile the aurora scheduler prop file
     * @return the aurora thrift client
     * @throws Exception the exception
     */
    public static ThriftClient getAuroraThriftClient(String auroraSchedulerPropFile) throws Exception {
        try {
            if (thriftClient == null) {
                thriftClient = new ThriftClient();

                // construct connection url for scheduler
                properties.load(AuroraClientSample.class.getClassLoader().getResourceAsStream(auroraSchedulerPropFile));
                String auroraHost = properties.getProperty(Constants.AURORA_SCHEDULER_HOST);
                String auroraPort = properties.getProperty(Constants.AURORA_SCHEDULER_PORT);
                String connectionUrl = MessageFormat.format(Constants.AURORA_SCHEDULER_CONNECTION_URL, auroraHost, auroraPort);

                thriftClient.readOnlySchedulerClient = AuroraSchedulerClientFactory.createReadOnlySchedulerClient(connectionUrl);
                thriftClient.auroraSchedulerManagerClient = AuroraSchedulerClientFactory.createSchedulerManagerClient(connectionUrl);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        return thriftClient;
    }

    /**
     * Creates the job.
     *
     * @param jobConfigBean the job config bean
     * @return the response bean
     * @throws Exception the exception
     */
    public ResponseBean createJob(JobConfigBean jobConfigBean) throws Exception {
        ResponseBean response = null;
        try {
            if (jobConfigBean != null) {
                JobConfiguration jobConfig = AuroraThriftClientUtil.getAuroraJobConfig(jobConfigBean);
                Response createJobResponse = this.auroraSchedulerManagerClient.createJob(jobConfig);
                response = AuroraThriftClientUtil.getResponseBean(createJobResponse, ResponseResultType.CREATE_JOB);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        return response;
    }

    /**
     * Kill tasks.
     *
     * @param jobKeyBean the job key bean
     * @param instances  the instances
     * @return the response bean
     * @throws Exception the exception
     */
    public ResponseBean killTasks(JobKeyBean jobKeyBean, Set<Integer> instances) throws Exception {
        ResponseBean response = null;
        try {
            if (jobKeyBean != null) {
                JobKey jobKey = AuroraThriftClientUtil.getAuroraJobKey(jobKeyBean);
                Response killTaskResponse = this.auroraSchedulerManagerClient.killTasks(jobKey, instances);
                response = AuroraThriftClientUtil.getResponseBean(killTaskResponse, ResponseResultType.KILL_TASKS);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        return response;
    }

    /**
     * Gets the job list.
     *
     * @param ownerRole the owner role
     * @return the job list
     * @throws Exception the exception
     */
    public GetJobsResponseBean getJobList(String ownerRole) throws Exception {
        GetJobsResponseBean response = null;
        try {
            Response jobListResponse = this.readOnlySchedulerClient.getJobs(ownerRole);
            response = (GetJobsResponseBean) AuroraThriftClientUtil.getResponseBean(jobListResponse, ResponseResultType.GET_JOBS);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        return response;
    }

    /**
     * Gets the pending reason for job.
     *
     * @param jobKeyBean the job key bean
     * @return the pending reason for job
     * @throws Exception the exception
     */
    public PendingJobReasonBean getPendingReasonForJob(JobKeyBean jobKeyBean) throws Exception {
        PendingJobReasonBean response = null;
        try {
            JobKey jobKey = AuroraThriftClientUtil.getAuroraJobKey(jobKeyBean);
            Set<JobKey> jobKeySet = new HashSet<>();
            jobKeySet.add(jobKey);

            TaskQuery query = new TaskQuery();
            query.setJobKeys(jobKeySet);

            Response pendingReasonResponse = this.readOnlySchedulerClient.getPendingReason(query);
            response = (PendingJobReasonBean) AuroraThriftClientUtil.getResponseBean(pendingReasonResponse, ResponseResultType.GET_PENDING_JOB_REASON);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        return response;
    }

    /**
     * Gets the job details.
     *
     * @param jobKeyBean the job key bean
     * @return the job details
     * @throws Exception the exception
     */
    public ResponseBean getJobDetails(JobKeyBean jobKeyBean) throws Exception {
        JobDetailsResponseBean response = null;
        try {
            if (jobKeyBean != null) {
                JobKey jobKey = AuroraThriftClientUtil.getAuroraJobKey(jobKeyBean);
                Set<JobKey> jobKeySet = new HashSet<>();
                jobKeySet.add(jobKey);

                TaskQuery query = new TaskQuery();
                query.setJobKeys(jobKeySet);

                Response jobDetailsResponse = this.readOnlySchedulerClient.getTasksStatus(query);
                response = (JobDetailsResponseBean) AuroraThriftClientUtil.getResponseBean(jobDetailsResponse, ResponseResultType.GET_JOB_DETAILS);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        return response;
    }

}