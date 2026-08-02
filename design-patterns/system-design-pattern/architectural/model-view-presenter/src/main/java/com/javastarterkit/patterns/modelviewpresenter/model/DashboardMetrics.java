package com.javastarterkit.patterns.modelviewpresenter.model;

/**
 * Immutable record representing dashboard summary metrics for a user.
 * Computed from the user's task repository data.
 *
 * @param totalTasks      total number of tasks
 * @param pendingTasks    tasks in PENDING status
 * @param inProgressTasks tasks in IN_PROGRESS status
 * @param completedTasks  tasks in COMPLETED status
 * @param overdueTasks    tasks past their due date and not completed
 */
public record DashboardMetrics(
        long totalTasks,
        long pendingTasks,
        long inProgressTasks,
        long completedTasks,
        long overdueTasks
) {

    /**
     * Returns a metrics instance with all counts initialized to zero.
     */
    public static DashboardMetrics empty() {
        return new DashboardMetrics(0, 0, 0, 0, 0);
    }
}