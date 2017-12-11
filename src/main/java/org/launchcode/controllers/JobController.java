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
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam int id, Model model) {

        //RequestParam has the reference to id and produces the ?id= syntax

        Job job = jobData.findById(id);

        model.addAttribute(job);

        //the object job has all the fields needed, which can be displayed in the template using *{field}

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

        //from the jobForm thing we can get all the info needed to create a new Job object, then add it to the database, finding each field by its id.

        jobData.add(newjob);

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        //if(jobForm's name field not null), add the job and redirect to the view
        //else display the not null error


        model.addAttribute(newjob);

        return "redirect:/job?id=" +(newjob.getId());


    }
}
