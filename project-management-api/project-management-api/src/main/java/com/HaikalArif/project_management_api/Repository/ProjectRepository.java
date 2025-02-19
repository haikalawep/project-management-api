package com.HaikalArif.project_management_api.Repository;

import com.HaikalArif.project_management_api.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
