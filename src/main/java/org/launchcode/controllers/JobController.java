package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.reflect.Parameter;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/", method = RequestMethod.GET)
    //public String index(@RequestParam("id")int id, @PathVariable int id, Model model) {
    public String index(@RequestParam int id, Model model) {

        Job job = jobData.findById(id);

        model.addAttribute(job);
        //model.addAttribute(id);

        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, String name) {
        if (errors.hasErrors()) {
            return "new-job";
        }

        name = jobForm.getName();
        Employer anEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location aLocation = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType aPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency aSkill = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        Job newjob = new Job(name, anEmployer, aLocation, aPositionType, aSkill);
      //use the getter to get the name

        //get the ID of e.g. location...then add it that way..might have to do extra work in JobData
        //look for the way that they used id to find the Employer -- in the instructions findby id

        //   newJob = new JobData.


        jobData.add(newjob);

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        //if(jobForm's name field not null), add the job and redirect to the view
        //else display the not null error

        //model.addAttribute(newjob.getId());
        model.addAttribute(newjob);

        return "redirect:/job/?id=" +(newjob.getId());
        //return "job-detail";

        //+ "?id=" + newjob.getId();

    }
}
