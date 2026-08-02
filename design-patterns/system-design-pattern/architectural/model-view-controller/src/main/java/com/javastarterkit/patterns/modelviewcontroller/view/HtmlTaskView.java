package com.javastarterkit.patterns.modelviewcontroller.view;

import com.javastarterkit.patterns.modelviewcontroller.model.Task;
import com.javastarterkit.patterns.modelviewcontroller.model.TaskList;

/**
 * HTML view that renders tasks as HTML.
 *
 * <p>This is a <b>View</b> in the MVC pattern. It demonstrates that multiple
 * views can observe the same model and render it differently. It has no
 * knowledge of the controller or business logic.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class HtmlTaskView implements TaskView {

    @Override
    public void render(TaskList model) {
        StringBuilder html = new StringBuilder();
        html.append("  <html>\n");
        html.append("    <body>\n");
        html.append("      <h1>Tasks (").append(model.completedCount())
                .append("/").append(model.size()).append(")</h1>\n");
        html.append("      <ul>\n");
        for (Task task : model.tasks()) {
            String css = task.isCompleted() ? " style=\"text-decoration:line-through\"" : "";
            html.append("        <li").append(css).append(">")
                    .append(task.description()).append("</li>\n");
        }
        html.append("      </ul>\n");
        html.append("    </body>\n");
        html.append("  </html>");
        System.out.println(html);
    }

    @Override
    public void onModelChanged(TaskList model) {
        // In a real UI, this would auto-refresh the HTML view.
    }
}