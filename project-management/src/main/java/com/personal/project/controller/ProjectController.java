package com.personal.project.controller;

import com.personal.model.dto.ProjectResponse;
import com.personal.model.dto.TeamResponse;
import com.personal.project.model.Project;
import com.personal.project.model.Team;
import com.personal.project.model.dto.TeamRq;
import com.personal.project.service.contract.ProjectService;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final TeamsService teamsService;
    private final ProjectService projectService;

    @GetMapping("/team/{name}")
    public TeamResponse findTeamByName(@PathVariable("name") String name) {
        return teamsService.findByName(name);
    }

    @GetMapping("/teams")
    public Page<TeamResponse> getTeams(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return teamsService.findAll(PageRequest.of(page, size));
    }

    @PostMapping("/team")
    public TeamResponse createTeam(@Valid @RequestBody TeamRq teamRq) {
        return teamsService.create(teamRq);
    }

    @PutMapping("/team/{name}/member/{id}/add")
    public TeamResponse addMemberToATeam(@PathVariable("name") String name,
                                         @PathVariable("id") int id) {
        return teamsService.addMemberToATeam(name, id);
    }

    @PutMapping("/team/{name}/member/{id}/remove")
    public TeamResponse removeMemberFromATeam(@PathVariable("name") String name,
                                              @PathVariable("id") int id) {
        return teamsService.removeMemberFromATeam(name, id);
    }

    @PutMapping("/team/{teamName}/add-project/{projectName}")
    public TeamResponse addTeamToProject(@PathVariable("teamName") String teamName,
                                         @PathVariable("projectName") String projectName) {
        return teamsService.addProject(teamName, projectName);
    }

    @PutMapping("/team/{teamName}/remove-project/{projectName}")
    public TeamResponse removeTeamToProject(@PathVariable("teamName") String teamName,
                                            @PathVariable("projectName") String projectName) {
        return teamsService.removeProject(teamName, projectName);
    }

    @DeleteMapping("/team/{name}")
    public boolean deleteATeam(@PathVariable("name") String name) {
        return teamsService.delete(name);
    }

    @GetMapping("/teams/search")
    public List<TeamResponse> searchTeams(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                  @RequestParam(value = "projectName", required = false, defaultValue = "") String projectName,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return teamsService.searchTeams(name, projectName, page, size);
    }

    @GetMapping("/projects")
    public Page<ProjectResponse> findAllProjects(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return projectService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/project/{name}")
    public ProjectResponse findProjectByName(@PathVariable("name") String name) {
        return projectService.findByName(name);
    }

    @PutMapping("/project/{name}")
    public ProjectResponse updateProjectDescription(@PathVariable("name") String name,
                                            @RequestParam("description") String description) {
        return projectService.updateDescription(name, description);
    }

    @DeleteMapping("/project/{name}")
    public boolean deleteProject(@PathVariable("name") String name) {
        return projectService.delete(name);
    }
}
