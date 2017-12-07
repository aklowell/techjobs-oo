package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.JobFieldType;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(@PathVariable int id, Model model) {
        Job job = jobData.findById(id);

        model.addAttribute(job);


        // TODO #1 - get the Job with the given ID and pass it into the view


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid Job newJob, Errors errors) {
       if (errors.hasErrors()) {
           return "new-job";
       }
        jobData.add(newJob);
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        //if(jobForm's name field not null), add the job and redirect to the view
        //else display the not null error


        return "job-detail";

    }
}
