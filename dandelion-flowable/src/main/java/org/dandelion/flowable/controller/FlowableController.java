package org.dandelion.flowable.controller;

import org.dandelion.flowable.service.FlowableService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/6/10 14:16
 */
@RestController
public class FlowableController {

    @Autowired
    private FlowableService flowableService;

    @PostMapping(value = "/process")
    public void startProcessInstance() {
        flowableService.startProcess();
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = flowableService.getTasks(assignee);
        List<TaskRepresentation> dos = new ArrayList<>();
        for (Task task : tasks) {
            dos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dos;
    }

    static class TaskRepresentation {

        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
