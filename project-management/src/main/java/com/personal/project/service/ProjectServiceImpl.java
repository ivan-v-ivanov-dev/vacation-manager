package com.personal.project.service;

import com.personal.project.model.Project;
import com.personal.project.repository.ProjectRepository;
import com.personal.project.service.contract.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Page<Project> findAll(PageRequest pageable) {
        Page<Project> projects = projectRepository.findAll(pageable);
        log.info("Retrieve projects");
        return projects;
    }

    @Override
    public Project findByName(String name) {
        Project project = projectRepository.findByName(name);
        log.info(format("Retrieve project %s", name));
        return project;
    }

    @Transactional
    @Override
    public Project updateDescription(String name, String description) {
        Project project = findByName(name);
        if (project == null) {
            log.error(format("No such project %s", name));
            throw new IllegalArgumentException(format("No such project %s", name));
        }

        project = project.toBuilder().description(description).build();
        log.info(format("Update project description %s", name));
        return projectRepository.save(project);
    }
}