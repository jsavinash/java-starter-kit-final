package com.javastarterkit.patterns.modelviewpresenter.presenter;

import com.javastarterkit.patterns.modelviewpresenter.view.TaskView;

/**
 * Base presenter interface defining lifecycle methods.
 * All presenters in MVP adhere to this contract, enabling testability and interchangeability.
 */
public interface TaskPresenter {

    /**
     * Called when a view attaches to this presenter.
     */
    void onAttach(TaskView view);

    /**
     * Called when a view detaches from this presenter.
     */
    void onDetach();

    /**
     * Called when the presenter should clean up resources.
     */
    void onDestroy();
}