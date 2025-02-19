package com.HaikalArif.project_management_api.Repository;

import com.HaikalArif.project_management_api.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Find task using Project ID
    List<Task> findByProjectId(long projectId);
    // FInd task using Project ID and Task ID
    Optional<Task> findByProjectIdAndId(long projectId, long id);
}
