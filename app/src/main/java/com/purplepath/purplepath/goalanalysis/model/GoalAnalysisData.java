package com.purplepath.purplepath.goalanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 18-Jul-16.
 */
public class GoalAnalysisData implements Serializable {
        private String message;

        private Goal_det goal_det;

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        public Goal_det getGoal_det ()
        {
            return goal_det;
        }

        public void setGoal_det (Goal_det goal_det)
        {
            this.goal_det = goal_det;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [message = "+message+", goal_det = "+goal_det+"]";
        }
}
