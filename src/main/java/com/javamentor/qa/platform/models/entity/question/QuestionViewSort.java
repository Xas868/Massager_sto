package com.javamentor.qa.platform.models.entity.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionViewSort {
    NEW {
        @Override
        public String getSortClause() {
            return "ORDER BY q.persistDateTime desc ";
        }
    },
    NO_ANSWER {
        @Override
        public String getSortClause() {
            return "AND q.answers IS EMPTY ";
        }
    },
    VIEW {
        @Override
        public String getSortClause() {
            return "ORDER BY view_count desc ";
        }
    };

    public abstract String getSortClause();
}
